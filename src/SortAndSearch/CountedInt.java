package SortAndSearch;

/**
 * A wrapper class for an integer value that tracks the number of comparisons
 * made across all instances
 */
public class CountedInt implements Comparable<CountedInt> {
    private static long comparisonCount = 0;
    private final int value;

    public CountedInt(int value) {
        this.value = value;
    }

    // Resets the global comparison counter to zero
    public static void resetCounter() {
        comparisonCount = 0;
    }

     //Returns the current total comparison count
    public static long getComparisonCount() {
        return comparisonCount;
    }

     //Compares this CountedInt with the specified object for order
     //The comparison count is incremented here
    @Override
    public int compareTo(CountedInt other) {
        // Increment the global counter every time a comparison is performed
        comparisonCount++;

        return Integer.compare(this.value, other.value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
