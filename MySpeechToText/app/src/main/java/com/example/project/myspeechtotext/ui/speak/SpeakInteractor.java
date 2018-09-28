package com.example.project.myspeechtotext.ui.speak;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.example.project.myspeechtotext.R;
import com.example.project.myspeechtotext.local_db.DictionaryDBHelper;
import com.example.project.myspeechtotext.ui.dashboard.entity.model.RawDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SpeakInteractor {
    private Activity activity;
    SpeakContainer.Interactor interactor;
    private List<RawDictionary> rawDictionaryList = null;
    private String TAG ="SpeakInteractor";

    public SpeakInteractor( SpeakContainer.Interactor interactor, Activity mactivity) {
        activity = mactivity;
        this.interactor = interactor;
       rawDictionaryList = DictionaryDBHelper.getInstance(activity.getApplicationContext()).getDictionarDbData();
    }


    public void filterData(String result){
        Log.e(TAG, "Resut >>>"+ result);

        Map<Integer, Integer> selectPositionMap  = DictionaryDBHelper.getInstance(activity.getApplicationContext()).updateAllSpeakingFrequency(result);
        if(selectPositionMap.size()> 0){
            interactor.onSuccess(selectPositionMap);
        }else{
            interactor.onError(activity.getString(R.string.no_match));
        }

      //  rawDictionaryList = DictionaryDBHelper.getInstance(activity.getApplicationContext()).updateFrequency();
        /*if(rawDictionaryList != null && rawDictionaryList.size() >0 && result != null && !TextUtils.isEmpty(result)){
            Map<Integer, Integer> occurrences = new HashMap<Integer, Integer>();
            for(int s= 0; s<rawDictionaryList.size(); s++){
                int i = 0;
                int from = 0;
                Pattern p = Pattern.compile(rawDictionaryList.get(s).getDicWord().toLowerCase());
                Matcher m = p.matcher(result.toLowerCase());
                while (m.find(from)) {
                    i++;
                    from = m.start() + 1;
                }
                //System.out.println(rawDictionaryList.get(s).getDicWord());
                //System.out.println(i);
                if(i>0) {
                    occurrences.put(rawDictionaryList.get(s).getId(), rawDictionaryList.get(s).getDicCount() + i);
                }
            }

            if(occurrences != null && occurrences.size() >0){
                interactor.onSuccess(occurrences);
            }else{
                interactor.onError(activity.getString(R.string.no_match));
            }

        }else{
            interactor.onError(activity.getString(R.string.no_match));
        }*/
    }
}
