import java.util.Scanner;

public class RoundLogger {
    
    
    private int score;
    private int fairwaysMissedLeft;  
    private int fairwaysMissedRight;  
    private int fairwaysHit; 
    private int totalPutts; 
     

    public void  askQuestions(){
        Scanner scan = new Scanner(System.in); 

        System.out.println("What did you score today");
        int score = scan.nextInt(); 

        System.out.println("How Many Fairways did you miss left");
        int fairwaysMissedLeft = scan.nextInt(); 
        System.out.println("How Many Fairways did you miss right");
        int fairwaysMissedRight = scan.nextInt();
        System.out.println("How Many Fairways did you hit");
        int fairwaysHit = scan.nextInt();
        System.out.println("What were the total putts for the round");
        int totalPutts = scan.nextInt();

        RoundLogger logRound = new RoundLogger(score, fairwaysMissedLeft, fairwaysMissedRight, fairwaysHit, totalPutts);

    }
    

    public RoundLogger(int score, int fairwaysMissedLeft, int fairwaysMissedRight, int fairwaysHit, int totalPutts ){
        this.score=score; 
        this.fairwaysMissedLeft = fairwaysMissedRight; 
        this.fairwaysMissedRight= fairwaysMissedRight;
        this.fairwaysHit = fairwaysHit; 
        this.totalPutts = totalPutts; 

        // Thinking about each time we log a round it creates a hashmap

    }



}
