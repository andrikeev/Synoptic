package ru.andrikeev.android.synoptic.di;

import dagger.Binds;
import dagger.Module;
import ru.andrikeev.android.synoptic.model.ModelsConverter;
import ru.andrikeev.android.synoptic.model.ModelsConverterImpl;
import ru.andrikeev.android.synoptic.model.network.OpenWeatherService;
import ru.andrikeev.android.synoptic.model.network.RemoteService;
import ru.andrikeev.android.synoptic.model.persistence.CacheService;
import ru.andrikeev.android.synoptic.model.persistence.WeatherDataStore;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepository;
import ru.andrikeev.android.synoptic.model.repository.WeatherRepositoryImpl;

/**
 * Module for bindings between interfaces and implementations.
 */
@Module
abstract class BindsModule {

    @Binds
    abstract RemoteService bindRemoteService(OpenWeatherService service);

    @Binds
    abstract CacheService bindCacheService(WeatherDataStore store);

    @Binds
    abstract ModelsConverter bindModelsConverter(ModelsConverterImpl converter);

    @Binds
    abstract WeatherRepository bindWeatherRepository(WeatherRepositoryImpl repository);
}
