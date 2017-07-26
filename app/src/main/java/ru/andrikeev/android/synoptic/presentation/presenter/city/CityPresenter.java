package ru.andrikeev.android.synoptic.presentation.presenter.city;


import android.util.Log;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.andrikeev.android.synoptic.model.network.places.GooglePlacesService;
import ru.andrikeev.android.synoptic.model.network.places.response.PlacesResponse;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;

/**
 * Created by overtired on 25.07.17.
 */

@InjectViewState
public class CityPresenter extends RxPresenter<CityView>{

    GooglePlacesService service;

    private Consumer<String> consumer = new Consumer<String>() {
        @Override
        public void accept(@NonNull String s) throws Exception {
            Log.d("Hello!","Rx works!");

            service.loadPlaces(s)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<PlacesResponse>() {
                        @Override
                        public void accept(@NonNull PlacesResponse placesResponse) throws Exception {
                            //UpdateList
                            Log.d("Consumer", "Accepted updating");
                        }
                    });
        }
    };

    @Inject
    public CityPresenter(@NonNull GooglePlacesService service){
        this.service = service;
    }

    public Consumer<String> getConsumer() {
        return consumer;
    }
}
