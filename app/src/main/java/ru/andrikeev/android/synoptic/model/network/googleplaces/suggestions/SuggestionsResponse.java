package ru.andrikeev.android.synoptic.model.network.googleplaces.suggestions;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by overtired on 26.07.17.
 */

public class SuggestionsResponse {
    @SerializedName("predictions")
    private List<Suggestion> suggestions;

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }
}
