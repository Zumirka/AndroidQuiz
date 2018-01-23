package com.example.zumirka.androidquiz.Model;

import java.util.ArrayList;

/**
 * Created by Zumirka on 28.12.2017.
 */

public class Question {
    private String content;
    private ArrayList<Answear> answears;
    private int difficult;
    // Konstruktor który alokuje pamięć na listę odpowiedzi
    public Question() {
        answears = new ArrayList<>();
    }
    // pobieranie zawartości pytania
    public String getContent() {
        return content;
    }
    //ustawianie zawartości pytania
    public void setContent(String content) {
        this.content = content;
    }
    // pobieranie listy z odpowiedziami (obiektami typu odpowiedż)
    public ArrayList<Answear> getAnswears() {
        return answears;
    }
    // dodaje nowy obiekt typu odpowiedź do listy odpowiedzi
    public void addAnswer(Answear answear) {
        this.answears.add(answear);
    }
    // pobiera poziom trudności
    public int getDifficult() {
        return difficult;
    }
    //ustawia poziom trudności
    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }
}
