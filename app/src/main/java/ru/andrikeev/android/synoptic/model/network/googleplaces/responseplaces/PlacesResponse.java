package ru.andrikeev.android.synoptic.model.network.googleplaces.responseplaces;

import com.google.gson.annotations.SerializedName;

/**
 * Created by overtired on 25.07.17.
 */

public class PlacesResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private Result resultPlace;

    public Result getResultPlace() {
        return resultPlace;
    }

    public String getStatus() {
        return status;
    }
}
