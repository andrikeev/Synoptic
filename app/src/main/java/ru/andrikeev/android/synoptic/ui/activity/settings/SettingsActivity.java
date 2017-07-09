package ru.andrikeev.android.synoptic.ui.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.ui.activity.BaseActivity;
import ru.andrikeev.android.synoptic.ui.activity.main.MainActivity;

/**
 * Экран настроек приложения.
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(MainActivity.getIntent(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                startActivity(MainActivity.getIntent(this));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static Intent getIntent(@NonNull Context context) {
        return new Intent(context, SettingsActivity.class);
    }
}
