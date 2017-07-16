package ru.andrikeev.android.synoptic.ui.fragment.weather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import javax.inject.Inject;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.WeatherModel;
import ru.andrikeev.android.synoptic.presentation.presenter.weather.WeatherPresenter;
import ru.andrikeev.android.synoptic.presentation.view.WeatherView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;
import ru.andrikeev.android.synoptic.utils.DateUtils;

public class WeatherFragment extends BaseFragment<WeatherView, WeatherPresenter> implements WeatherView {

    private SwipeRefreshLayout refreshLayout;
    private TextView cityName;
    private ImageView weatherIcon;
    private TextView temperature;
    private TextView shortDescription;
    private TextView description;
    private TextView pressure;
    private TextView humidity;
    private TextView wind;
    private TextView lastUpdate;

    @Inject
    @InjectPresenter
    WeatherPresenter presenter;

    @ProvidePresenter
    protected WeatherPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_weather;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityName = view.findViewById(R.id.cityName);
        weatherIcon = view.findViewById(R.id.weatherIcon);
        temperature = view.findViewById(R.id.temperature);
        shortDescription = view.findViewById(R.id.shortDescription);
        description = view.findViewById(R.id.description);
        pressure = view.findViewById(R.id.pressure);
        humidity = view.findViewById(R.id.humidity);
        wind = view.findViewById(R.id.wind);
        lastUpdate = view.findViewById(R.id.lastUpdate);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.fetchWeather();
            }
        });
    }

    @Override
    public void showLoading() {
        if (!refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showWeather(WeatherModel model) {
        cityName.setText(model.getCityName());
        shortDescription.setText(model.getShortDescription());
        description.setText(model.getDescription());
        temperature.setText(String.valueOf(model.getTemperature()));
        pressure.setText(String.valueOf(model.getPressure()));
        humidity.setText(String.valueOf(model.getHumidity()));
        wind.setText(String.valueOf(model.getWindSpeed()));
        lastUpdate.setText(DateUtils.formatDate(model.getDate()));

        switch (model.getWeatherId()) {
            // TODO: set icon
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show(); // TODO: show error
    }

    @Override
    public void showFetchingError() {
        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show(); // TODO: show error
    }

    public static WeatherFragment create() {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
