package ru.andrikeev.android.synoptic.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.andrikeev.android.synoptic.R;

/**
 * Application settings.
 */
@Singleton
public class Settings {

    private SharedPreferences preferences;

    private Context context;

    @Inject
    public Settings(@NonNull SharedPreferences preferences,
                    @NonNull Context context) {
        this.preferences = preferences;
        this.context = context;
    }

    /**
     * Theme settings.
     */
    public boolean isNightMode() {
        return preferences.getBoolean(context.getString(R.string.pref_night_mode_key),
                context.getResources().getBoolean(R.bool.pref_night_mode_default));
    }

    /**
     * Weather synchronization interval.
     */
    public int getSyncInterval() {
        // WA for ListPreferenceCompat, it use String for both descriptions and values
        return Integer.valueOf(preferences.getString(context.getString(R.string.pref_sync_weather_interval_key),
                context.getString(R.string.pref_sync_weather_interval_default)));
    }

    /**
     * Is background weather synchronization enabled.
     */
    public boolean isSyncEnabled() {
        return preferences.getBoolean(context.getString(R.string.pref_sync_weather_key),
                context.getResources().getBoolean(R.bool.pref_sync_weather_default));
    }
}
