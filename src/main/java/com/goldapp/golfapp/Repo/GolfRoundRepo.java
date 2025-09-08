package com.goldapp.golfapp.Repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goldapp.golfapp.Pojo.GolfRound;

@Repository
public interface GolfRoundRepo extends JpaRepository<GolfRound, Long> {
  GolfRound findRoundByDateAndCourseNameAndUserId(LocalDate date, String courseName, Long userId);

  List<GolfRound> findAllRoundsByUserId(Long userId);

}
