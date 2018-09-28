package com.example.project.myspeechtotext.ui.splash;

import android.app.Activity;

public class SplashContainer {
    public interface View{
        void onDestroy();
        void exceptionError(String msg);
    }

    public interface Presenter{
        void onDestroy();
        void onSplashTimeOut();
    }

    public interface Interactor{
        void makeNavigationDecision(Activity activity);
        void exceptionError(String msg);
    }
}
