import java.io.IOException;
import java.util.Scanner;

public class Main {
    static boolean timerFinished = false;
    public static void main(String[] args){
        System.out.println("Hello World!");
        Timer t = new Timer(1000);
        Thread thread1 = new Thread(t);
        thread1.start();
        Scanner scanner = new Scanner(System.in);
        while(!timerFinished){
            try {
                if (System.in.available() > 0) {
                    scanner.nextLine();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Timer is: "+ t.counter);
        scanner.close();
    }
}

class Timer implements Runnable {
    public final int lengthMS;
    public int counter = 0;
    public Timer(int lengthMS){
        this.lengthMS = lengthMS;
    }
    @Override
    public void run() {
        for(int i=0; i<lengthMS; i++){
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