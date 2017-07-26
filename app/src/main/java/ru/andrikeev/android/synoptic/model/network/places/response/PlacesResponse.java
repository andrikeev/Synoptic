package ru.andrikeev.android.synoptic.model.network.places.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by overtired on 25.07.17.
 */

public class PlacesResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("results")
    private ArrayList<Result> results;

    public ArrayList<Result> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}
