package ru.andrikeev.android.synoptic.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.application.Settings;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;

/**
 * Базовый класс для экранов с выставлением нужной темы.
 */
public class BaseActivity extends AppCompatActivity {

    @Inject
    protected Settings settings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(settings.isNightMode() ? MODE_NIGHT_YES : MODE_NIGHT_NO);
    }
}
