package ru.andrikeev.android.synoptic.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.model.sync.JobCreatorImpl;

/**
 * Module for jobs dependencies.
 */
@Module
final class JobModule {

    @Provides
    @Singleton
    @NonNull
    JobManager provideJobManager(@NonNull Context context, @NonNull JobCreatorImpl creator) {
        JobManager manager = JobManager.create(context);
        manager.addJobCreator(creator);
        return manager;
    }

    @Provides
    @Singleton
    @NonNull
    JobCreatorImpl provideWeatherJobCreator(@NonNull WeatherRepository repository) {
        return new JobCreatorImpl(repository);
    }
}

