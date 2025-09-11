package com.goldapp.golfapp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.goldapp.golfapp.Pojo.UserInfo;

@Repository
public interface AuthRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUserNameAndPassWord(String userName, String password);

    UserInfo findByUserNameIgnoreCase(String userName);

    @Query("SELECT COALESCE(MAX(u.id), 0) FROM UserInfo u")
    Long findMaxId();

    UserInfo findByEmailIgnoreCase(String email);

}