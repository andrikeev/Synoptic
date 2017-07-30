package ru.andrikeev.android.synoptic.model;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.network.response.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;
import ru.andrikeev.android.synoptic.utils.DateUtils;
import ru.andrikeev.android.synoptic.utils.UnitsUtils;
import ru.andrikeev.android.synoptic.utils.units.TemperatureUnits;

/**
 * Helper class for converting models.
 */
@Singleton
public class ModelsConverterImpl implements ModelsConverter {

    private Settings settings;

    private Context context;

    @Inject
    ModelsConverterImpl(@NonNull Settings settings,
                        @NonNull Context context) {
        this.settings = settings;
        this.context = context;
    }

    public WeatherModel toViewModel(@NonNull Weather weather) {
        return new WeatherModel(weather.getCityName(),
                DateUtils.formatDate(new Date(weather.getTimestamp())),
                resolveWeatherIcon(weather.getWeatherId()),
                weather.getDescription(),
                getTemperatureString(weather.getTemperature()),
                getTemperatureUnits(),
                getPressureString(weather.getPressure()),
                getHumidityString(weather.getHumidity()),
                getWindString(weather.getWindSpeed()),
                resolveWindDirection(weather.getWindDegree()),
                getCloudsString(weather.getClouds()));
    }

    private static int resolveWindDirection(float windDegree) {
        if (windDegree >= 0 && windDegree < 33.75) {
            return R.drawable.ic_weather_wind_direction_north;
        } else if (windDegree >= 33.75 && windDegree < 78.75) {
            return R.drawable.ic_weather_wind_direction_north_east;
        } else if (windDegree >= 78.75 && windDegree < 123.75) {
            return R.drawable.ic_weather_wind_direction_east;
        } else if (windDegree >= 123.75 && windDegree < 168.75) {
            return R.drawable.ic_weather_wind_direction_south_east;
        } else if (windDegree >= 168.75 && windDegree < 213.75) {
            return R.drawable.ic_weather_wind_direction_south;
        } else if (windDegree >= 213.75 && windDegree < 258.75) {
            return R.drawable.ic_weather_wind_direction_south_west;
        } else if (windDegree >= 258.75 && windDegree < 303.75) {
            return R.drawable.ic_weather_wind_direction_west;
        } else if (windDegree >= 303.75 && windDegree < 348.75) {
            return R.drawable.ic_weather_wind_direction_north_west;
        } else if (windDegree >= 348.75 && windDegree <= 360) {
            return R.drawable.ic_weather_wind_direction_north;
        } else {
            throw new IllegalStateException(String.format(Locale.ENGLISH, "Wrong wind degree %.3f", windDegree));
        }
    }

    private static int resolveWeatherIcon(int weatherId) {
        if (200 <= weatherId && weatherId < 300) {
            if (210 <= weatherId && weatherId <= 221) {
                return R.drawable.ic_weather_lightning;
            } else {
                return R.drawable.ic_weather_lightning_rainy;
            }
        } else if (300 <= weatherId && weatherId < 400) {
            return R.drawable.ic_weather_rainy;
        } else if (500 <= weatherId && weatherId < 600) {
            return R.drawable.ic_weather_pouring;
        } else if (600 <= weatherId && weatherId < 700) {
            if (611 <= weatherId && weatherId <= 616) {
                return R.drawable.ic_weather_snowy_rainy;
            } else {
                return R.drawable.ic_weather_snowy;
            }
        } else if (700 <= weatherId && weatherId < 800) {
            return R.drawable.ic_weather_fog;
        } else if (800 == weatherId) {
            return R.drawable.ic_weather_sunny;
        } else if (801 <= weatherId && weatherId <= 802) {
            return R.drawable.ic_weather_partlycloudy;
        } else if (803 <= weatherId && weatherId <= 804) {
            return R.drawable.ic_weather_cloudy;
        } else if (weatherId == 906) {
            return R.drawable.ic_weather_hail;
        } else if (weatherId == 771 ||
                weatherId == 781 ||
                weatherId == 900 ||
                weatherId == 901 ||
                weatherId == 902 ||
                weatherId == 905 ||
                weatherId > 951) {
            return R.drawable.ic_weather_windy;
        }
        return 0;
    }

    private String getTemperatureString(float temperature) {
        return String.valueOf(UnitsUtils.formatTemperature(temperature, settings.getTempUnits()));
    }

    private String getTemperatureUnits() {
        return context.getString(settings.getTempUnits() == TemperatureUnits.CELSIUS
                ? R.string.pref_temp_units_celsius_sign
                : R.string.pref_temp_units_fahrenheit_sign);
    }

    private String getPressureString(float pressure) {
        return context.getString(R.string.weather_pressure_pascals, UnitsUtils.round(pressure));
    }

    private String getHumidityString(float humidity) {
        return context.getString(R.string.weather_humidity, UnitsUtils.round(humidity));
    }

    private String getWindString(float wind) {
        return context.getString(R.string.weather_wind_mps, UnitsUtils.round(wind));
    }

    private String getCloudsString(float clouds) {
        return context.getString(R.string.weather_clouds, UnitsUtils.round(clouds));
    }

    public Weather toCacheModel(@NonNull WeatherResponse weatherResponse) {
        return new Weather(
                weatherResponse.getCityId(),
                weatherResponse.getCity(),
                weatherResponse.getDate() * 1000,
                weatherResponse.getWeatherDescription().getId(),
                weatherResponse.getWeatherDescription().getDescription(),
                weatherResponse.getWeatherCondition().getTemperature(),
                weatherResponse.getWeatherCondition().getPressure(),
                weatherResponse.getWeatherCondition().getHumidity(),
                weatherResponse.getWind().getSpeed(),
                weatherResponse.getWind().getDegree(),
                weatherResponse.getClouds().getPercents());
    }

}
