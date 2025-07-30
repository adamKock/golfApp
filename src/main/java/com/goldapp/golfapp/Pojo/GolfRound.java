package com.goldapp.golfapp.Pojo;

import java.time.LocalDate;
import java.util.List;

public class GolfRound {

    private String courseName; 
    private LocalDate date; 
    private List<Hole> holes;
    private String weatherConditions; 

    // getters and setters

    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Hole> getHoles() {
        return holes;
    }
    public void setHoles(List<Hole> holes) {
        this.holes = holes;
    }

    public String getWeatherConditions(){
        return weatherConditions; 
    }

    public void setWeatherConditions(String weatherConditions){
        this.weatherConditions = weatherConditions; 
    }
    
}
