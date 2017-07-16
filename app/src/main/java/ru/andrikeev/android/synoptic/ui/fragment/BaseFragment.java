package ru.andrikeev.android.synoptic.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import ru.andrikeev.android.synoptic.di.Injectable;

/**
 * Base MVP Fragment.
 */
public abstract class BaseFragment<View extends MvpView, Presenter extends MvpPresenter<View>>
        extends MvpAppCompatFragment implements Injectable {

    protected abstract int getResourceId();

    protected abstract Presenter providePresenter();

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater,
                                          @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getResourceId(), container, false);
    }
}
