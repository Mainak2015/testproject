package com.example.project.myspeechtotext.ui.speak;

import android.app.Activity;
import android.util.Log;

import com.example.project.myspeechtotext.ui.dashboard.DashboardInteractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpeakPresenter implements SpeakContainer.Presenter, SpeakContainer.Interactor {

    private SpeakContainer.MainView mainView;
    private Activity activity;
    private SpeakInteractor speakinteractor;

    public SpeakPresenter(SpeakContainer.MainView mainView) {
        this.mainView = mainView;
        activity = (Activity) mainView;
        speakinteractor = new SpeakInteractor(this, (Activity) mainView);
    }


    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onSpeakResult(String result) {
        Log.e("Resut >>>", result);
        String strOutput = result.replace("second","2nd").replace("fourth","4th").replace("xx", "Twenty");
        speakinteractor.filterData(strOutput);
    }


    @Override
    public void onSuccess(Map<Integer, Integer>data) {
      if(mainView != null){
          mainView.onRedirectHome(data);
      }
    }

    @Override
    public void onError(String msg) {
        if (mainView != null) {
            mainView.showMessage(msg);
        }
    }
}
