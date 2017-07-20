package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import io.requery.Entity;
import io.requery.Key;
import io.requery.Table;

/**
 * Сущность - текущее состояние погоды.
 */
@Entity
@Table(name = "weather")
public class Weather {

    /**
     * Идентификатор погоды - совпадает с идентификатором города.
     */
    @Key
    protected long cityId;

    /**
     * Название города.
     */
    protected String cityName;

    /**
     * Дата и время.
     */
    protected long timestamp;

    /**
     * Идентифиактор состояния погоды.
     */
    protected int weatherId;

    /**
     * Краткое описание.
     */
    protected String shortDescription;

    /**
     * Полное описание.
     */
    protected String description;

    /**
     * Температура.
     */
    protected float temperature;

    /**
     * Давление.
     */
    protected float pressure;

    /**
     * Влажность.
     */
    protected float humidity;

    /**
     * Скорость ветра.
     */
    protected float windSpeed;

    /**
     * Направление (азимут) ветра.
     */
    protected float windDegree;

    /**
     * Облачность в процентах.
     */
    protected float clouds;

    public long getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public long getTimestamp() {
        return timestamp;
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

    public float getWindDegree() {
        return windDegree;
    }

    public float getClouds() {
        return clouds;
    }

    protected Weather() {
    }

    protected Weather(@NonNull Weather weather) {
        this.cityId = weather.getCityId();
        this.cityName = weather.getCityName();
        this.timestamp = weather.getTimestamp();
        this.weatherId = weather.getWeatherId();
        this.shortDescription = weather.getShortDescription();
        this.description = weather.getDescription();
        this.temperature = weather.getTemperature();
        this.pressure = weather.getPressure();
        this.humidity = weather.getHumidity();
        this.windSpeed = weather.getWindSpeed();
        this.windDegree = weather.getWindDegree();
        this.clouds = weather.getClouds();
    }

    public Weather(long cityId,
                   @NonNull String cityName,
                   long timestamp,
                   int weatherId,
                   @NonNull String shortDescription,
                   @NonNull String description,
                   float temperature,
                   float pressure,
                   float humidity,
                   float windSpeed,
                   float windDegree,
                   float clouds) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.timestamp = timestamp;
        this.weatherId = weatherId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.clouds = clouds;
    }
}
