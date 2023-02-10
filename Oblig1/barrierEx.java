package Oblig1;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.Collections;

class HelloHP {

    public static void main(String args[]) throws InterruptedException,
                                                  BrokenBarrierException {
        
        CyclicBarrier barrier = new CyclicBarrier(4);
        Party first = new Party(1000, barrier, "PARTY-1");
        Party second = new Party(2000, barrier, "PARTY-2");
        Party third = new Party(3000, barrier, "PARTY-3");
        Party fourth = new Party(4000, barrier, "PARTY-4");
        
        new Thread(first).start();
        new Thread(second).start();
        new Thread(third).start();                           
        new Thread(fourth).start();

        System.out.println(Thread.currentThread().getName() + " has finished");

    }

}

class Party implements Runnable{
    private int duration;
    private CyclicBarrier barrier;
    private String name;

    public Party(int duration, CyclicBarrier barrier, String name) {
        this.duration = duration;
        this.barrier = barrier;
        this.name = name;
    }

    public void run() {
        try {
            Thread.sleep(duration);
            System.out.println( name
                                + " is calling await()");
            barrier.await();
            System.out.println( name
                                + " has started running again");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}