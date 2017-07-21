package ru.andrikeev.android.synoptic.model.sync;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

import ru.andrikeev.android.synoptic.model.repository.WeatherRepositoryImpl;

/**
 * Job creator.
 */
public class JobCreatorImpl implements JobCreator {

    @NonNull
    private WeatherRepositoryImpl repository;

    public JobCreatorImpl(@NonNull WeatherRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public Job create(String tag) {
        switch (tag) {
            case FetchWeatherJob.TAG:
                return new FetchWeatherJob(repository);
            default:
                return null;
        }
    }
}
