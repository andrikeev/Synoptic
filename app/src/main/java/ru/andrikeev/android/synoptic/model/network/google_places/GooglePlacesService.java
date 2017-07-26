package ru.andrikeev.android.synoptic.model.network.google_places;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.network.google_places.response_places.PlacesResponse;
import ru.andrikeev.android.synoptic.model.network.google_places.response_predictions.PredictionsResponse;

/**
 * Created by overtired on 25.07.17.
 */

public class GooglePlacesService {

    public static final String API_KEY_PLACES = "api_key_places";//TODO:?

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

    public Single<PlacesResponse> loadPlace(String placeId){
        return api.getPlacesByQuery(apiKey,placeId,settings.getLocale())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<PredictionsResponse> loadPredictions(String input){
        return api.getPredictions(apiKey,input,settings.getLocale())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}