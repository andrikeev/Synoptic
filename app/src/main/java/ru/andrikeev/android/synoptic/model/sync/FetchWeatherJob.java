package ru.andrikeev.android.synoptic.model.sync;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import timber.log.Timber;

/**
 * Background fetching weather job.
 */
public class FetchWeatherJob extends Job {

    public static final String TAG = "SyncWeatherJob";

    private WeatherRepository repository;

    FetchWeatherJob(@NonNull WeatherRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    protected Result onRunJob(Params params) {
        Timber.d("Fetching weather");

        repository.fetchWeather();

        return Result.SUCCESS;
    }

    public static int scheduleJob(int millis) {
        return new JobRequest.Builder(TAG)
                .setPeriodic(millis)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setUpdateCurrent(true)
                .setPersisted(true)
                .build()
                .schedule();
    }
}
