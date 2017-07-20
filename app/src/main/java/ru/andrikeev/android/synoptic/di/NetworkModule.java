package ru.andrikeev.android.synoptic.di;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.andrikeev.android.synoptic.BuildConfig;
import ru.andrikeev.android.synoptic.model.network.OpenWeatherApi;

import static java.util.concurrent.TimeUnit.SECONDS;
import static ru.andrikeev.android.synoptic.model.network.OpenWeatherService.API_KEY_NAME;

/**
 * Module for network dependencies.
 */
@Module
final class NetworkModule {

    private static final String BASE_URL_NAME = "base_url";

    /**
     * API key for Open Weather Api
     */
    @Provides
    @Singleton
    @Named(API_KEY_NAME)
    @NonNull
    String provideApiKey() {
        return BuildConfig.API_KEY;
    }

    /**
     * Base API URL.
     */
    @Provides
    @Singleton
    @Named(BASE_URL_NAME)
    @NonNull
    String provideBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    /**
     * Provides OkHttpClient for http requests with cache.
     *
     * @param cache filesystem cache storage
     * @return http client
     */
    @Provides
    @Singleton
    @NonNull
    OkHttpClient provideOkHttpClient(@NonNull Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(10L, SECONDS)
                .build();
    }

    /**
     * Provides filesystem cache of 10Mb in {@link Context#getCacheDir()}
     *
     * @param context application context
     * @return filesystem cache of 10Mb in {@link Context#getCacheDir()}
     */
    @Provides
    @Singleton
    @NonNull
    Cache provideCache(@NonNull Context context) {
        return new Cache(context.getCacheDir(), 10 * 1024 * 1024);
    }

    /**
     * Provides retrofit implementation of {@link OpenWeatherApi}
     *
     * @param baseUrl base URL
     * @param client  http client
     * @return retrofit api implementation
     */
    @Provides
    @Singleton
    @NonNull
    OpenWeatherApi provideApi(@NonNull @Named(BASE_URL_NAME) String baseUrl,
                              @NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApi.class);
    }
}
