package com.example.zumirka.androidquiz.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zumirka on 28.12.2017.
 */

public class Question {
    private String content;
    private List<Answer> answers;
    private int difficult;

    public Question() {
        answers=new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }
}
