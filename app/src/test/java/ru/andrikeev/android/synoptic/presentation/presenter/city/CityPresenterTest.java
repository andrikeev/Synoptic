package ru.andrikeev.android.synoptic.presentation.presenter.city;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Emitter;
import io.reactivex.FlowableEmitter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import ru.andrikeev.android.synoptic.application.Settings;
import ru.andrikeev.android.synoptic.model.CityResolver;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by overtired on 30.07.17.
 */
public class CityPresenterTest {

    private CityPresenter presenter;
    private CityResolver resolver;
    private Settings settings;


    @Before
    public void prepare(){
        resolver = mock(CityResolver.class);
        settings = mock(Settings.class);

        presenter = new CityPresenter(resolver,settings);
    }

    @Test
    public void inputTextProcessing(){
        presenter.onTextChanged(Observable.<CharSequence>empty().observeOn(Schedulers.trampoline()));


        //verify(presenter).loadCity("1235");
    }
}