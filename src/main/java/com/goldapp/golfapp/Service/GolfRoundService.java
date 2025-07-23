package com.goldapp.golfapp.Service;

import java.util.List;

import com.goldapp.golfapp.Pojo.GolfRound;

public interface GolfRoundService {

    void addRound(GolfRound golfRound);
    public List<GolfRound> getGolfRounds();
    
} 
