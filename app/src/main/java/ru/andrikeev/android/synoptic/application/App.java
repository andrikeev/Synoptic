package ru.andrikeev.android.synoptic.application;

import android.app.Activity;
import android.app.Application;

import com.evernote.android.job.JobManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import ru.andrikeev.android.synoptic.BuildConfig;
import ru.andrikeev.android.synoptic.di.AppInjector;
import ru.andrikeev.android.synoptic.model.sync.FetchWeatherJob;
import timber.log.Timber;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;
import static android.support.v7.app.AppCompatDelegate.setDefaultNightMode;


/**
 * Real application class.
 */
public final class App extends Application implements HasActivityInjector {

    @Inject
    protected Settings settings;

    @Inject
    protected DispatchingAndroidInjector<Activity> injector;

    @Inject
    protected JobManager jobManager;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return injector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        AppInjector.init(this);

        setDefaultNightMode(settings.isNightMode() ? MODE_NIGHT_YES : MODE_NIGHT_NO);

        initScheduledJob();
    }

    private void initScheduledJob() {
        jobManager.cancelAll();
        if (settings.isSyncEnabled() && jobManager.getAllJobRequestsForTag(FetchWeatherJob.TAG).size() == 0) {
            FetchWeatherJob.scheduleJob(settings.getSyncInterval());
        }
    }
}
