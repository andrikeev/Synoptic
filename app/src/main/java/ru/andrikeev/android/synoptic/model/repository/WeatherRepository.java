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
import ru.andrikeev.android.synoptic.model.network.openweather.OpenWeatherService;
import ru.andrikeev.android.synoptic.model.network.openweather.response.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;
import ru.andrikeev.android.synoptic.model.persistence.WeatherDataBase;
import timber.log.Timber;

public class WeatherRepository {

    /**
     * From API documentation: "Do not send requests more than 1 time per 10 minutes
     * from one device/one API key. Normally the weather is not changing so frequently."
     */
    private static final long FETCH_MIN_TIMEOUT = 600_000_000L;

    private static long CITY_STUB_ID = 5601538L; // TODO: change to user choice

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

    private static boolean shouldFetchWeather(@NonNull Weather weather) {
        return System.currentTimeMillis() - weather.getTimestamp() > FETCH_MIN_TIMEOUT;
    }

    @NonNull
    public Observable<Resource<WeatherModel>> loadWeather() {
        return storage.getWeather(CITY_STUB_ID)
                .switchIfEmpty(loadRemoteAndSave(CITY_STUB_ID).toObservable())
                .map(new Function<Weather, Resource<WeatherModel>>() {
                    @Override
                    public Resource<WeatherModel> apply(@NonNull Weather weather) throws Exception {
                        if (shouldFetchWeather(weather)) {
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

    @NonNull
    private Single<Weather> loadRemoteAndSave(long cityId) {
        return weatherService.loadWeather(cityId)
                .map(new Function<WeatherResponse, Weather>() {
                    @Override
                    public Weather apply(@NonNull WeatherResponse weatherResponse) throws Exception {
                        Timber.d("Weather loaded from api: %s", weatherResponse);
                        Weather weather = converter.toDbModel(weatherResponse);
                        storage.saveOrUpdate(weather);
                        return weather;
                    }
                });
    }

    public void fetchWeather() {
        loadRemoteAndSave(CITY_STUB_ID).subscribe(
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
                        Timber.e(throwable, "Error fetching weather");
                        subject.onNext(Resource.<WeatherModel>error(throwable));
                    }
                });
    }
}
