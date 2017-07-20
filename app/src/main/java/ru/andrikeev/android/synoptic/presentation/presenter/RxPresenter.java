package ru.andrikeev.android.synoptic.presentation.presenter;

import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;

import io.reactivex.disposables.Disposable;

/**
 * Base presenter that provides Rx subscription for async operations and automatically
 * dispose subscription when presenter is destroyed.
 */
public abstract class RxPresenter<View extends MvpView> extends MvpPresenter<View> {

    @Nullable
    protected Disposable subscription;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.dispose();
            subscription = null;
        }
    }
}
