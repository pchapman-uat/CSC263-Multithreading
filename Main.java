import java.io.IOException;
import java.util.Scanner;

public class Main {
    static boolean timerFinished = false;
    public static void main(String[] args){
        System.out.println("Hello World!");
        Scanner scanner = new Scanner(System.in);
        // Get the users input for the timer
        // BUG: If you enter a number that is not an integer, the program will crash.
        System.out.println("How many miliseconds do you want to count?");
        int timer = scanner.nextInt();
        System.out.println("How late can you be?");
        int end = scanner.nextInt();

        // Create a new timer
        Timer t = new Timer(timer, end);


        // Display the goal
        System.out.println("Your goal is to count to "+timer+" miliseconds.");
        System.out.println("You can be up to "+end+" miliseconds late.");
        System.out.println("Push Enter to stop the timer.");

        // Countdown for the game
        System.out.println("Timer is starting...");
        int countdown = 3;
        for(int i = 0; i<countdown; i++){
            System.out.println(countdown-i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Start the timer
        System.out.println("BEGIN!");
        Thread thread1 = new Thread(t);
        thread1.start();
        
        // Wait for the timer to finish
        while(!timerFinished){
            try {
                // Check if the user has pressed enter
                if (System.in.available() > 0) {
                    scanner.nextLine();
                    // Stop the timer
                    thread1.interrupt();
                    // Exit the loop
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Print the results
        System.out.println("Your goal was: "+ t.gaol + " miliseconds");
        System.out.println("Timer is: "+ t.counter + " miliseconds");
        // Calculate the score
        float score = t.getScore();
        System.out.println(calcScoreString(score));
        System.out.println("You got: "+score+"%");

        // Close the scanner
        scanner.close();
    }

    // Calculate the score
    private static String calcScoreString(double num){
        // Num is a score between 0 and 100, this is changed to a percentage
        num /= 100;
        // Color styles for the terminal
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";

        // Threshold values for the colors
        final double greenThreshold = 0.9;
        final double yellowThreshold = 0.7;

        // Return the result with the color based on the score
        if(num >= greenThreshold){
            return ANSI_GREEN + "Perfect!" + ANSI_RESET;
        }else if(num >= yellowThreshold){
            return ANSI_YELLOW + "Great!" + ANSI_RESET;
        }else{
            return ANSI_RED + "Too Bad" + ANSI_RESET;
        }
    }
}

// Timer class
class Timer implements Runnable {
    // The goal is the amount of miliseconds the user has to count to
    public final int gaol;
    // The end is the amount of miliseconds the user can be late
    public final int end;
    // The counter is the amount of miliseconds the user has counted to
    public int counter = 0;
    public Timer(int gaol, int end){
        this.gaol = gaol;
        this.end = end;
    }
    // The run method is called when the thread is started
    @Override
    public void run() {
        // Start the timer
        for(int i=0; i<gaol+end; i++){
            try {
                // Sleep for 1 millisecond
                // Doing sleep will result in an inacurate timer, but for the purpose of this project it is fine
                Thread.sleep(1);
                counter++;
            } catch (InterruptedException e) {
                break;
            }
        }
        // Set the timerFinished variable to true
        System.out.println("Timer finished.");
        Main.timerFinished = true;
    }

    // Get the score
    // Returns a value from 0 to 100
    public float getScore(){
        // Calculate the score
        float score = (float)counter/(float)gaol;
        // If the timer is finished, the score is 0
        if(counter == gaol+end){
            score = 0;
        }
        // If the timer is late, calculate the distance from the goal
        if(score > 1){
            score = 1 - (score-1);
        }
        // Round the score to 2 decimal places
        score*=100;
        score = (float) (Math.round(score * 100.0) / 100.0);
        return score;
    }
}
/**
 * This is an example of a thread that counts to 5, it is not used in the project
 * @deprecated
 */
class MyThread extends Thread {
    private String threadName;
    public MyThread(String name) {
        threadName = name;
    }
    public void run(){
        for(int i=0; i<5; i++){
            System.out.println(threadName + " is running- "+i);
            try  {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                System.out.println( threadName + " interrupted.");
            }
        }
        System.out.println(threadName + " finished.");
    }
}

/**
 * This is an example of a runnable that counts to 5, it is not used in the project
 * @deprecated
 */
class MyRunnable implements Runnable {
    private String threadName;
    public MyRunnable(String name) {
        threadName = name;
    }

    @Override
    public void run(){
        for(int i=0;i<5;i++){
            System.out.println(threadName + " is running- "+i);
            try  {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                System.out.println( threadName + " interrupted.");
            }
        }
    }
}