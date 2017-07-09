package ru.andrikeev.android.synoptic.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.andrikeev.android.synoptic.R;

/**
 * Настройки приложения.
 */
@Singleton
public class Settings {

    private Context context;

    private SharedPreferences preferences;

    @Inject
    public Settings(@NonNull Context context) {
        this.context = context;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
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
