package com.example.project.myspeechtotext.ui.splash;

import android.Manifest;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.project.myspeechtotext.BR;
import com.example.project.myspeechtotext.R;
import com.example.project.myspeechtotext.permission.PermissionUtils;
import com.example.project.myspeechtotext.ui.common.BaseActivity;
import com.example.project.myspeechtotext.ui.splash.entity.model.SplashModel;
import com.example.project.myspeechtotext.utility.AppData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends BaseActivity implements SplashContainer.View {

    private SplashModel splashModel = new SplashModel();
    private SplashContainer.Presenter presenter;
    /****** Permission *******/
    PermissionUtils permissionUtils;
    ArrayList<String> permissions=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentOfViewWithBinding(R.layout.activity_splash, this, BR.SplashModel, splashModel);
        showToolBar(false);
        presenter = new SplashPresenter(this);
        if (Build.VERSION.SDK_INT >= 23){
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            //AppUtility.toast(SplashScreen.this,"SplashScreen oncreate"," checking permission");
            permissionUtils = PermissionUtils.builtPermission(this,permissions, getString(R.string.app_name));

        }

    }

    @Override
    public void PermissionGranted(int request_code) {
        super.PermissionGranted(request_code);

        if(presenter!=null)
            presenter.onSplashTimeOut();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (permissionUtils!=null&&!permissionUtils.handleNeverAskSetting) {
            permissionUtils.handleNeverAskSetting = true;
            permissionUtils.check_permission(permissions," Chrmatacity app needs permissions",1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void exceptionError(String msg) {
        AppData.showException(this, msg);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode,permissions,grantResults);

    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        super.PartialPermissionGranted(request_code, granted_permissions);
        Log.i("PERMISSION PARTIALLY","GRANTED");
        permissionUtils.handlePartialPermission(granted_permissions, "Chrmatacity");

    }

    @Override
    public void PermissionDenied(int request_code) {
        super.PermissionDenied(request_code);
        Log.i("PERMISSION","DENIED");
        if (permissionUtils!=null&&!permissionUtils.handleNeverAskSetting) {
            permissionUtils.handleNeverAskSetting = true;
            permissionUtils.check_permission(permissions," Chrmatacity app needs permissions",1);
        }
    }

    @Override
    public void NeverAskAgain(int request_code) {
        super.NeverAskAgain(request_code);
        Log.i("PERMISSION","NEVER ASK AGAIN");
        //checkSystemWritePermission(SplashScreen.this);
        permissionUtils.startInstalledAppDetailsActivity(this);
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.i("#on ","Trim Memory Level-"+level);
    }


}
