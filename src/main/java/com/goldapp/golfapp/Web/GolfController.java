package com.goldapp.golfapp.Web;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.goldapp.golfapp.Pojo.GolfRound;
import com.goldapp.golfapp.Pojo.UserInfo;
import com.goldapp.golfapp.Service.GolfRoundService;

@CrossOrigin(origins = "http://localhost:5173", 
             allowedHeaders = "*",
             methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
             allowCredentials = "true")
@RestController
@RequestMapping("/round")
public class GolfController {

    @Autowired
    private GolfRoundService golfRoundService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPost(@RequestBody GolfRound round) {
        golfRoundService.addRound(round);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Round saved successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Golf App!";
    }

    @GetMapping("/get")
    public List<GolfRound> getMethodName() {
        if (golfRoundService.getGolfRounds().size() < 1) {
            throw new IllegalArgumentException("No rounds saved");
        }
        return golfRoundService.getGolfRounds();
    }

    @PostMapping("/find")
    public GolfRound findRound(@RequestBody GolfRound round) {
        return golfRoundService.findRoundByDateAndCourseName(round.getDate(), round.getCourseName());
    }


    @GetMapping("/allRounds")
    public List<GolfRound> getAllGolfRounds() {
        if (golfRoundService.getGolfRounds().size() < 1) {
            throw new IllegalArgumentException("No rounds saved");
        }
        return golfRoundService.getGolfRounds();
    }


}
