package com.example.project.myspeechtotext.ui.speak;

import android.app.Activity;

import com.example.project.myspeechtotext.ui.dashboard.entity.model.RawDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpeakContainer {

   public interface MainView{
       void showMessage(String message);
       void onRedirectHome(Map<Integer, Integer> result);

    }

    public interface Presenter{
        void onDestroy();
        void onSpeakResult(String result);


    }


    public interface Interactor{
        void onSuccess(Map<Integer, Integer> result);
        void onError(String msg);
    }
}
