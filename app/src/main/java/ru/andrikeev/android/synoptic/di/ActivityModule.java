package ru.andrikeev.android.synoptic.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.andrikeev.android.synoptic.ui.activity.about.AboutActivity;
import ru.andrikeev.android.synoptic.ui.activity.city.CityActivity;
import ru.andrikeev.android.synoptic.ui.activity.main.MainActivity;
import ru.andrikeev.android.synoptic.ui.activity.settings.SettingsActivity;

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract SettingsActivity contributeSettingsActivity();

    @ContributesAndroidInjector
    abstract AboutActivity contributeAboutActivity();

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract CityActivity contributeCityActivity();
}
