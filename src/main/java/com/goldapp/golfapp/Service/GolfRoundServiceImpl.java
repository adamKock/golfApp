package com.goldapp.golfapp.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldapp.golfapp.Pojo.GolfRound;
import com.goldapp.golfapp.Repo.GolfRoundRepo;

@Service
public class GolfRoundServiceImpl implements GolfRoundService {
    @Autowired
    private GolfRoundRepo repo;

    @Override
    public void addRound(GolfRound golfRound) {
        repo.save(golfRound);
    }

    @Override
    public List<GolfRound> getGolfRounds() {
        return repo.findAll();
    }

    @Override
    public GolfRound findRoundByDateAndCourseNameAndUserId(LocalDate date, String courseName, Long userId) {
        return repo.findRoundByDateAndCourseNameAndUserId(date, courseName, userId);
    }

    @Override
    public List<GolfRound> findAllRoundsByUserId(Long userId) {
        return repo.findAllRoundsByUserId(userId);

    }

}
