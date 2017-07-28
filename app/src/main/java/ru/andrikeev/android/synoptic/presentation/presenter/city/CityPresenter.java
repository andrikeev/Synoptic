package ru.andrikeev.android.synoptic.presentation.presenter.city;


import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.CityResolver;
import ru.andrikeev.android.synoptic.model.ModelsConverter;
import ru.andrikeev.android.synoptic.model.data.PredictionModel;
import ru.andrikeev.android.synoptic.model.network.googleplaces.GooglePlacesService;
import ru.andrikeev.android.synoptic.model.network.googleplaces.responseplaces.Location;
import ru.andrikeev.android.synoptic.model.network.openweather.response.WeatherResponse;
import ru.andrikeev.android.synoptic.model.persistence.Weather;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;

/**
 * Created by overtired on 25.07.17.
 */

@InjectViewState
public class CityPresenter extends RxPresenter<CityView> {

    private Settings settings;

    private CityResolver cityResolver;

    private Consumer<String> consumer = new Consumer<String>() {
        @Override
        public void accept(@NonNull String input) throws Exception {
            cityResolver.loadPredictions(input)
                    .subscribe(new Consumer<List<PredictionModel>>() {
                        @Override
                        public void accept(@NonNull List<PredictionModel> predictionModels) throws Exception {
                            getViewState().updateList(predictionModels);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            getViewState().showError();
                        }
                    });
        }
    };

    @Inject
    public CityPresenter(@NonNull CityResolver cityResolver,
                         @NonNull Settings settings) {
        this.cityResolver = cityResolver;
        this.settings = settings;
    }

    public Consumer<String> getConsumer() {
        return consumer;
    }

    public void loadCity(final String placeId) {
        getViewState().showLoading();
        getViewState().hideKeyboard();

        subscription = cityResolver.loadCityId(placeId)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long cityId) throws Exception {
                        settings.setCityId(cityId);
                        getViewState().hideProgressAndExit();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getViewState().showError();
                        getViewState().hideLoading();
                    }
                });
    }
}
