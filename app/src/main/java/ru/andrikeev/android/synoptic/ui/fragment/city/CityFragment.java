package ru.andrikeev.android.synoptic.ui.fragment.city;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.CityModel;
import ru.andrikeev.android.synoptic.presentation.presenter.city.CityPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;
import ru.andrikeev.android.synoptic.ui.fragment.weather.WeatherFragment;

/**
 * Created by overtired on 25.07.17.
 */

public class CityFragment extends BaseFragment<CityView, CityPresenter> implements CityView {
    private EditText editText;
    private RecyclerView recyclerView;

    @Inject
    @InjectPresenter
    CityPresenter presenter;

    @ProvidePresenter
    protected CityPresenter provideCityPresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_city);
        recyclerView = view.findViewById(R.id.recycler_city);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //TODO: Dispose?
        Disposable disposable = RxTextView.textChanges(editText)
                .debounce(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.length() > 0;
                    }
                }).map(new Function<CharSequence, String>() {

                    @Override
                    public String apply(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.toString();
                    }
                }).subscribe(presenter.getConsumer());
    }

    @Override
    protected int getResourceId() {
        return R.layout.fragment_city;
    }

    @Override
    protected CityPresenter providePresenter() {
        return presenter;
    }

    @Override
    public void updateList(@android.support.annotation.NonNull List<CityModel> cities) {
        recyclerView.setAdapter(new CityAdapter(cities));
    }

    private class CityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CityModel city;
        private TextView textView;

        public CityHolder(View view){
            super(view);
            textView = view.findViewById(R.id.city_text);
            view.setOnClickListener(this);
        }

        public void setCity(CityModel city){
            this.city = city;
            updateHolder();
        }

        private void updateHolder(){
            textView.setText(city.getName());
        }

        @Override
        public void onClick(View view) {
            getActivity().setResult(Activity.RESULT_OK,
                    WeatherFragment.getResultIntent(city.getLongitude(),city.getLatitude()));
            getActivity().finish();
            //TODO:return result
        }
    }

    private class CityAdapter extends RecyclerView.Adapter<CityHolder>{

        private List<CityModel> cities;

        public CityAdapter(List<CityModel> cities){
            this.cities = cities;
        }

        @Override
        public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.item_city,parent,false);
            return new CityHolder(v);
        }

        @Override
        public void onBindViewHolder(CityHolder holder, int position) {
            holder.setCity(cities.get(position));
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }
    }
}
