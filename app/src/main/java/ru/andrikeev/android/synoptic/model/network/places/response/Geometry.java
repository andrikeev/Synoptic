package ru.andrikeev.android.synoptic.model.network.places.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 26.07.17.
 */

public class Geometry {
    @SerializedName("location")
    private Location location;

    public Location getLocation() {
        return location;
    }
}
