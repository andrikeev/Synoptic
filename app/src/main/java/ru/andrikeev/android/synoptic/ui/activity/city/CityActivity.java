package ru.andrikeev.android.synoptic.ui.activity.city;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.ui.activity.BaseActivity;

/**
 * Created by overtired on 25.07.17.
 */

public class CityActivity extends BaseActivity implements HasSupportFragmentInjector {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Inject
    protected DispatchingAndroidInjector<Fragment> injector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }


    public static Intent getIntent(@NonNull Context context){
        return new Intent(context,CityActivity.class);
    }
}
