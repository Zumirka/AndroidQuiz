package com.example.zumirka.androidquiz.Model;

/**
 * Created by Zumirka on 28.12.2017.
 */

public class Answear {
    private String content;
    private boolean isCorrect;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        this.isCorrect = correct;
    }
}
