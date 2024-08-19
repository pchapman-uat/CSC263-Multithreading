import java.io.IOException;
import java.util.Scanner;

public class Main {
    static boolean timerFinished = false;
    public static void main(String[] args){
        System.out.println("Hello World!");
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many miliseconds do you want to count?");
        int timer = scanner.nextInt();
        System.out.println("How late can you be?");
        int end = scanner.nextInt();


        Timer t = new Timer(timer, end);



        System.out.println("Your goal is to count to "+timer+" miliseconds.");
        System.out.println("You can be up to "+end+" miliseconds late.");
        System.out.println("Push Enter to stop the timer.");

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
        System.out.println("BEGIN!");
        Thread thread1 = new Thread(t);
        thread1.start();
        
        while(!timerFinished){
            try {
                if (System.in.available() > 0) {
                    scanner.nextLine();
                    thread1.interrupt();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Your goal was: "+ t.gaol + " miliseconds");
        System.out.println("Timer is: "+ t.counter + " miliseconds");
        float score = t.getScore();
        System.out.println(calcScoreString(score));
        System.out.println("You got: "+score+"%");

        scanner.close();
    }

    private static String calcScoreString(double num){
        num /= 100;
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";

        final double greenThreshold = 0.9;
        final double yellowThreshold = 0.7;
        if(num >= greenThreshold){
            return ANSI_GREEN + "Perfect!" + ANSI_RESET;
        }else if(num >= yellowThreshold){
            return ANSI_YELLOW + "Great!" + ANSI_RESET;
        }else{
            return ANSI_RED + "Too Bad" + ANSI_RESET;
        }
    }
}

class Timer implements Runnable {
    public final int gaol;
    public final int end;
    public int counter = 0;

    public Timer(int gaol, int end){
        this.gaol = gaol;
        this.end = end;
    }
    @Override
    public void run() {
        for(int i=0; i<gaol+end; i++){
            try {
                Thread.sleep(1);
                counter++;
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Timer finished.");
        Main.timerFinished = true;
    }

    public float getScore(){
        float score = (float)counter/(float)gaol;
        if(counter == gaol+end){
            score = 0;
        }
        if(score > 1){
            score = 1 - (score-1);
        }
        score*=100;
        score = (float) (Math.round(score * 100.0) / 100.0);
        return score;
    }
}

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