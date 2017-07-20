package ru.andrikeev.android.synoptic.model.persistence;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.requery.Persistable;
import io.requery.reactivex.ReactiveEntityStore;
import timber.log.Timber;

/**
 * Local storage for cached weather.
 */
@Singleton
public class WeatherDataBase {

    @NonNull
    private ReactiveEntityStore<Persistable> dataStore;

    @Inject
    WeatherDataBase(@NonNull ReactiveEntityStore<Persistable> dataStore) {
        this.dataStore = dataStore;
    }

    @NonNull
    public Observable<Weather> getWeather() {
        return dataStore.select(WeatherEntity.class)
                .get()
                .observable()
                .subscribeOn(Schedulers.single())
                .map(new Function<WeatherEntity, Weather>() {
                    @Override
                    public Weather apply(@NonNull WeatherEntity weatherEntity) throws Exception {
                        Timber.d("Weather restored from cache: %s", weatherEntity);
                        return new Weather(weatherEntity);
                    }
                });
    }

    public void saveOrUpdate(@NonNull Weather weather) {
        dataStore.upsert(new WeatherEntity(weather))
                .subscribeOn(Schedulers.single())
                .subscribe(
                        new Consumer<WeatherEntity>() {
                            @Override
                            public void accept(@NonNull WeatherEntity weatherEntity) throws Exception {
                                Timber.d("Weather cached: %s", weatherEntity);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                Timber.e("Error caching weather", throwable);
                            }
                        }
                );
    }
}
