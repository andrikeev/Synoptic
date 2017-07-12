package ru.andrikeev.android.synoptic.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static ru.andrikeev.android.synoptic.application.Settings.USER_PREFERENCES;

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
    Context provideContext() {
        return application.getApplicationContext();
    }

    /**
     * Предоставляет хранилище для пользовательских настроек приложения.
     *
     * @return приватное хранилище для настроек пользователя
     */
    @Provides
    @Singleton
    @Named(USER_PREFERENCES)
    @NonNull
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }
}
