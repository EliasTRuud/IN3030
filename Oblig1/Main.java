package Oblig1;

public class Main {

    public static void main(String[] args) {
        /*
        findMaxParallel() should become the most efficient method once the array grows large enough (>100000000 in
        my case) due to friendlier caching. Before that findMaxParallel2() and findMaxSeq() can be faster.
        I recommend running the code multiple times (i.e. 7 runs like the obligs) and considering the median value, as
        runtimes can be very easily affected by external factors.
        */
        int n = 100000000;
        int seed = 738;
        FindMax fm = new FindMax(n, seed);

        long start = System.nanoTime();
        fm.findMaxSeq();
        long end = System.nanoTime();
        System.out.println("Max value " + fm.globalMax);
        System.out.println("Sequential time: " + ((end - start) / 1000000.0) + "ms");

        start = System.nanoTime();
        fm.findMaxParallel();
        end = System.nanoTime();
        System.out.println("Max value " + fm.globalMax);
        System.out.println("Parallel 1 time: " + ((end - start) / 1000000.0) + "ms");

        start = System.nanoTime();
        fm.findMaxParallel2();
        end = System.nanoTime();
        System.out.println("Max value " + fm.globalMax);
        System.out.println("Parallel 2 time: " + ((end - start) / 1000000.0) + "ms");
    }
}