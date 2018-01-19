package com.example.zumirka.androidquiz.Model;


public class Statistic {

    private String difficulty, time, points, date;
    private Category category;

    public Statistic() {
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDifficulty() {

        switch (difficulty) {
            case "1":
                return "Łatwy";
            case "2":
                return "Średni";
            case "3":
                return "Trudny";
            default:
                return "";
        }

    }

    public void setDifficulty(String diff) {
        this.difficulty = diff;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
