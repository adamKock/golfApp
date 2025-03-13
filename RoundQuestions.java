import java.util.Scanner;

public class RoundQuestions {
    private String date; 
    private int score;
    private int fairwaysMissedLeft;  
    private int fairwaysMissedRight;  
    private int fairwaysHit; 
    private int totalPutts; 

    //Here we want to ask the questions from scanner class and then take the VARS from them passing them into the round logger class

    public void askQuestions(){
        Scanner scan = new Scanner(System.in); 

        System.out.println("What did you score today");
        int usersScore = scan.nextInt(); 

        System.out.println("How Many Fairways did you miss left");
        int missedFairwayLeft = scan.nextInt(); 
        System.out.println("How Many Fairways did you miss right");
        int missedFairwayRight = scan.nextInt();
        System.out.println("How Many Fairways did you hit");
        int missedFairwayhit = scan.nextInt();
        System.out.println("What were the total putts for the round");
        int totalPutts = scan.nextInt();

    }


    
}
