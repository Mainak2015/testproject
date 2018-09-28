package com.example.project.myspeechtotext.ui.dashboard.entity.model;

public class RawDictionary {
    private int id;
    private String dicWord;
    private int dicCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDicWord() {
        return dicWord;
    }

    public void setDicWord(String dicWord) {
        this.dicWord = dicWord;
    }

    public int getDicCount() {
        return dicCount;
    }

    public void setDicCount(int dicCount) {
        this.dicCount = dicCount;
    }
}
