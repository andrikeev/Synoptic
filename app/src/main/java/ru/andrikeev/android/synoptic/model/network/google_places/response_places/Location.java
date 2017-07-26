package ru.andrikeev.android.synoptic.model.network.google_places.response_places;

import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 26.07.17.
 */

public class Location {
    @SerializedName("lat")
    private double latitude;

    @SerializedName("lng")
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
