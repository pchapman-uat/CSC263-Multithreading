public class Main {
    public static void main(String[] args){
        System.out.println("Hello World!");
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