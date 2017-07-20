package ru.andrikeev.android.synoptic.di;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import ru.andrikeev.android.synoptic.application.App;

/**
 * Application component for dependencies injection.
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        NetworkModule.class,
        JobModule.class,
        ActivityModule.class
})
interface AppComponent extends AndroidInjector<App> {

    @Override
    void inject(App application);
}
