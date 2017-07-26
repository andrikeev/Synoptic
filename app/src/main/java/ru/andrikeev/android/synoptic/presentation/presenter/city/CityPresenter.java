package ru.andrikeev.android.synoptic.presentation.presenter.city;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.ModelsConverter;
import ru.andrikeev.android.synoptic.model.network.RemoteService;
import ru.andrikeev.android.synoptic.model.network.google_places.GooglePlacesService;
import ru.andrikeev.android.synoptic.model.network.google_places.ResponceConverter;
import ru.andrikeev.android.synoptic.model.network.google_places.response_places.Location;
import ru.andrikeev.android.synoptic.model.network.google_places.response_places.PlacesResponse;
import ru.andrikeev.android.synoptic.model.network.google_places.response_predictions.PredictionsResponse;
import ru.andrikeev.android.synoptic.model.network.openweather.OpenWeatherService;
import ru.andrikeev.android.synoptic.model.network.openweather.response_place.response.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;

/**
 * Created by overtired on 25.07.17.
 */

@InjectViewState
public class CityPresenter extends RxPresenter<CityView> {

    Settings settings;

    ModelsConverter converter;
    RemoteService weatherService;
    GooglePlacesService placesService;

    private Consumer<String> consumer = new Consumer<String>() {
        @Override
        public void accept(@NonNull String input) throws Exception {
            placesService.loadPredictions(input)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<PredictionsResponse>() {
                        @Override
                        public void accept(@NonNull PredictionsResponse predictionsResponse) throws Exception {
                            getViewState().updateList(ResponceConverter.toViewModel(predictionsResponse));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            getViewState().showError();
                        }
                    });
        }
    };

    @Inject
    public CityPresenter(@NonNull GooglePlacesService placesService,
                         @NonNull RemoteService weatherService,
                         @NonNull ModelsConverter converter,
                         @NonNull Settings settings) {
        this.placesService = placesService;
        this.weatherService = weatherService;
        this.converter = converter;
        this.settings = settings;
    }

    public Consumer<String> getConsumer() {
        return consumer;
    }

    public void loadCity(final String placeId) {
        getViewState().showLoading();
        getViewState().hideKeyboard();

        placesService.loadPlace(placeId)
                .map(new Function<PlacesResponse, Location>() {
                    @Override
                    public Location apply(@NonNull PlacesResponse placesResponse) throws Exception {
                        if(placesResponse.getResultPlace()!=null) {
                            return placesResponse.getResultPlace().getGeometry().getLocation();
                        }else {
                            throw new Exception("Couldn't load this city");
                        }
                    }
                })
                .subscribe(new Consumer<Location>() {
                    @Override
                    public void accept(@NonNull Location location) throws Exception {
                        weatherService.getWeather(location.getLatitude(), location.getLongitude())
                                .subscribeOn(Schedulers.io())
                                .map(new Function<WeatherResponse, Weather>() {
                                    @Override
                                    public Weather apply(@NonNull WeatherResponse weatherResponse) throws Exception {
                                        return converter.toCacheModel(weatherResponse);
                                    }
                                })
                                .subscribe(new Consumer<Weather>() {
                                    @Override
                                    public void accept(@NonNull Weather weather) throws Exception {
                                        settings.setCityId(weather.getCityId());
                                        getViewState().hideProgressAndExit();
                                    }
                                });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getViewState().showError();
                        getViewState().hideLoading();
                    }
                });
    }
}
