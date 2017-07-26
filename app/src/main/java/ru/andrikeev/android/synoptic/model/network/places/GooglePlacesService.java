package ru.andrikeev.android.synoptic.model.network.places;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.network.places.response.Places;

/**
 * Created by overtired on 25.07.17.
 */

public class GooglePlacesService {

    public static final String API_KEY_PLACES = "api_key";//TODO:?

    private GooglePlacesApi api;

    private Settings settings;

    private String apiKey;

    @Inject
    GooglePlacesService(@NonNull GooglePlacesApi api,
                        @NonNull Settings settings,
                        @NonNull @Named(API_KEY_PLACES) String apiKey){
        this.api = api;
        this.settings = settings;
        this.apiKey = apiKey;
    }

    public Single<Places> loadPlaces(String query){
        return api.getPlacesByQuery(apiKey,query,settings.getLocale())
                .subscribeOn(Schedulers.io());
    }
}
