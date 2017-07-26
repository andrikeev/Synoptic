package ru.andrikeev.android.synoptic.ui.fragment.city;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import ru.andrikeev.android.synoptic.R;
import ru.andrikeev.android.synoptic.presentation.presenter.city.CityPresenter;
import ru.andrikeev.android.synoptic.presentation.view.CityView;
import ru.andrikeev.android.synoptic.ui.fragment.BaseFragment;

/**
 * Created by overtired on 25.07.17.
 */

public class CityFragment extends BaseFragment<CityView,CityPresenter> implements CityView{
    private EditText editText;

    @Inject
    @InjectPresenter
    CityPresenter presenter;

    @ProvidePresenter
    protected CityPresenter provideCityPresenter(){
        return presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.edit_city);

        Disposable disposable = RxTextView.textChanges(editText)
                .debounce(400, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.length()>0;
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
    public void updateList() {
        //TODO:List with suggestions
    }
}
