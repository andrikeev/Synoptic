package ru.andrikeev.android.synoptic.model.network.places;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.andrikeev.android.synoptic.model.network.places.response.PlacesResponse;

/**
 * Created by overtired on 25.07.17.
 */

public interface GooglePlacesApi {
    String API_KEY = "key";
    String QUERY = "query";
    String LANGUAGE = "language";

    //https://maps.googleapis.com/maps/api/place/textsearch/json?key=AIzaSyAY4H9vB8S8B4_9ESsEiQAsJW2dqUXYzw0&query=%22Vladimir%22&language=ru&type=(cities)

    @GET("json?")
    Single<PlacesResponse> getPlacesByQuery(
            @Query(API_KEY) String key,
            @Query(QUERY) String query,
            @Query(LANGUAGE) String language);
}
