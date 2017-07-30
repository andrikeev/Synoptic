package ru.andrikeev.android.synoptic.presentation.presenter.weather;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.model.repository.Resource;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.WeatherView;

/**
 * Presenter for {@link WeatherView} with
 * {@link com.arellomobile.mvp.viewstate.MvpViewState} for saving the view state.
 */
@InjectViewState
public class WeatherPresenter extends RxPresenter<WeatherView> {

    private WeatherRepository repository;

    @Inject
    WeatherPresenter(@NonNull WeatherRepository repository) {
        this.repository = repository;
    }

    private void loadWeather() {
        getViewState().showLoading();
        repository.loadWeather()
                .subscribe(
                        new Observer<Resource<WeatherModel>>() {
                            @Override
                            public void onSubscribe(Disposable disposable) {
                                subscription = disposable;
                            }

                            @Override
                            public void onNext(Resource<WeatherModel> resource) {
                                switch (resource.getStatus()) {
                                    case SUCCESS:
                                        getViewState().hideLoading();
                                        getViewState().showWeather(resource.getData());
                                        break;
                                    case FETCHING:
                                        getViewState().showWeather(resource.getData());
                                        break;
                                    case ERROR:
                                        getViewState().showFetchingError();
                                        getViewState().hideLoading();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                getViewState().hideLoading();
                                getViewState().showError();
                            }

                            @Override
                            public void onComplete() {
                            }
                        }
                );
    }

    public void fetchWeather() {
        getViewState().showLoading();
        repository.fetchWeather();
    }

    public void fetchWeather(double lon, double lat){
        getViewState().showLoading();
        repository.fetchWeather(lon, lat);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadWeather();
    }
}
