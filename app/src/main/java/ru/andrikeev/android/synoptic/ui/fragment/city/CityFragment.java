package ru.andrikeev.android.synoptic.ui.fragment.city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.presentation.presenter.city.CityPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;

/**
 * Created by overtired on 25.07.17.
 */

public class CityFragment extends BaseFragment<CityView,CityPresenter> implements CityView{
    @Inject
    @InjectPresenter
    CityPresenter presenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_city;
    }

    @Override
    protected CityPresenter providePresenter() {
        return presenter;
    }
}
