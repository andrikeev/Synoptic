package ru.andrikeev.android.synoptic.model.network;

import io.reactivex.Single;
import ru.andrikeev.android.synoptic.model.network.response.WeatherResponse;

/**
 * Interface for remote (network) service.
 */
public interface RemoteService {

    /**
     * Load current weather for city with given id.
     *
     * @param cityId city id
     * @return {@link Single} with loaded {@link WeatherResponse}
     */
    Single<WeatherResponse> getWeather(long cityId);
}
