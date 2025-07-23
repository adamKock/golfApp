package com.goldapp.golfapp.Web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.goldapp.golfapp.Pojo.GolfRound;
import com.goldapp.golfapp.Service.GolfRoundService;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class GolfController {

    @Autowired private GolfRoundService golfRoundService; 


   


    @PostMapping("/round/create")
    public ResponseEntity<HttpStatus> createPost(@RequestBody GolfRound round){
        if(round.getScore()<0){
            throw new IllegalArgumentException("Score cannot be negative");
        }
        golfRoundService.addRound(round);
        return new ResponseEntity<>(HttpStatus.OK); 

    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Golf App!";
    }

    @GetMapping("/round/get")
    public List<GolfRound> getMethodName() {
        if(golfRoundService.getGolfRounds().size()<1){
            throw new IllegalArgumentException("No rounds saved");
        }
        return golfRoundService.getGolfRounds();
    }
    
	
}
