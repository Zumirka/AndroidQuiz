package com.example.zumirka.androidquiz.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zumirka on 28.12.2017.
 */

public class Test {
    private String category;
    private ArrayList<Question> questions;
    public Test() {

        questions=new ArrayList<>();

    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public Question getLastQuestion()
    {
        return questions.get(questions.size()-1);
    }

}
