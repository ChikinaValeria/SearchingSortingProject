package SortAndSearch;

public class MergeSort {

    /**
     * Sorts the array using the Merge Sort algorithm.
     * Uses CountedInt and compareTo() to track comparisons.
     * @param data The array of CountedInt objects to be sorted.
     */
    public static void sort(CountedInt[] data) {
        // call the main recursive sort method with the full array range
        mergeSort(data, 0, data.length - 1);
    }

    private static void mergeSort(CountedInt[] data, int low, int high) {
        // Base case: the sub-array has 1 or 0 elements, which means it's already sorted
        if (low < high) {
            // find the middle point of the sub-array
            int mid = low + (high - low) / 2;

            // recursively sort the first half
            mergeSort(data, low, mid);

            // recursively sort the second half
            mergeSort(data, mid + 1, high);

            // merge the two sorted parts
            merge(data, low, mid, high);
        }
    }

    private static void merge(CountedInt[] data, int low, int mid, int high) {
        // temporary arrays for the two halves
        int size1 = mid - low + 1;
        int size2 = high - mid;

        CountedInt[] leftArray = new CountedInt[size1];
        CountedInt[] rightArray = new CountedInt[size2];

        // copy data to the temporary arrays
        for (int i = 0; i < size1; ++i) {
            leftArray[i] = data[low + i];
        }
        for (int j = 0; j < size2; ++j) {
            rightArray[j] = data[mid + 1 + j];
        }

        // initial indices for the temporary arrays and the main array
        int i = 0, j = 0;
        int k = low; // starting index for the merged array segment

        // merge the temporary arrays back into the original array
        while (i < size1 && j < size2) {
            if (leftArray[i].compareTo(rightArray[j]) <= 0) {
                data[k] = leftArray[i];
                i++;
            } else {
                data[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // copy remaining elements of leftArray[] if any
        while (i < size1) {
            data[k] = leftArray[i];
            i++;
            k++;
        }

        // copy remaining elements of rightArray[] if any
        while (j < size2) {
            data[k] = rightArray[j];
            j++;
            k++;
        }
    }

    /**
     * Sorts the array using the Merge Sort algorithm.
     * @param data The array of integers to be sorted.
     */
    public static void sort(int[] data) {
        mergeSort(data, 0, data.length - 1);
    }

    private static void mergeSort(int[] data, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            mergeSort(data, low, mid);
            mergeSort(data, mid + 1, high);
            merge(data, low, mid, high);
        }
    }

    private static void merge(int[] data, int low, int mid, int high) {
        int size1 = mid - low + 1;
        int size2 = high - mid;

        int[] leftArray = new int[size1];
        int[] rightArray = new int[size2];

        for (int i = 0; i < size1; ++i) {
            leftArray[i] = data[low + i];
        }
        for (int j = 0; j < size2; ++j) {
            rightArray[j] = data[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = low;

        while (i < size1 && j < size2) {
            if (leftArray[i] <= rightArray[j]) {
                data[k] = leftArray[i];
                i++;
            } else {
                data[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < size1) {
            data[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < size2) {
            data[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
