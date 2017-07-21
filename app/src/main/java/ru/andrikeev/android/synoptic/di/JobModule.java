package ru.andrikeev.android.synoptic.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.evernote.android.job.JobCreator;
import com.evernote.android.job.JobManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module for jobs dependencies.
 */
@Module
final class JobModule {

    @Provides
    @Singleton
    @NonNull
    JobManager provideJobManager(@NonNull Context context, @NonNull JobCreator creator) {
        JobManager manager = JobManager.create(context);
        manager.addJobCreator(creator);
        return manager;
    }
}

