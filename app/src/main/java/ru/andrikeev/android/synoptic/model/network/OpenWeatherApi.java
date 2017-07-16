package ru.andrikeev.android.synoptic.model.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.andrikeev.android.synoptic.model.network.response.WeatherResponse;

/**
 * Open Weather Map API.
 */
public interface OpenWeatherApi {

    String API_KEY = "appid";

    String CITY_NAME = "q";
    String CITY_ID = "id";
    String LATITUDE = "lat";
    String LONGITUDE = "lon";
    String ZIP_CODE = "zip";

    String LANGUAGE = "lang";

    @GET("weather")
    Single<WeatherResponse> getWeatherForCoords(
            @Query(API_KEY) String apiKey,
            @Query(LANGUAGE) String language,
            @Query(LONGITUDE) double longitude,
            @Query(LATITUDE) double latitude);

    @GET("weather")
    Single<WeatherResponse> getWeatherForCity(
            @Query(API_KEY) String apiKey,
            @Query(LANGUAGE) String language,
            @Query(CITY_NAME) String cityName);

    @GET("weather")
    Single<WeatherResponse> getWeatherForCity(
            @Query(API_KEY) String apiKey,
            @Query(LANGUAGE) String language,
            @Query(CITY_ID) long cityId);

    @GET("weather")
    Single<WeatherResponse> getWeatherForZip(
            @Query(API_KEY) String apiKey,
            @Query(LANGUAGE) String language,
            @Query(ZIP_CODE) String zipCode);

}
