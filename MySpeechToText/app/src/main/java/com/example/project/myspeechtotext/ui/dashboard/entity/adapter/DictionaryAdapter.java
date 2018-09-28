package com.example.project.myspeechtotext.ui.dashboard.entity.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.example.project.myspeechtotext.R;
import com.example.project.myspeechtotext.BR;

import com.example.project.myspeechtotext.ui.dashboard.entity.model.DistionaryData;

import java.util.List;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {

    private List<DistionaryData> distionaryDatalist;
    private Activity activity;


    public DictionaryAdapter(Activity activity, List<DistionaryData> mDistionaryDatalist) {
        distionaryDatalist = mDistionaryDatalist;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.child_item_row, viewGroup, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ViewDataBinding viewDataBinding = viewHolder.getViewDataBinding();
        viewDataBinding.setVariable(BR.DistionaryDataModel, distionaryDatalist.get(position));
    }

    @Override
    public int getItemCount() {
        return (null != distionaryDatalist ? distionaryDatalist.size() : 0);
    }

    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding mViewDataBinding;

        public ViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            mViewDataBinding = viewDataBinding;
            mViewDataBinding.executePendingBindings();
        }

        public ViewDataBinding getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    public void setupdateNewdata(List<DistionaryData> mDistionaryDatalist) {
        distionaryDatalist = mDistionaryDatalist;
        notifyDataSetChanged();
    }
}