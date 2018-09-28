package com.example.project.myspeechtotext.ui.common;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.project.myspeechtotext.R;
import com.example.project.myspeechtotext.permission.PermissionResultCallback;
import com.example.project.myspeechtotext.ui.common.entity.base.BaseModel;
import com.example.project.myspeechtotext.BR;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity implements PermissionResultCallback, BaseModel.BackModelCallBack {
    /****** Ui component **********/
    public Toolbar toolbar;
    private FrameLayout containerFrameLayout;
    /****** component **********/
    public BaseModel baseModel;
    public ViewDataBinding  baseBinding, binding;
    public boolean isBackShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseModel = new BaseModel();
        baseBinding = DataBindingUtil.setContentView(this,R.layout.activity_base );
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        baseBinding.setVariable(BR.Context, this);
        baseBinding.setVariable(BR.BaseModel, baseModel);
        baseBinding.setVariable(BR.BackModelCallBack, this);
        initView();

    }

    private void initView(){
        containerFrameLayout = (FrameLayout) findViewById(R.id.containerFrameLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        }

    protected void setContentOfView(int _layoutId,Activity activity) {
        View view = LayoutInflater.from(activity).inflate(_layoutId, containerFrameLayout, false);
        containerFrameLayout.addView(view, new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    protected void setContentOfViewWithBinding(int _layoutId, Activity activity, int BRInstance, Object bindObject) {
        addCustomView(LayoutInflater.from(activity),containerFrameLayout,_layoutId,BRInstance,bindObject);
    }

    public void addCustomView(LayoutInflater inflater, ViewGroup container, int layoutId, int instance, Object user) {
        binding = DataBindingUtil.inflate(inflater,layoutId, container, true);
        binding.setVariable(instance, user);
    }

    public void makeTranslucentStatusBar(){
        setTheme(R.style.OverlayAppTheme);
    }


    public ViewDataBinding getBinding() {
        return binding;
    }

    public void showToolBar(boolean callback) {
        baseModel.setToolBarShowing(callback);
    }

    public void showBackButton() {
        isBackShowing = true;
        baseModel.setBackButtonShow(true);
    }


    public void setTittle(String text) {
        baseModel.setTittle(text);
    }


    public void isLoading(boolean state){
        baseModel.setLoading(state);
    }


    /****** Permission ******/

    @Override
    public void PermissionGranted(int request_code) {

    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }

    /********* End Permission ********/
    @Override
    public void clickBackArrow(View view) {
        finish();
    }

}
