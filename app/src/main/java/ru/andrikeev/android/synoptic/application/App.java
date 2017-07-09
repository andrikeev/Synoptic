package ru.andrikeev.android.synoptic.application;

import android.app.Application;
import android.support.annotation.NonNull;

import ru.andrikeev.android.synoptic.di.AppComponent;
import ru.andrikeev.android.synoptic.di.AppModule;
import ru.andrikeev.android.synoptic.di.DaggerAppComponent;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;
import static android.support.v7.app.AppCompatDelegate.setDefaultNightMode;


/**
 * Реальный класс приложения. Нужен для предоставления завиосимостей, связанных с контекстом приложения.
 */
public final class App extends Application {

    /**
     * Граф зависимостей.
     */
    @NonNull
    @SuppressWarnings("NullableProblems")
    private AppComponent dependencyGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        dependencyGraph = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        setDefaultNightMode(dependencyGraph.provideSettings().isNightMode() ? MODE_NIGHT_YES : MODE_NIGHT_NO);
    }

    @NonNull
    public AppComponent getDependencyGraph() {
        return dependencyGraph;
    }
}
