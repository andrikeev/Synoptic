package ru.andrikeev.android.synoptic.model.network.google_places;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.andrikeev.android.synoptic.model.data.PredictionModel;
import ru.andrikeev.android.synoptic.model.network.google_places.response_places.PlacesResponse;
import ru.andrikeev.android.synoptic.model.network.google_places.response_predictions.Prediction;
import ru.andrikeev.android.synoptic.model.network.google_places.response_predictions.PredictionsResponse;

/**
 * Created by overtired on 26.07.17.
 */

public class ResponceConverter {
    public static List<PredictionModel> toViewModel(@NonNull PredictionsResponse response){
        List<PredictionModel> predictions = new ArrayList<>();

        for(Prediction prediction:response.getPredictions()){
            String name = prediction.getDescription();
            String placeId = prediction.getPlaceId();

            predictions.add(new PredictionModel(placeId,name));
        }

        return predictions;
    }
}
