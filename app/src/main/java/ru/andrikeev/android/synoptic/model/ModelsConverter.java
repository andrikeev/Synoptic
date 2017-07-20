package ru.andrikeev.android.synoptic.model;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Locale;

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

    @Inject
    ModelsConverter(@NonNull Settings settings) {
        this.settings = settings;
    }

    public WeatherModel toViewModel(@NonNull Weather weather) {
        return new WeatherModel(weather.getCityName(),
                new Date(weather.getTimestamp()),
                weather.getWeatherId(),
                weather.getShortDescription(),
                weather.getDescription(),
                UnitsUtils.formatTemperature(weather.getTemperature(), settings.getTempUnits()),
                UnitsUtils.round(weather.getPressure()),
                UnitsUtils.round(weather.getHumidity()),
                UnitsUtils.round(weather.getWindSpeed()),
                resolveWindDirection(weather.getWindDegree()),
                UnitsUtils.round(weather.getClouds()));
    }

    private static WeatherModel.WindDirection resolveWindDirection(float windDegree) {
        if (windDegree >= 0 && windDegree < 33.75) {
            return WeatherModel.WindDirection.NORTH;
        } else if (windDegree >= 33.75 && windDegree < 78.75) {
            return WeatherModel.WindDirection.NORTH_EAST;
        } else if (windDegree >= 78.75 && windDegree < 123.75) {
            return WeatherModel.WindDirection.EAST;
        } else if (windDegree >= 123.75 && windDegree < 168.75) {
            return WeatherModel.WindDirection.SOUTH_EAST;
        } else if (windDegree >= 168.75 && windDegree < 213.75) {
            return WeatherModel.WindDirection.SOUTH;
        } else if (windDegree >= 213.75 && windDegree < 258.75) {
            return WeatherModel.WindDirection.SOUTH_WEST;
        } else if (windDegree >= 258.75 && windDegree < 303.75) {
            return WeatherModel.WindDirection.WEST;
        } else if (windDegree >= 303.75 && windDegree < 348.75) {
            return WeatherModel.WindDirection.NORTH_WEST;
        } else if (windDegree >= 348.75 && windDegree <= 360) {
            return WeatherModel.WindDirection.NORTH;
        } else {
            throw new IllegalStateException(String.format(Locale.ENGLISH, "Wrong wind degree %.3f", windDegree));
        }
    }

    public Weather toDbModel(@NonNull WeatherResponse weatherResponse) {
        return new Weather(
                weatherResponse.getCityId(),
                weatherResponse.getCity(),
                weatherResponse.getDate() * 1000,
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
