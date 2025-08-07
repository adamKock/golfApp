package com.goldapp.golfapp.Pojo;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "golf_rounds")
public class GolfRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    private String courseName;

    private LocalDate date; 
    private String weatherConditions; 

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "golf_round_id")
    private List<Hole> holes;

    // getters and setters

    public Long getId(){
        return id; 
    }

    public void setId(Long id){
        this.id=id; 
    }

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
