package com.example.project.myspeechtotext.ui.dashboard.entity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dictionary {
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("frequency")
    @Expose
    private int frequency;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
