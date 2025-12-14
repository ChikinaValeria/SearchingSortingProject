package SortAndSearch;

public class QuickSort {

    /**
     * Sorts the array using the Quick Sort algorithm.
     * Average Time Complexity: O(n log n)
     * @param data The array of CountedInt to be sorted.
     */
    public static void sort(CountedInt[] data) {
        quickSort(data, 0, data.length - 1);
    }

    private static void quickSort(CountedInt[] data, int low, int high) {
        if (low < high) {
            // partitionIndex is the index of the pivot element after partition
            int partitionIndex = partition(data, low, high);

            // Recursively sort elements before and after partition
            quickSort(data, low, partitionIndex - 1);
            quickSort(data, partitionIndex + 1, high);
        }
    }

    private static int partition(CountedInt[] data, int low, int high) {
        // Choose the rightmost element as the pivot
        CountedInt pivot = data[high];

        // Index of smaller element
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (data[j].compareTo(pivot) <= 0) {
                i++;

                // Swap data[i] and data[j]
                CountedInt temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }

        // Swap data[i+1] (the element right after the smaller elements) and data[high] (the pivot)
        CountedInt temp = data[i + 1];
        data[i + 1] = data[high];
        data[high] = temp;

        return i + 1; // Return the pivot's new position
    }

    /**
     * Sorts the array using the Quick Sort algorithm (for simple demonstration).
     * @param data The array of integers to be sorted.
     */
    public static void sort(int[] data) {
        quickSort(data, 0, data.length - 1);
    }

    private static void quickSort(int[] data, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(data, low, high);
            quickSort(data, low, partitionIndex - 1);
            quickSort(data, partitionIndex + 1, high);
        }
    }

    private static int partition(int[] data, int low, int high) {
        int pivot = data[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (data[j] <= pivot) {
                i++;
                int temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }

        int temp = data[i + 1];
        data[i + 1] = data[high];
        data[high] = temp;

        return i + 1;
    }
}