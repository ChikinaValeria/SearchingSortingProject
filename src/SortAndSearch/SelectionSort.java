package SortAndSearch;

public class SelectionSort {

    /**
     * Sorts the array using the Selection Sort algorithm.
     * Time Complexity: O(n^2)
     * @param data The array of CountedInt to be sorted.
     */
    public static void sort(CountedInt[] data) {
        int n = data.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in unsorted array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (data[j].compareTo(data[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            // Swap the found minimum element with the first element of the unsorted subarray
            CountedInt temp = data[minIndex];
            data[minIndex] = data[i];
            data[i] = temp;
        }
    }


    /**
     * Sorts the array using the Selection Sort algorithm (for simple demonstration).
     * @param data The array of integers to be sorted.
     */
    public static void sort(int[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = data[minIndex];
            data[minIndex] = data[i];
            data[i] = temp;
        }
    }
}
