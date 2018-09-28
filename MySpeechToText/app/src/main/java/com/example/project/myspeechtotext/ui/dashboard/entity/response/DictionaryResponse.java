package com.example.project.myspeechtotext.ui.dashboard.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DictionaryResponse {
    @SerializedName("dictionary")
    @Expose
    private List<Dictionary> dictionary = null;

    public List<Dictionary> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<Dictionary> dictionary) {
        this.dictionary = dictionary;
    }
}
