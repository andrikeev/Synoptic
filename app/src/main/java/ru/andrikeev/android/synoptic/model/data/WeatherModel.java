package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Weather model for UI.
 */
public class WeatherModel {

    /**
     * Название города.
     */
    private String cityName;

    /**
     * Дата и время.
     */
    private Date date;

    /**
     * Идентифиактор состояния погоды.
     */
    private int weatherId;

    /**
     * Краткое описание.
     */
    private String shortDescription;

    /**
     * Полное описание.
     */
    private String description;

    /**
     * Температура.
     */
    private int temperature;

    /**
     * Давление.
     */
    private int pressure;

    /**
     * Влажность.
     */
    private int humidity;

    /**
     * Скорость ветра.
     */
    private int windSpeed;

    /**
     * Направление ветра.
     */
    private WindDirection windDirection;

    /**
     * Облачность в процентах.
     */
    private int clouds;

    public WeatherModel(@NonNull String cityName,
                        @NonNull Date date,
                        int weatherId,
                        @NonNull String shortDescription,
                        @NonNull String description,
                        int temperature,
                        int pressure,
                        int humidity,
                        int windSpeed,
                        @NonNull WindDirection windDirection,
                        int clouds) {
        this.cityName = cityName;
        this.date = date;
        this.weatherId = weatherId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.clouds = clouds;
    }

    public String getCityName() {
        return cityName;
    }

    public Date getDate() {
        return date;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public float getClouds() {
        return clouds;
    }

    public enum WindDirection {
        NORTH,
        NORTH_EAST,
        EAST,
        SOUTH_EAST,
        SOUTH,
        SOUTH_WEST,
        WEST,
        NORTH_WEST
    }
}
