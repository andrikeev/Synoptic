package ru.andrikeev.android.synoptic.di;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import ru.andrikeev.android.synoptic.application.App;
import ru.andrikeev.android.synoptic.ui.activity.BaseActivity;

/**
 * Helper class for building dependency graph.
 */
public class AppInjector {

    private AppInjector() {
    }

    public static void init(@NonNull App application) {
        DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build()
                .inject(application);

        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity instanceof BaseActivity) {
                    AndroidInjection.inject(activity);
                }

                if (activity instanceof FragmentActivity) {
                    ((FragmentActivity) activity).getSupportFragmentManager()
                            .registerFragmentLifecycleCallbacks(
                                    new FragmentManager.FragmentLifecycleCallbacks() {

                                        @Override
                                        public void onFragmentAttached(FragmentManager fragmentManager,
                                                                       Fragment fragment,
                                                                       Context context) {
                                            if (fragment instanceof Injectable) {
                                                AndroidSupportInjection.inject(fragment);
                                            }
                                        }
                                    }, true);
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }
}
