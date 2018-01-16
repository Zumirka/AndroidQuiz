package com.example.zumirka.androidquiz.Model;




public class Statistic {

    private String CategoryId,Difficulty,Time,Points,Date;
    public  Statistic() {}
    public String getCategoryId(){return  CategoryId;}
    public String getDifficulty(){
        String diff="";
        if(Difficulty.equals("1"))
            diff= "Łatwy";

        if(Difficulty.equals("2"))
            diff= "Średni";

        if(Difficulty.equals("3"))
            diff= "Trudny";

        return diff;
    }

    public String getTime(){return Time;}
    public String getPoints() {return Points;}
    public String getDate() {return Date;}
    public void setCategoryId( String id){this.CategoryId=id;}
    public void setDifficulty(String diff){this.Difficulty=diff;}
    public void setTime(String time){this.Time=time;}
    public void setPoints(String points){this.Points=points;}
    public void setDate(String date){this.Date=date;}


}
