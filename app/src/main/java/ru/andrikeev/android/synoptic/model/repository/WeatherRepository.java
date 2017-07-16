package ru.andrikeev.android.synoptic.model.repository;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;
import ru.andrikeev.android.synoptic.model.ModelsConverter;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.OpenWeatherService;
import ru.andrikeev.android.synoptic.model.network.response.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;
import ru.andrikeev.android.synoptic.model.persistence.WeatherDataBase;
import timber.log.Timber;

public class WeatherRepository {

    /**
     * From API documentation: "Do not send requests more than 1 time per 10 minutes
     * from one device/one API key. Normally the weather is not changing so frequently."
     */
    private static final long FETCH_MIN_TIMEOUT = 600000L;

    @NonNull
    private PublishSubject<Resource<WeatherModel>> subject = PublishSubject.create();

    @NonNull
    private OpenWeatherService weatherService;

    @NonNull
    private WeatherDataBase storage;

    @NonNull
    private ModelsConverter converter;

    @Inject
    WeatherRepository(@NonNull OpenWeatherService weatherService,
                      @NonNull WeatherDataBase storage,
                      @NonNull ModelsConverter converter) {
        this.weatherService = weatherService;
        this.storage = storage;
        this.converter = converter;
    }

    public Observable<Resource<WeatherModel>> loadWeather() {
        return storage.getWeather()
                .switchIfEmpty(loadRemoteAndSave().toObservable())
                .map(new Function<Weather, Resource<WeatherModel>>() {
                    @Override
                    public Resource<WeatherModel> apply(@NonNull Weather weather) throws Exception {
                        if (System.currentTimeMillis() - weather.getTimestamp() > FETCH_MIN_TIMEOUT) {
                            fetchWeather();
                            return Resource.fetching(converter.toViewModel(weather));
                        } else {
                            return Resource.success(converter.toViewModel(weather));
                        }
                    }
                })
                .concatWith(subject)
                .observeOn(AndroidSchedulers.mainThread());

    }

    private Single<Weather> loadRemoteAndSave() {
        return weatherService.loadWeather()
                .map(new Function<WeatherResponse, Weather>() {
                    @Override
                    public Weather apply(@NonNull WeatherResponse weatherResponse) throws Exception {
                        Weather weather = converter.toDbModel(weatherResponse);
                        storage.saveOrUpdate(weather);
                        return weather;
                    }
                });
    }

    public void fetchWeather() {
        loadRemoteAndSave().subscribe(
                new Consumer<Weather>() {
                    @Override
                    public void accept(@NonNull Weather weather) throws Exception {
                        Timber.d("Weather fetched: %s", weather);
                        subject.onNext(Resource.success(converter.toViewModel(weather)));
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Timber.e("Error fetching weather", throwable);
                        subject.onNext(Resource.<WeatherModel>error(throwable));
                    }
                });
    }
}
