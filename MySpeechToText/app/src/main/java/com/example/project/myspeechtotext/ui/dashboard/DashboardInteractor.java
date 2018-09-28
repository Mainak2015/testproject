package com.example.project.myspeechtotext.ui.dashboard;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.project.myspeechtotext.network.WebServiceCall;
import com.example.project.myspeechtotext.ui.dashboard.entity.model.RawDictionary;
import com.example.project.myspeechtotext.ui.dashboard.entity.response.DictionaryResponse;
import com.example.project.myspeechtotext.local_db.DictionaryDBHelper;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DashboardInteractor {
    DashboardContainer.Interactor interactor;
    DashboardActivity activity;
    @NonNull
    private DictionaryDBHelper mDbHelper;


    public DashboardInteractor(DashboardContainer.Interactor interactor, DashboardActivity mActivity) {
        this.interactor = interactor;
        activity = mActivity;


    }

    public void getdbData(){
        mDbHelper = DictionaryDBHelper.getInstance(activity.getApplicationContext());
        List<RawDictionary> rawDictionaryList = mDbHelper.getDictionarDbData();
        if(rawDictionaryList == null || rawDictionaryList.size()==0){
            loadData();
        }else{
            interactor.onFetchSuccess( mDbHelper.getDictionarDbData() );
        }
    }

    public void loadData(){
        Subscription subscription = WebServiceCall.service().getDictionaryRawdata()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DictionaryResponse>() {
                    @Override
                    public void onCompleted() {
                        interactor.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        interactor.onFetchDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(DictionaryResponse dictionaryResponse) {
                        boolean responseval = mDbHelper.insertDistionarWord(dictionaryResponse.getDictionary());
                        if(responseval){
                            interactor.onFetchSuccess( mDbHelper.getDictionarDbData() );
                        }
                    }
                });

        /*Call<DictionaryResponse> getDataFromserver = WebServiceCall.service().getDictionaryRawdata();

        getDataFromserver.enqueue(new Callback<DictionaryResponse>() {
            @Override
            public void onResponse(Call<DictionaryResponse> call, Response<DictionaryResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    boolean responseval = mDbHelper.insertDistionarWord(response.body().getDictionary());

                }
            }

            @Override
            public void onFailure(Call<DictionaryResponse> call, Throwable t) {
                interactor.onError(t.getMessage());
            }
        });
*/
    }

}
