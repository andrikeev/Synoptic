package ru.andrikeev.android.synoptic.presentation.presenter.city;


import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.CityResolver;
import ru.andrikeev.android.synoptic.model.data.SuggestionModel;
import ru.andrikeev.android.synoptic.presentation.presenter.RxPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;

/**
 * Created by overtired on 25.07.17.
 */

@InjectViewState
public class CityPresenter extends RxPresenter<CityView> {

    private Settings settings;

    private CityResolver cityResolver;

    private Disposable textChangedSubscription;

    @Inject
    public CityPresenter(@NonNull CityResolver cityResolver,
                         @NonNull Settings settings) {
        this.cityResolver = cityResolver;
        this.settings = settings;
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

    public void onTextChanged(Observable<CharSequence> observable){
        textChangedSubscription = observable
                .debounce(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.length() > 0;
                    }
                }).map(new Function<CharSequence, String>() {

                    @Override
                    public String apply(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.toString();
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String input) throws Exception {
                        cityResolver.loadPredictions(input)
                                .subscribe(new Consumer<List<SuggestionModel>>() {
                                    @Override
                                    public void accept(@NonNull List<SuggestionModel> suggestionModels) throws Exception {
                                        getViewState().updateList(suggestionModels);
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(@NonNull Throwable throwable) throws Exception {
                                        getViewState().showError();
                                    }
                                });
                    }
                });
    }

    public void onDestroyView(){
        if(textChangedSubscription!=null){
            textChangedSubscription.dispose();
            textChangedSubscription = null;
        }
    }
}
