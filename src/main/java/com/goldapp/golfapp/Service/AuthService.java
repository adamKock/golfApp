package com.goldapp.golfapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goldapp.golfapp.Pojo.UserInfo;
import com.goldapp.golfapp.Repo.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    public UserInfo authenticate(String userName, String passWord) {
        return authRepository.findByUserNameAndPassWord(userName, passWord);
    }

    public UserInfo findByUserName(String userName) {
        return authRepository.findByUserName(userName);
    }

    public UserInfo registerUser(UserInfo userInfo) {
        // Check if username already exists
        UserInfo existingUser = findByUserName(userInfo.getUserName());
        if (existingUser != null) {
            throw new RuntimeException("Username already exists");
        }

  // Manual ID generation
        Long nextId = getNextId();
        userInfo.setId(nextId);
        
        return authRepository.save(userInfo);
    }
 private Long getNextId() {
        // Get the current maximum ID from the database
        Long maxId = authRepository.findMaxId();
        
        // If no records exist yet, start from 1
        if (maxId == null || maxId == 0) {
            return 1L;
        }
        
        // Return the next ID
        return maxId + 1;
    }


    public boolean userExists(String userName) {
        return authRepository.findByUserName(userName) != null;
    }

     public boolean emailExists(String email) {
        return authRepository.findByEmail(email) != null;
    }
}