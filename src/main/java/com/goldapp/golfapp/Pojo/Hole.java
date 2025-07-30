package com.goldapp.golfapp.Pojo;

public class Hole {
    private int holeNumber;
    private String par;
    private int strokes;
    private String fairway;
    private int putts;
    private int penalties; 
    private String teeClubUsed;
    private Boolean isGIR;
    private String upAndDown;

    // getters and setters

    public String getTeeClubUsed(){
        return teeClubUsed;
    }

    public void setTeeClubUsed(String teeClubUsed){
        this.teeClubUsed = teeClubUsed;

    }

    public Boolean getGIR(){
        return isGIR;
    }
    public void setIsGIR(Boolean isGIR) {
        this.isGIR = isGIR;
    }

    public String getUpAndDown(){
        return upAndDown; 
    }
    public void setUpAndDown(String upAndDown){
        this.upAndDown = upAndDown; 
    }

    public int getPenalties(){
        return penalties;
    }

    public void setPenalties(int penalties){
        this.penalties = penalties;
    }

    public int getHoleNumber() {
        return holeNumber;
    }
    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }

    public String getPar() {
        return par;
    }
    public void setPar(String par) {
        this.par = par;
    }

    public int getStrokes() {
        return strokes;
    }
    public void setStrokes(int strokes) {
        this.strokes = strokes;
    }

    public String getFairway() {
        return fairway;
    }
    public void setFairway(String fairway) {
        this.fairway = fairway;
    }

    public int getPutts() {
        return putts;
    }
    public void setPutts(int putts) {
        this.putts = putts;
    }
}
