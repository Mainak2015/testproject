package com.example.project.myspeechtotext.ui.common.entity.common;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.project.myspeechtotext.utility.smoothscroller.SnappingLinearLayoutManager;

public class BaseDatabindAdapter extends BaseObservable {

    @BindingAdapter({"bind:setDefaultAdapter"})
    public static void setDefaultAdapter(RecyclerView view, Object adapter) {
        view.hasFixedSize();
        view.setLayoutManager(new SnappingLinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL,false));
        view.setAdapter((RecyclerView.Adapter) adapter);
    }
}
