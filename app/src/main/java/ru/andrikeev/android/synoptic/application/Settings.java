package ru.andrikeev.android.synoptic.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import ru.andrikeev.android.synoptic.R;

/**
 * Настройки приложения.
 */
@Singleton
public class Settings {

    public static final String USER_PREFERENCES = "user_preferences";

    private SharedPreferences preferences;

    private Context context;

    @Inject
    public Settings(@NonNull @Named(USER_PREFERENCES) SharedPreferences preferences,
                    @NonNull Context context) {
        this.preferences = preferences;
        this.context = context;
    }

    /**
     * Настройки темы приложения.
     *
     * @return true, если должна отображаться тёмная тема, false - если светлая
     */
    public boolean isNightMode() {
        return preferences.getBoolean(context.getString(R.string.pref_night_mode_key), false);
    }
}
