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
    OpenWeatherService weatherService;
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
                    });
        }
    };

    @Inject
    public CityPresenter(@NonNull GooglePlacesService placesService,
                         @NonNull OpenWeatherService openWeatherService,
                         @NonNull ModelsConverter converter,
                         @NonNull Settings settings) {
        this.placesService = placesService;
        this.weatherService = openWeatherService;
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
                        return placesResponse.getResultPlace().getGeometry().getLocation();
                    }
                })
                .subscribe(new Consumer<Location>() {
                    @Override
                    public void accept(@NonNull Location location) throws Exception {
                        weatherService.loadWeather(location.getLatitude(), location.getLongitude())
                                .subscribeOn(Schedulers.io())
                                .map(new Function<WeatherResponse, Weather>() {
                                    @Override
                                    public Weather apply(@NonNull WeatherResponse weatherResponse) throws Exception {
                                        return converter.toDbModel(weatherResponse);
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
                });
    }
}
