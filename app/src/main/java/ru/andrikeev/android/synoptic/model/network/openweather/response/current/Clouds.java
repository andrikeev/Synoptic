package ru.andrikeev.android.synoptic.model.network.openweather.response.current;

import com.google.gson.annotations.SerializedName;

/**
 * Clouds.
 */
public class Clouds {

    @SerializedName("all")
    private float percents;

    public float getPercents() {
        return percents;
    }
}
