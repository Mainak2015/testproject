package com.example.project.myspeechtotext.ui.speak;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;

import com.example.project.myspeechtotext.BR;
import com.example.project.myspeechtotext.R;
import com.example.project.myspeechtotext.ui.common.BaseActivity;
import com.example.project.myspeechtotext.ui.speak.entity.model.SpeakModel;
import com.example.project.myspeechtotext.utility.AppData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class SpeakActivity extends BaseActivity implements SpeakModel.SpeakCallBack, SpeakContainer.MainView {

    private SpeakModel speakModel = new SpeakModel();
    private SpeakPresenter presenter;
    private String TAG = "SpeakActivity";
    private final int REQ_CODE_SPEECH_INPUT = 312;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showToolBar(true);
        setContentOfViewWithBinding(R.layout.activity_speak, this, BR.SpeakModel, speakModel);
        getBinding().setVariable(BR.SpeakCallBack, this);

        presenter = new SpeakPresenter(this);
        setTittle("Speech to Text");
        speakModel.setTopHeading("Tap to Speak");

    }


    @Override
    public void tapTospeak(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            showMessage(getString(R.string.err_msg_not_support));
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showMessage(String message) {
        AppData.showToast(this, message);
    }

    @Override
    public void onRedirectHome(Map<Integer, Integer> result) {
        Intent intentResult = new Intent();
        intentResult.putExtra("mapResult", (Serializable) result);
        setResult(Activity.RESULT_OK,intentResult);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 312:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result != null && result.size() > 0) {
                        presenter.onSpeakResult( result.get(0));
                        }
                }
                break;
        }
    }
}
