package ru.andrikeev.android.synoptic.model.network.googleplaces.responsepredictions;

import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 26.07.17.
 */

public class Prediction {
    @SerializedName("description")
    private String description;
    @SerializedName("place_id")
    private String placeId;

    public String getPlaceId() {
        return placeId;
    }

    public String getDescription() {
        return description;
    }
}
