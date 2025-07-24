package com.goldapp.golfapp.Pojo;

public class GolfRound {

    private int score;
    private String course; 

    // No-arg constructor required by Jackson
    public GolfRound() {
    }

    public GolfRound(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }


    public String getCourse(){
        return course;
    }

    public void setCourse(String course){
        this.course = course;
    }

}