package com.example.project.myspeechtotext.ui.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.util.AsyncListUtil;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.project.myspeechtotext.BR;
import com.example.project.myspeechtotext.R;
import com.example.project.myspeechtotext.ui.common.BaseActivity;
import com.example.project.myspeechtotext.ui.dashboard.entity.adapter.DictionaryAdapter;
import com.example.project.myspeechtotext.ui.dashboard.entity.model.DashboardModel;
import com.example.project.myspeechtotext.ui.dashboard.entity.model.DistionaryData;
import com.example.project.myspeechtotext.ui.dashboard.entity.model.RawDictionary;
import com.example.project.myspeechtotext.ui.speak.SpeakActivity;
import com.example.project.myspeechtotext.utility.AppData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashboardActivity extends BaseActivity implements DashboardContainer.View, DashboardModel.DashboardCallBack {

    private DashBoardPresenter presenter;
    DashboardModel dashboardModel = new DashboardModel();
    DictionaryAdapter dictionaryAdapter;
    List<DistionaryData> distionaryDatalist = new ArrayList<>();

    private String TAG = "DashboardActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showToolBar(true);
        setContentOfViewWithBinding(R.layout.activity_dashboard, this, BR.DashboardModel, dashboardModel);
        getBinding().setVariable(BR.DashboardCallBack, this);



        setTittle("Speech to Text");


        dictionaryAdapter = new DictionaryAdapter(this, distionaryDatalist);
        dashboardModel.setDictionaryAdapter(dictionaryAdapter);

        presenter = new DashBoardPresenter(this, DashboardActivity.this);
        presenter.onDownloadItems();

        Log.e("Click onCreate==", TAG);

    }

    @Override
    public void showProgress() {
        isLoading(true);
    }

    @Override
    public void hideProgress() {
        isLoading(false);
    }


    @Override
    public void showMessage(String message) {
        AppData.showToast(this, message);
    }


    @Override
    public void toSpeak(View view) {
        Log.e("Click Result==", TAG);

        Log.e("Call ASYNCTask==", TAG);

         AppData.startNewActivityForResult(this, SpeakActivity.class, 101);

    }

    @Override
    public void showViewList(List<RawDictionary> listdata) {
        if (listdata != null && listdata.size() > 0) {
            for (int i = 0; i < listdata.size(); i++) {
                DistionaryData distionaryData = new DistionaryData();
                distionaryData.setId(listdata.get(i).getId());
                distionaryData.setDicWord(listdata.get(i).getDicWord());
                distionaryData.setDicCount(listdata.get(i).getDicCount());
                distionaryData.setSelected(false);
                distionaryDatalist.add(distionaryData);
            }

            dashboardModel.getDictionaryAdapter().setupdateNewdata(distionaryDatalist);
        }
    }

    @Override
    public List<DistionaryData> getDictionaryList() {
        return distionaryDatalist;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK && data != null) {
                    Map<Integer, Integer> speakMap = (Map<Integer, Integer>) data.getSerializableExtra("mapResult");
                    if (speakMap != null && speakMap.size() > 0) {
                        presenter.onUpdateList(speakMap);
                    }
                }
                break;
        }
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
