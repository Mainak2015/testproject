package com.example.project.myspeechtotext.ui.dashboard;

import android.app.Activity;

import com.example.project.myspeechtotext.R;
import com.example.project.myspeechtotext.ui.dashboard.entity.model.DistionaryData;
import com.example.project.myspeechtotext.ui.dashboard.entity.model.RawDictionary;
import com.example.project.myspeechtotext.utility.ConnectionCheck;

import java.util.List;
import java.util.Map;

public class DashBoardPresenter implements DashboardContainer.Presenter, DashboardContainer.Interactor {
    private DashboardContainer.View  view;
    private DashboardActivity activity;
    private DashboardInteractor interactor;


    public DashBoardPresenter(DashboardContainer.View view, DashboardActivity  mActivity) {
        this.view = view;
        activity= mActivity;
        interactor = new DashboardInteractor(this, activity);
    }


    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onDownloadItems() {
        checkNetworkConnection();
    }

    private void checkNetworkConnection() {
        if (ConnectionCheck.isOnlineSocket(activity)) {
            view.showProgress();
            interactor.getdbData();
        } else {
            onFetchDataError(activity.getResources().getString(R.string.err_msg_internet));
        }
    }

    @Override
    public void onUpdateList(Map<Integer, Integer>data) {
        onUpddateCounter(data);
    }

    @Override
    public void onCompleted() {
        if(view!= null){
            view.hideProgress();
        }
    }

    @Override
    public void onFetchSuccess(List<RawDictionary> listdata) {
       if(view!= null){
           onCompleted();
           view.showViewList(listdata);
       }

    }

    @Override
    public void onUpddateCounter(Map<Integer, Integer> data) {
        if(view != null) {
            int countVal = 0;
            List<DistionaryData> distionaryDatalist = view.getDictionaryList();
            for (Map.Entry<Integer, Integer> entry : data.entrySet()) {

                int keyId = entry.getKey();
                int keyVal = entry.getValue();

                System.out.println("Key :" + keyId + " Val= " + keyVal);
                for (int i = 0; i < distionaryDatalist.size(); i++) {
                    if (distionaryDatalist.get(i).getId()==keyId) {
                        distionaryDatalist.get(i).setSelected(true);
                        distionaryDatalist.get(i).setDicCount(keyVal);
                        if(countVal > 0){
                            break;
                        }
                    } else {
                        if(countVal==0) {
                            distionaryDatalist.get(i).setSelected(false);
                        }
                    }
                }

                countVal++;
            }
        }
    }

    @Override
    public void onFetchDataError(String msg) {
        if(view != null){
            view.showMessage(msg);
            view.hideProgress();
        }
    }
}
