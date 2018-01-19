package com.example.zumirka.androidquiz.Model;

import java.util.ArrayList;

/**
 * Created by Zumirka on 28.12.2017.
 */

public class Question {
    private String content;
    private ArrayList<Answear> answears;
    private int difficult;

    public Question() {
        answears = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Answear> getAnswears() {
        return answears;
    }

    public void addAnswer(Answear answear) {
        this.answears.add(answear);
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }
}
