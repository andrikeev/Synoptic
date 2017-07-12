package ru.andrikeev.android.synoptic.ui.activity.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.ui.activity.BaseActivity;

/**
 * Экран "О приложении"
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static Intent getIntent(@NonNull Context context) {
        return new Intent(context, AboutActivity.class);
    }
}
