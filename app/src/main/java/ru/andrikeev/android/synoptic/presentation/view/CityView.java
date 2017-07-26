package ru.andrikeev.android.synoptic.presentation.view;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import ru.andrikeev.android.synoptic.model.data.PredictionModel;

/**
 * Created by overtired on 25.07.17.
 */

public interface CityView extends MvpView{
    void updateList(@NonNull List<PredictionModel> cities);
    void showLoading();
    void hideLoading();
    void hideProgressAndExit();
    void hideKeyboard();
}
