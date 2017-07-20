package ru.andrikeev.android.synoptic.model;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.response.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;
import ru.andrikeev.android.synoptic.utils.UnitsUtils;

/**
 * Helper class for converting models.
 */
@Singleton
public class ModelsConverter {

    private Settings settings;

    private Context context;

    @Inject
    public ModelsConverter(@NonNull Settings settings,
                           @NonNull Context context) {
        this.settings = settings;
        this.context = context;
    }

    public WeatherModel toViewModel(@NonNull Weather weather) {
        return new WeatherModel(weather.getCityName(),
                new Date(weather.getTimestamp() * 1000),
                weather.getWeatherId(),
                weather.getShortDescription(),
                weather.getDescription(),
                UnitsUtils.formatTemperature(weather.getTemperature(), UnitsUtils.Units.METRIC),
                UnitsUtils.round(weather.getPressure()),
                UnitsUtils.round(weather.getHumidity()),
                UnitsUtils.round(weather.getWindSpeed()),
                WeatherModel.WindDirection.NORTH,
                UnitsUtils.round(weather.getClouds()));
    }

    public Weather toDbModel(@NonNull WeatherResponse weatherResponse) {
        return new Weather(
                weatherResponse.getCityId(),
                weatherResponse.getCity(),
                weatherResponse.getDate(),
                weatherResponse.getWeatherDescription().getId(),
                weatherResponse.getWeatherDescription().getShortDescription(),
                weatherResponse.getWeatherDescription().getDescription(),
                weatherResponse.getWeatherCondition().getTemperature(),
                weatherResponse.getWeatherCondition().getPressure(),
                weatherResponse.getWeatherCondition().getHumidity(),
                weatherResponse.getWind().getSpeed(),
                weatherResponse.getWind().getDegree(),
                weatherResponse.getClouds().getPercents());
    }

}
