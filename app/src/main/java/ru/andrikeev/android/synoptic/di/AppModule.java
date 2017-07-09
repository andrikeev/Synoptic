package ru.andrikeev.android.synoptic.di;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль для предоставления зависимостей, связанных с контекстом приложения.
 */
@Module
final public class AppModule {

    @NonNull
    private Application application;

    public AppModule(@NonNull final Application application) {
        this.application = application;
    }

    /**
     * Предоставляет контекст приложения.
     *
     * @return контекст приложения
     */
    @Provides
    @Singleton
    @NonNull
    public Context provideContext() {
        return application.getApplicationContext();
    }
}
