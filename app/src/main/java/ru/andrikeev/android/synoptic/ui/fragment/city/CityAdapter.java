package ru.andrikeev.android.synoptic.ui.fragment.city;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.model.data.PredictionModel;
import ru.andrikeev.android.synoptic.presentation.presenter.city.CityPresenter;

/**
 * Created by overtired on 28.07.17.
 */

public class CityAdapter extends Adapter<CityAdapter.CityHolder>{

    private List<PredictionModel> cities;
    private Context context;
    private CityPresenter presenter;

    public CityAdapter(Context context, CityPresenter presenter){
        this.cities = new ArrayList<>();
        this.context = context;
        this.presenter = presenter;
    }

    public void add(List<PredictionModel> cities){
        this.cities.addAll(cities);
    }

    public void clear(){
        this.cities.clear();
    }

    @Override
    public CityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
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

    public class CityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private PredictionModel city;
        private TextView textView;

        public CityHolder(View view){
            super(view);
            textView = view.findViewById(R.id.city_text);
            view.setOnClickListener(this);
        }

        public void setCity(PredictionModel city){
            this.city = city;
            updateHolder();
        }

        private void updateHolder(){
            textView.setText(city.getName());
        }

        @Override
        public void onClick(View view) {
            presenter.loadCity(city.getPlaceID());
        }
    }
}
