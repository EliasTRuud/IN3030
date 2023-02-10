/**
 * A class that measures the runtimes of the algorithms implemented in the
 * 'FindMax' class.
 *
 * To use:
 *
 * Compile:
 * javac FindMax.java Exercise1.java
 *
 * Run:
 * java Exercise1 <n> (where <n> is the size of the array)
 *
 *
 * @author Shiela Kristoffersen
 *
 */

 import java.util.Arrays;

 class Exercise1 {
 
 
   /**
    * @param args Should recieve an integer 'n' that determines the size of the array.
    */
   public static void main(String[] args) {
 
 
     /**
      * 'n' is the size of the array.
      * 'seed' is the number used for generating a specific random sequence.
      * 'runs' is the number of times each algorithm is run. Choose an odd number to more easily calculate the median.
      */
     int n, seed = 100;
     int runs = 7;
 
 
     /**
      * Catching excpetions in the case of wrong user input.
      */
     try {
       n = Integer.parseInt(args[0]);
     } catch(ArrayIndexOutOfBoundsException e) {
       System.out.println("\nCorrect use of program is 'java Exercise1 <n>', where <n> is an integer.\n");
       return;
     } catch(NumberFormatException e) {
       System.out.println("\nPlease send in an integer <n>: 'java Exercise1 <n>'.\n");
       return;
     }
 
 
     FindMax fm = new FindMax(n, seed);
 
 
     /**
      * Storing the runtimes in arrays.
      */
     double[] runtimeSeq = new double[runs];
     double[] runtimePar_11 = new double[runs];
     double[] runtimePar_12 = new double[runs];
     double[] runtimePar_21 = new double[runs];
     double[] runtimePar_22 = new double[runs];
 
     System.out.println("\nTesting FindMax with n = " + n + " and cores = " + fm.cores + "\n");
 
     for (int i = 0; i < runs; i++) {
 
       System.out.println("...Run " + (i + 1) + "...");
 
       long start = System.nanoTime();
       fm.findMaxSeq();
       long end = System.nanoTime();
       runtimeSeq[i] = (end - start) / 1000000.0;
 
       start = System.nanoTime();
       fm.findMaxPar_11();
       end = System.nanoTime();
       runtimePar_11[i] = (end - start) / 1000000.0;
 
 
       start = System.nanoTime();
       fm.findMaxPar_12();
       end = System.nanoTime();
       runtimePar_12[i] = (end - start) / 1000000.0;
 
       start = System.nanoTime();
       fm.findMaxPar_21();
       end = System.nanoTime();
       runtimePar_21[i] = (end - start) / 1000000.0;
 
       start = System.nanoTime();
       fm.findMaxPar_22();
       end = System.nanoTime();
       runtimePar_22[i] = (end - start) / 1000000.0;
     }
 
 
     /**
      * Sorting the arrays in ascending order.
      */
     Arrays.sort(runtimeSeq);
     Arrays.sort(runtimePar_11);
     Arrays.sort(runtimePar_12);
     Arrays.sort(runtimePar_21);
     Arrays.sort(runtimePar_22);
 
 
     /**
      * Finding medians. Here I'm assuming that 'runs' is an odd number.
      */
     double medianSeq = runtimeSeq[runs / 2];
     double medianPar_11 = runtimePar_11[runs / 2];
     double medianPar_12 = runtimePar_12[runs / 2];
     double medianPar_21 = runtimePar_21[runs / 2];
     double medianPar_22 = runtimePar_22[runs / 2];
 
 
     /**
      * Printing out the maximum number found, the runtime and the speedup
      * for each algorithm.
      */
     System.out.println("\nSequential Algorithm");
     System.out.println("Max\t:\t" + fm.globalMax);
     System.out.println("Time\t:\t" + medianSeq + " ms");
 
     System.out.println("\nParallel Algorithm 1.1");
     printInfo(fm.globalMax, medianPar_11, medianSeq);
 
     System.out.println("\nParallel Algorithm 1.2");
     printInfo(fm.globalMax, medianPar_12, medianSeq);
 
     System.out.println("\nParallel Algorithm 2.1");
     printInfo(fm.globalMax, medianPar_21, medianSeq);
 
     System.out.println("\nParallel Algorithm 2.2");
     printInfo(fm.globalMax, medianPar_22, medianSeq);
   }
 
 
   /**
    * Calculates speedup, and prints out information to the terminal.
    * @param max        The maximum integer found.
    * @param runtimePar The runtime of the parallel algorithm.
    * @param runtimeSeq The runtime of the sequential algorithm.
    */
   static void printInfo(int max, double runtimePar, double runtimeSeq) {
     System.out.println("Max\t:\t" + max);
     System.out.println("Time\t:\t" + runtimePar + " ms");
     System.out.println("Speedup\t:\t" + runtimeSeq / runtimePar);
   }
 
 }