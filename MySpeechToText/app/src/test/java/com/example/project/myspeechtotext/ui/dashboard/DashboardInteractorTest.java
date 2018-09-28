package com.example.project.myspeechtotext.ui.dashboard;

import android.app.Activity;
import android.content.Context;

import com.example.project.myspeechtotext.local_db.DictionaryDBHelper;
import com.example.project.myspeechtotext.network.RetrofitService;
import com.example.project.myspeechtotext.ui.dashboard.entity.model.RawDictionary;
import com.example.project.myspeechtotext.ui.dashboard.entity.response.Dictionary;
import com.example.project.myspeechtotext.ui.dashboard.entity.response.DictionaryResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardInteractorTest {
    @Mock
    RetrofitService retrofitService;
    @Mock
    DashboardContainer.Interactor interactor;
    @Mock
    DashboardActivity activity;
    @Mock
    DashBoardPresenter presenter;
    @Mock
    DashboardContainer.View view;
    @Mock
    private DictionaryDBHelper mDbHelper;
    @Mock
    InOrder inOrder;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new DashBoardPresenter(view, activity);
        Schedulers.immediate();
        Schedulers.immediate();
    }

    @Test
    public void fetchRemoteDataShouldLoadIntoView() {
        List<Dictionary> dictionarylist = new ArrayList<>();
        Dictionary dictionary = new Dictionary();
        dictionary.setWord("Hello");
        dictionary.setFrequency(10);
        dictionarylist.add(dictionary);

        DictionaryResponse dictionaryResponse  = new DictionaryResponse();
        dictionaryResponse.setDictionary(dictionarylist);

        when(retrofitService.getDictionaryRawdata())
                .thenReturn(Observable.just(dictionaryResponse));


        DashboardInteractor dashboardInteractor = new DashboardInteractor(
                this.interactor,
                this.activity

        );


        dashboardInteractor.loadData();

    }

    @Test
    public void fetchingEorrorShouldreturn(){
        Exception exception = new Exception();
        when(retrofitService.getDictionaryRawdata())
                .thenReturn(Observable.<DictionaryResponse>error(exception));

        DashboardInteractor dashboardInteractor = new DashboardInteractor(
                this.interactor,
                this.activity
        );

        dashboardInteractor.loadData();

    }
}