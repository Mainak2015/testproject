package com.example.project.myspeechtotext.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.project.myspeechtotext.R;

public class AppData {

    public static String TAG = "ST ::::";
    public static final String BASE_URL = "http://a.galactio.com/interview/";




    public static void showNoInterNetToast(Context context) {
        Toast.makeText(context, context.getResources().getString(R.string.err_msg_internet), Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static void showException(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void startNewActivity(Activity activity, Class className, boolean clearStack) {
        Intent intent = new Intent(activity, className);
        if (clearStack)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }


    public static void startNewActivityForResult(Activity activity, Class className, int code) {
        Intent intent = new Intent(activity, className);
        activity.startActivityForResult(intent, code);

    }

}
