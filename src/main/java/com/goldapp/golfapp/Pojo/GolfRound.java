package com.goldapp.golfapp.Pojo;

public class GolfRound {

    private int score;

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

}