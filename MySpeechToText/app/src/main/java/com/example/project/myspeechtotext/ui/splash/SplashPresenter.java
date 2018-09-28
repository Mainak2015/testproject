package com.example.project.myspeechtotext.ui.splash;

import android.app.Activity;

import com.example.project.myspeechtotext.ui.dashboard.DashboardActivity;
import com.example.project.myspeechtotext.utility.AppData;

public class SplashPresenter implements SplashContainer.Presenter, SplashContainer.Interactor{
    private SplashContainer.View view;
    private Activity activity;
    private SplashInteractor interactor;

    public SplashPresenter(SplashContainer.View view) {
        this.view = view;
        ;
        interactor = new SplashInteractor(this, (Activity) view);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onSplashTimeOut() {
        if(view != null){
            interactor.pageNavigationDecision();
        }

    }

    @Override
    public void makeNavigationDecision(Activity activity) {
        AppData.startNewActivity(activity, DashboardActivity.class, true);
    }

    @Override
    public void exceptionError(String msg) {
        if(view != null){
            view.exceptionError(msg);
        }
    }
}
