package ru.andrikeev.android.synoptic.model.data;

import android.support.annotation.NonNull;

/**
 * Created by overtired on 26.07.17.
 */

public class CityModel {
    @NonNull
    private String name;

    private double longitude;

    private double latitude;

    public CityModel(@NonNull String name, double longitude, double latitude){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
