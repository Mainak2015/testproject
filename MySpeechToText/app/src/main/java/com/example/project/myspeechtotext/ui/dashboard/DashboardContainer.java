package com.example.project.myspeechtotext.ui.dashboard;

import android.app.Activity;

import com.example.project.myspeechtotext.ui.dashboard.entity.model.DistionaryData;
import com.example.project.myspeechtotext.ui.dashboard.entity.model.RawDictionary;
import com.example.project.myspeechtotext.ui.dashboard.entity.response.Dictionary;

import java.util.List;
import java.util.Map;

public class DashboardContainer {
    interface View{
        void showProgress();
        void hideProgress();
        void showMessage(String message);
        void showViewList(List<RawDictionary> listdata);
        List <DistionaryData> getDictionaryList();
    }

    public interface Presenter{
        void onDestroy();
        void onDownloadItems();
        void onUpdateList(Map<Integer, Integer>data);

    }


    public interface Interactor{
       void onFetchSuccess(List<RawDictionary> listdata);
       void onCompleted();
        void onUpddateCounter(Map<Integer, Integer>data);
        void onFetchDataError(String msg);
    }
}
