package map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class UpdatingMapEntriesTest {

    public static void main(String[] args) {
        var integers = new int[10];
        for (int i = 0; i < integers.length; i++) {
            integers[i] = ThreadLocalRandom.current().nextInt(0, 10);
        }
        System.out.println(Arrays.toString(integers));

        printCountsNaive(integers);
        printCountsUsingGetOrDefault(integers);
        printCountsUsingPutIfAbsent(integers);
        printCountsUsingMerge(integers);
        printCountsUsingCompute(integers);
        printCountsUsingComputeIfPresent(integers);
        printCountsUsingComputeIfAbsent(integers);
    }

    private static void printCountsNaive(int[] integers) {
        var counts = new HashMap<Integer, Integer>();
        for (int integer : integers) {
            //noinspection Java8MapApi
            if (counts.get(integer) == null) {
                counts.put(integer, 1);
            } else {
                counts.put(integer, counts.get(integer) + 1);
            }
        }
        System.out.println(counts);
    }

    private static void printCountsUsingGetOrDefault(int[] integers) {
        var counts = new HashMap<Integer, Integer>();
        for (int integer : integers) {
            counts.put(integer, counts.getOrDefault(integer, 0) + 1);
        }
        System.out.println(counts);
    }

    private static void printCountsUsingPutIfAbsent(int[] integers) {
        var counts = new HashMap<Integer, Integer>();
        for (int integer : integers) {
            counts.putIfAbsent(integer, 0);
            counts.put(integer, counts.get(integer) + 1);
        }
        System.out.println(counts);
    }

    private static void printCountsUsingMerge(int[] integers) {
        var counts = new HashMap<Integer, Integer>();
        for (int integer : integers) {
            counts.merge(integer, 1, Integer::sum);
        }
        System.out.println(counts);
    }

    private static void printCountsUsingCompute(int[] integers) {
        var counts = new HashMap<Integer, Integer>();
        for (int i = 0; i < integers.length; i++) {
            counts.put(i, ThreadLocalRandom.current().nextBoolean() ? null : 0);
        }
        int nulls = 0;
        for (int integer : integers) {
            try {
                //noinspection ConstantConditions
                counts.compute(integer, (k, v) -> v + 1); // doesn't handle null values and throws NPE
            } catch (NullPointerException ex) {
                nulls++;
            }
        }
        System.out.println(counts + ", " + nulls + " times NPE threw");
    }

    private static void printCountsUsingComputeIfPresent(int[] integers) {
        var counts = new HashMap<Integer, Integer>();
        for (int i = 0; i < integers.length; i++) {
            counts.put(i, ThreadLocalRandom.current().nextBoolean() ? null : 0);
        }
        int nulls = 0;
        for (int integer : integers) {
            try {
                counts.computeIfPresent(integer, (k, v) -> v + 1); // ignores null values
            } catch (NullPointerException ex) {
                nulls++;
            }
        }
        System.out.println(counts + ", " + nulls + " times NPE threw");
    }

    private static void printCountsUsingComputeIfAbsent(int[] integers) {
        var counts = new HashMap<Integer, Integer>();
        int nulls = 0;
        for (int integer : integers) {
            try {
                counts.computeIfAbsent(integer, k -> ThreadLocalRandom.current().nextInt(10, 20)); // handles null values
            } catch (NullPointerException ex) {
                nulls++;
            }
        }
        System.out.println(counts + ", " + nulls + " times NPE threw");
    }
}
