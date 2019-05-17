package pair1;

import pair.Pair;

public class PairTest1 {
    public static void main(String[] args) {
        String[] words = {"Mary", "had", "a", "little", "lamb"};
        Pair<String> mm = ArrayAlg.minmax(words);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
        System.out.println("middle = " + ArrayAlg.getMiddle(words));
    }
}

class ArrayAlg {

    /**
     * Gets the minimum and maximum of an array of strings.
     *
     * @param a an array of strings
     * @return a pair with the min and max values, or null if <code>a</code> is null or empty
     */
    static Pair<String> minmax(String[] a) {
        if (a == null || a.length == 0) {
            return null;
        }

        String min = a[0];
        String max = a[0];

        for (String s : a) {
            if (min.compareTo(s) > 0) {
                min = s;
            }
            if (max.compareTo(s) < 0) {
                max = s;
            }
        }

        return new Pair<>(min, max);
    }

    @SafeVarargs
    static <T> T getMiddle(T... a) {
        return a[a.length / 2];
    }

    static <T extends Comparable> T min(T[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        T smallest = a[0];

        for (T s : a) {
            //noinspection unchecked
            if (smallest.compareTo(s) > 0) {
                smallest = s;
            }
        }

        return smallest;
    }
}
