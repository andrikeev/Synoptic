package ru.andrikeev.android.synoptic.model.network.openweather.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.andrikeev.android.synoptic.model.network.openweather.response.current.Clouds;
import ru.andrikeev.android.synoptic.model.network.openweather.response.current.WeatherCondition;
import ru.andrikeev.android.synoptic.model.network.openweather.response.current.WeatherDescription;
import ru.andrikeev.android.synoptic.model.network.openweather.response.current.Wind;

/**
 * Weather model from response.
 */
public class WeatherResponse {

    @SerializedName("weather")
    private List<WeatherDescription> weatherDescription;

    @SerializedName("main")
    private WeatherCondition weatherCondition;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("dt")
    private long date;

    @SerializedName("name")
    private String city;

    @SerializedName("id")
    private long cityId;

    public WeatherDescription getWeatherDescription() {
        return weatherDescription.get(0);
    }

    public WeatherCondition getWeatherCondition() {
        return weatherCondition;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public long getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public long getCityId() {
        return cityId;
    }
}
