package ru.andrikeev.android.synoptic.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.andrikeev.android.synoptic.ui.fragment.weather.WeatherFragment;


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract WeatherFragment contributeWeatherFragment();
}
