package ru.andrikeev.android.synoptic.model.data;

/**
 * Created by overtired on 26.07.17.
 */

public class SuggestionModel {
    public SuggestionModel(String placeID, String name){
        this.placeID = placeID;
        this.name = name;
    }

    private String placeID;
    private String name;

    public String getName() {
        return name;
    }

    public String getPlaceID() {
        return placeID;
    }
}
