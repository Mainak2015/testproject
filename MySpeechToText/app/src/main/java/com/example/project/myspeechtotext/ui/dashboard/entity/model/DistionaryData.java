package com.example.project.myspeechtotext.ui.dashboard.entity.model;

import android.databinding.Bindable;
import com.example.project.myspeechtotext.BR;

import com.example.project.myspeechtotext.ui.common.entity.common.BaseDatabindAdapter;

public class DistionaryData extends BaseDatabindAdapter {

    private  int id;
    private String dicWord;
    private int dicCount;
    private boolean isSelected = false;

    @Bindable
    public int getId() {
        return id;
    }

    @Bindable
    public String getDicWord() {
        return dicWord;
    }

    @Bindable
    public String getDicCount() {
        return String.valueOf(dicCount);
    }

    @Bindable
    public boolean isSelected() {
        return isSelected;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setDicWord(String dicWord) {
        this.dicWord = dicWord;
        notifyPropertyChanged(BR.dicWord);
    }

    public void setDicCount(int dicCount) {
        this.dicCount = dicCount;
        notifyPropertyChanged(BR.dicCount);
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        notifyPropertyChanged(BR.selected);
    }
}
