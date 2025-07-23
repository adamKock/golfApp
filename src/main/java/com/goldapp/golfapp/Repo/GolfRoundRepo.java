package com.goldapp.golfapp.Repo;

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

    


    
}
