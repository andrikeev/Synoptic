package ru.andrikeev.android.synoptic.model.network.response.current;

import com.google.gson.annotations.SerializedName;

/**
 * Weather description.
 */
public class WeatherDescription {

    @SerializedName("id")
    private long id;

    @SerializedName("main")
    private String shortDescription;

    @SerializedName("description")
    private String description;

    public long getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }
}
