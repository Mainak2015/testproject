package com.example.project.myspeechtotext.ui.common.entity.base;

import android.databinding.Bindable;
import android.view.View;

import com.example.project.myspeechtotext.ui.common.entity.common.BaseDatabindAdapter;
import com.example.project.myspeechtotext.BR;

public class BaseModel extends BaseDatabindAdapter {
    private boolean isToolBarShowing = true;
    private String tittle;
    private boolean isBackButtonShow = false;
    private boolean isLoading = false;

    public BaseModel() {
    }

    public BaseModel(boolean isToolBarShowing, String tittle, boolean isBackButtonShow, boolean isLoading) {
        this.isToolBarShowing = isToolBarShowing;
        this.tittle = tittle;
        this.isBackButtonShow = isBackButtonShow;
        this.isLoading = isLoading;
    }

    @Bindable
    public boolean isToolBarShowing() {
        return isToolBarShowing;
    }

    @Bindable
    public String getTittle() {
        return tittle;
    }

    @Bindable
    public boolean isBackButtonShow() {
        return isBackButtonShow;
    }

    @Bindable
    public boolean isLoading() {
        return isLoading;
    }

    public void setToolBarShowing(boolean toolBarShowing) {
        isToolBarShowing = toolBarShowing;
        notifyPropertyChanged(BR.toolBarShowing);
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
        notifyPropertyChanged(BR.tittle);
    }

    public void setBackButtonShow(boolean backButtonShow) {
        isBackButtonShow = backButtonShow;
        notifyPropertyChanged(BR.backButtonShow);
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        notifyPropertyChanged(BR.loading);
    }

    public interface BackModelCallBack{
        void clickBackArrow(View view);
    }
}
