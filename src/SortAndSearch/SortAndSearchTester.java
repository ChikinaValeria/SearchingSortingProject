package SortAndSearch;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Arrays;


public class SortAndSearchTester {

    public static void main(String[] args) {
        System.setProperty("org.apache.logging.log4j.simplelog.StatusLogger.level", "OFF");

        Scanner scanner = new Scanner(System.in);
        String choice = "";

        while (!choice.equalsIgnoreCase("q")) {
            displayMenu();

            choice = scanner.nextLine().trim();

            processChoice(choice, scanner);
            System.out.println();
        }

        System.out.println("Program terminated. Goodbye!");
        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("Menu of Searching and Sorting Testbed.");
        System.out.println();
        System.out.println("1)  Linear searching");
        System.out.println("2)  Binary searching");
        System.out.println("3)  O(n^2) type of sorting");
        System.out.println("4)  O(n*log(n)) type of searching");
        System.out.println("5)  Sorting performance");
        System.out.println();
        System.out.println("(q/Q) Quit");
        System.out.println();
        System.out.print("Your choice: ");
    }

    public static void processChoice(String choiceStr, Scanner scanner) {
        switch (choiceStr.toLowerCase()) {
            case "1":
                handleLinearSearch(scanner);
                break;
            case "2":
                handleBinarySearch(scanner);;
                break;
            case "3":
                handleBubbleSort();
                break;
            case "4":
                handleMergeSort();
                break;
            case "5":
                handleSortingPerformance();
                break;
            case "q":
                break;
            default:
                System.out.println("Error: Unknown option. Please enter a number from 1 to 5 or 'Q'.");
                break;
        }


    }

    private static void handleLinearSearch(Scanner scanner) {
        System.out.print("In the list are values 0, ..., 9; which value would you like to search with linear search? ");

        try {
            int target = scanner.nextInt();
            String result = LinearSearch.search(target);
            System.out.println(result);

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter an integer value.");
        } finally {
            scanner.nextLine();
        }
    }

    private static void handleBinarySearch(Scanner scanner) {
        System.out.print("In the list are values 0, ..., 9; which value would you like to search with binary search? ");

        try {
            int target = scanner.nextInt();
            String result = BinarySearch.search(target);
            System.out.println(result);

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input. Please enter an integer value.");
        } finally {
            scanner.nextLine();
        }
    }

    private static int[] generateRandomIntArray(int size, int maxBound) {
        Random rand = new Random();
        int[] data = new int[size];

        // This sets up the range. If maxBound is 100, we get numbers from -50 to 49
        // We subtract the 'offset' (half of maxBound) to shift the range into negative values
        // If maxBound is 100, the offset is 50
        int offset = maxBound / 2;

        for (int i = 0; i < size; i++) {
            // rand.nextInt(maxBound) creates a number from 0 up to (maxBound - 1)
            // Subtracting the offset shifts the range to include negative numbers
            data[i] = rand.nextInt(maxBound) - offset;
        }
        return data;
    }

    private static void handleBubbleSort() {
        int[] data = generateRandomIntArray(10, 200);

        System.out.println();
        System.out.println("Data set before bubble sorting:");
        printArray(data);
        System.out.println();

        BubbleSort.sort(data);

        System.out.println("Data set after bubble sorting:");
        printArray(data);
    }

    private static void handleMergeSort() {
        int[] data = generateRandomIntArray(10, 200);

        System.out.println();
        System.out.println("Data set before merge sorting:");
        printArray(data);
        System.out.println();

        MergeSort.sort(data);

        System.out.println("Data set after merge sorting:");
        printArray(data);
    }

    // generates a random array of CountedInts
    private static CountedInt[] generateRandomCountedIntArray(int size, int maxBound) {
        Random rand = new Random();
        CountedInt[] data = new CountedInt[size];
        int offset = maxBound / 2;

        for (int i = 0; i < size; i++) {
            data[i] = new CountedInt(rand.nextInt(maxBound) - offset);
        }
        return data;
    }

    /**
     * Helper to clone a CountedInt array
     */
    private static CountedInt[] cloneArray(CountedInt[] original) {
        CountedInt[] clone = new CountedInt[original.length];
        // a deep copy of the objects themselves, not just the references
        for (int i = 0; i < original.length; i++) {
            clone[i] = new CountedInt(original[i].getValue());
        }
        return clone;
    }

    private static void printArray(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i]);
            if (i < data.length - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    private static void handleSortingPerformance() {
        int[] sizes = {1000, 2000, 4000, 8000, 16000, 32000};
        String[] algorithms = {"BubbleSort", "SelectionSort", "MergeSort", "QuickSort"};
        int maxBound = 100000;

        // Structure for storing all results: [Algorithm][Metric (0:comp, 1:ms)][Size]
        // 4 algorithms * 2 metrics = 8 data rows
        long[][] comparisonResults = new long[algorithms.length * 2][sizes.length];

        // data collection
        for (int i = 0; i < sizes.length; i++) {
            int size = sizes[i];
            CountedInt[] originalData = generateRandomCountedIntArray(size, maxBound);

            testAndStore(size, originalData, BubbleSort::sort, comparisonResults, 0, 1, i);
            testAndStore(size, originalData, SelectionSort::sort, comparisonResults, 2, 3, i);
            testAndStore(size, originalData, MergeSort::sort, comparisonResults, 4, 5, i);
            testAndStore(size, originalData, QuickSort::sort, comparisonResults, 6, 7, i);
        }

        // formatted table output
        System.out.println();
        System.out.printf("%-35s", "");
        for (int size : sizes) {
            System.out.printf("%15d", size);
        }
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        String[] rowNames = {
                "BubbleSort,random,comparisons", "BubbleSort,random,ms",
                "SelectionSort,random,comparisons", "SelectionSort,random,ms",
                "MergeSort,random,comparisons", "MergeSort,random,ms",
                "QuickSort,random,comparisons", "QuickSort,random,ms"
        };

        // indices in the comparisonResults array corresponding to the desired display order
        // Bubble (0,1) -> Selection (2,3) -> Merge (4,5) -> Quick (6,7)
        int[] displayIndices = {0, 1, 2, 3, 4, 5, 6, 7};

        for (int i = 0; i < rowNames.length; i++) {
            int rowIdx = displayIndices[i];
            System.out.printf("%-35s", rowNames[rowIdx]);

            for (int j = 0; j < sizes.length; j++) {
                System.out.printf("%15d", comparisonResults[rowIdx][j]);
            }
            System.out.println();
        }

        // excel Export
        // create the Excel file with charts based on the collected data
        ExcelExporter.exportDataWithCharts(sizes, comparisonResults);
    }

    // helper method for sorting and saving results
    private static void testAndStore(int size, CountedInt[] originalData, SortMethod sortFunc, long[][] results, int compRow, int msRow, int sizeCol) {
        CountedInt[] data = cloneArray(originalData);
        CountedInt.resetCounter();

        long startTime = System.nanoTime();
        sortFunc.sort(data);
        long endTime = System.nanoTime();

        long durationMs = (endTime - startTime) / 1_000_000;
        long comparisons = CountedInt.getComparisonCount();

        results[compRow][sizeCol] = comparisons;
        results[msRow][sizeCol] = durationMs;
    }

    /**
     * A functional interface for a sorting method that takes a CountedInt array.
     */
    @FunctionalInterface
    interface SortMethod {
        void sort(CountedInt[] data);
    }

    /**
     * Executes the sorting, monitors time and comparisons, and prints the results.
     */
    private static void testSortAlgorithm(int size, String algoName, CountedInt[] originalData, SortMethod sortFunc) {
        // deep clone the original array to ensure each sort starts with the same data
        CountedInt[] data = cloneArray(originalData);

        // reset the counter before starting the sort
        CountedInt.resetCounter();

        // record start time using System.nanoTime() for high precision
        long startTime = System.nanoTime();

        // execute the sort function
        sortFunc.sort(data);

        // record end time and calculate elapsed milliseconds
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;

        // get the total comparison count
        long comparisons = CountedInt.getComparisonCount();

        System.out.printf("%d, %s, random, comparisons, %d%n", size, algoName, comparisons);
        System.out.printf("%d, %s, random, ms, %d%n", size, algoName, durationMs);
    }
}
