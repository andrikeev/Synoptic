package ru.andrikeev.android.synoptic.model.network.googleplaces.responsepredictions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by overtired on 26.07.17.
 */

public class PredictionsResponse {
    @SerializedName("predictions")
    private List<Prediction> predictions;

    public List<Prediction> getPredictions() {
        return predictions;
    }
}
