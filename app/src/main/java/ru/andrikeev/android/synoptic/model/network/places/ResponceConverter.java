package ru.andrikeev.android.synoptic.model.network.places;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.andrikeev.android.synoptic.model.data.CityModel;
import ru.andrikeev.android.synoptic.model.network.places.response.PlacesResponse;
import ru.andrikeev.android.synoptic.model.network.places.response.Result;

/**
 * Created by overtired on 26.07.17.
 */

public class ResponceConverter {
    public static List<CityModel> toViewModel(@NonNull PlacesResponse response){
        List<CityModel> cities = new ArrayList<>();

        for (Result result:response.getResults()) {
            String cityName = result.getAddress();
            double longitude = result.getGeometry().getLocation().getLongitude();
            double latitude = result.getGeometry().getLocation().getLatitude();

            cities.add(new CityModel(cityName,longitude,latitude));
        }

        return cities;
    }
}
