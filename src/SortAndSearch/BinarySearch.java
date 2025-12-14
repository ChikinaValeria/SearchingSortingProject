package SortAndSearch;

public class BinarySearch {
    private static final int[] SEARCH_LIST = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static String search(int targetValue) {
        // Start the recursive search on the full list (from index 0 to the last index)
        boolean found = recursiveSearch(SEARCH_LIST, 0, SEARCH_LIST.length - 1, targetValue);

        return found ? "Found" : "Not found";
    }

    private static boolean recursiveSearch(int[] data, int min, int max, int target) {

        // Base case: If the 'min' index crosses the 'max' index, the element is not found
        if (min > max) {
            return false;
        }
        // Calculate the midpoint index
        int midpoint = min + (max - min) / 2;
        int midpointValue = data[midpoint];

        if (midpointValue == target) {
            return true;
        }
        // If the middle value is too high, search the LEFT half (from min to midpoint - 1)
        else if (midpointValue > target) {
            return recursiveSearch(data, min, midpoint - 1, target);
        }
        // If the middle value is too low, search the RIGHT half (from midpoint + 1 to max)
        else {
            return recursiveSearch(data, midpoint + 1, max, target);
        }
    }
}
