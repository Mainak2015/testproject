package com.example.project.myspeechtotext.ui.speak.entity.model;

import android.databinding.Bindable;
import android.view.View;

import com.example.project.myspeechtotext.BR;

import com.example.project.myspeechtotext.ui.common.entity.common.BaseDatabindAdapter;

public class SpeakModel  extends BaseDatabindAdapter{
    private String topHeading;

    @Bindable
    public String getTopHeading() {
        return topHeading;
    }

    public void setTopHeading(String topHeading) {
        this.topHeading = topHeading;
        notifyPropertyChanged(BR.topHeading);
    }

    public interface SpeakCallBack{
        void tapTospeak(View view);
    }
}
