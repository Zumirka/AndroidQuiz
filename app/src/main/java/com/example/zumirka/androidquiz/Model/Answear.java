package com.example.zumirka.androidquiz.Model;

/**
 * Created by Zumirka on 28.12.2017.
 */

public class Answear {
    private String content;
    private boolean isCorrect;

    //pobieranie zawartości odpowiedzi
    public String getContent() {
        return content;
    }

    // ustawianie zawartości odpowiedzi
    public void setContent(String content) {
        this.content = content;
    }

    // sprawdzanie czy jest poprawną odpowiedzią
    public boolean isCorrect() {
        return isCorrect;
    }

    //ustawienie że jest poprawną odpowiedzią
    public void setCorrect(boolean correct) {
        this.isCorrect = correct;
    }
}
