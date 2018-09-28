package com.example.project.myspeechtotext.ui.splash;

import android.app.Activity;
import android.os.Handler;

import com.example.project.myspeechtotext.local_db.DictionaryDBHelper;


public class SplashInteractor  {
    SplashContainer.Interactor interactor;
    Activity mActivity;

    public SplashInteractor(SplashContainer.Interactor interactor, Activity activity) {
        this.interactor = interactor;
        mActivity=activity;
    }

    public void pageNavigationDecision() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DictionaryDBHelper.getInstance(mActivity.getApplicationContext());
                    interactor.makeNavigationDecision(mActivity);
                }
            }, 3000);
        } catch (Exception e) {
            interactor.exceptionError(e.getMessage());
        }
    }
}
