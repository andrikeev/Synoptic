package ru.andrikeev.android.synoptic.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.andrikeev.android.synoptic.application.Settings;

/**
 * Компонент для предоставления зависимостей.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Settings provideSettings();
}
