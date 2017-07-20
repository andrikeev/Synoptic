package ru.andrikeev.android.synoptic.model.network;

import android.support.annotation.NonNull;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrikeev.android.synoptic.model.network.response.WeatherResponse;

public class OpenWeatherService {

    public static final String API_KEY_NAME = "api_key";

    private OpenWeatherApi api;

    private String apiKey;

    @Inject
    OpenWeatherService(@NonNull OpenWeatherApi api,
                       @NonNull @Named(API_KEY_NAME) String apiKey) {
        this.api = api;
        this.apiKey = apiKey;
    }

    public Single<WeatherResponse> loadWeather() {
        return api.getWeatherForCity(apiKey, Locale.getDefault().toString().substring(0, 2), 5601538L) // TODO: change to user choice
                .subscribeOn(Schedulers.io());
    }
}
