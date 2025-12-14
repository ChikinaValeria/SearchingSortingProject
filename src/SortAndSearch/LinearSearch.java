package SortAndSearch;

public class LinearSearch {

    /**
     * Linear Search (Sequential Search) checks every element in the list one by one
     * until a match is found or the end of the list is reached (O(n) time complexity).
     */
    private static final int[] SEARCH_LIST = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static String search(int targetValue) {

        for (int i = 0; i < SEARCH_LIST.length; i++) {
            int currentElement = SEARCH_LIST[i];
            if (currentElement == targetValue) {
                return "Found";
            }
        }

        return "Not found";
    }
}
