package Oblig1;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/** Task 2 of weekly assignment L2 2023 (Group2).
 *  Class with 1 runnable worker class implementing the b.4 method of updating global max (using local variables),
 *  and 3 methods to find the maximum number in an array.
 *  findMaxSeq() = sequential solution
 *  findMaxParallel() = using b.1 method of splitting array between threads
 *  findMaxParallel2() = using b.2 method of splitting array between threads
 *
 *  Credits to Shiela Kristoffersen's code for providing a structure to this solution.
 */
public class FindMax {

    int[] a;
    int n;
    int globalMax;
    int cores = Runtime.getRuntime().availableProcessors();

    /*
     Unlike the group session, i changed the method to update globalMax from a synchronized method to semaphores to
     showcase how it can be used. The lock is set to 1, which means that only 1 thread is allowed to enter the section
     of the code surrounded by the semaphore. Remember to always release the lock when finished to prevent deadlocks.
     */
    Semaphore lock = new Semaphore(1);
    // The number of parties for the cyclicBarrier must include the main thread, thus the +1.
    CyclicBarrier cb = new CyclicBarrier(cores + 1);

    FindMax(int n, int seed) {
        this.n = n;
        this.a = randomArray(n, seed);
    }

    int[] randomArray(int n, int seed) {
        Random rd = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rd.nextInt(n);
        }
        return a;
    }

    /**
     * Sequential solution to finding max.
     */
    void findMaxSeq() {
        globalMax = a[0];
        for (int i = 0; i < a.length; i++) {
            if (globalMax < a[i]) globalMax = a[i];
        }
    }

    /**
     * Runnable worker class. Updates global max after each thread has found their own local max value.
     */
    class Worker implements Runnable {
        int ind;
        int start;
        int end;
        int jump;
        int localMax;

        /**
         * Worker constructor.
         * @param ind   The id of the thread.
         * @param start The first index the thread has to check.
         * @param end   The last index the thread has to check.
         * @param jump  The number of indexes the thread should jump do to reach the next index it has to check.
         *              One jump is the same as moving one index ahead.
         */
        Worker(int ind, int start, int end, int jump) {
            this.ind = ind;
            this.start = start;
            this.end = end;
            this.jump = jump;
        }

        public void run() {
            localMax = a[start];
            for (int i = start; i < end; i += jump) {
                if (localMax < a[i]) localMax = a[i];
            }

            try {
                lock.acquire();
                if (globalMax < localMax) globalMax = localMax;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.release();
            }

            try {
                cb.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * First parallel solution to finding max value in an array.
     * Array here is split evenly between threads in continuous segments (thus jump = 1).
     */
    void findMaxParallel() {
        globalMax = a[0];
        int sizeOfSegment = a.length / cores;
        int jump = 1;

        for (int i = 0; i < cores; i++) {
            (new Thread
                    (new Worker(i, i * sizeOfSegment, (i+1) * sizeOfSegment, jump))).start();
        }

        // This barrier here prevents the main thread from continuing before all other threads have finished their tasks
        // Without it the main thread could print a globalMax value before the true globalMax has been found.
        try {
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * First parallel solution to finding max value in an array.
     * Each thread goes through the whole array, jumping X indexes each time where X = cores.
     */
    void findMaxParallel2() {
        globalMax = a[0];
        int jump = cores;
        for (int i =0; i < cores; i++) {
            (new Thread
                    (new Worker(i, i, a.length, jump))).start();
        }

        try {
            cb.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}