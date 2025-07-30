package com.goldapp.golfapp.Repo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.goldapp.golfapp.Pojo.GolfRound;

@Repository
public class GolfRoundRepo {

    private List<GolfRound> golfRoundDb= new ArrayList<>(); 

    //implement crud 

    public void addRound(GolfRound round){
        golfRoundDb.add(round); 
    }

    public List<GolfRound> getGolfRounds(){
        return golfRoundDb; 
    }

    public GolfRound findRoundByDateAndCourse(LocalDate date, String course) {
    for (GolfRound round : golfRoundDb) {
         System.out.println("Checking: " + round.getCourseName() + " on " + round.getDate());

        if (round.getDate().equals(date) && round.getCourseName().equalsIgnoreCase(course)) {
            return round;
        }
    }
    return null; // No match found
}

   

    


    
}
