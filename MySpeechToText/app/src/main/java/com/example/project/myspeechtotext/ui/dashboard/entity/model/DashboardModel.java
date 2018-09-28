package com.example.project.myspeechtotext.ui.dashboard.entity.model;

import android.databinding.Bindable;
import android.view.View;

import com.example.project.myspeechtotext.BR;

import com.example.project.myspeechtotext.ui.common.entity.common.BaseDatabindAdapter;
import com.example.project.myspeechtotext.ui.dashboard.entity.adapter.DictionaryAdapter;

public class DashboardModel extends BaseDatabindAdapter {

    private DictionaryAdapter dictionaryAdapter;

   @Bindable
   public DictionaryAdapter getDictionaryAdapter() {
        return dictionaryAdapter;
    }

    public void setDictionaryAdapter(DictionaryAdapter dictionaryAdapter) {
        this.dictionaryAdapter = dictionaryAdapter;
        notifyPropertyChanged(BR.dictionaryAdapter);
    }

    public interface DashboardCallBack{
       void toSpeak(View view);
    }
}
