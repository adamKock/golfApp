package com.goldapp.golfapp.Repo;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldapp.golfapp.Pojo.GolfRound;

@Repository
public interface GolfRoundRepo extends JpaRepository<GolfRound, Long>{
     GolfRound findRoundByDateAndCourseName(LocalDate date, String courseName);
    
}
