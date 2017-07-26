package ru.andrikeev.android.synoptic.model.network.places.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 26.07.17.
 */

public class Result {
    @SerializedName("formatted_address")
    private String address;

    @SerializedName("geometry")
    private Geometry geometry;

    public String getAddress() {
        return address;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
