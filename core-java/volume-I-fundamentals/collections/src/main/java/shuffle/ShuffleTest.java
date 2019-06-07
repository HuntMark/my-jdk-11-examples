package shuffle;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This program demonstrates the random shuffle and sort algorithms.
 *
 * @author Cay Horstmann
 * @version 1.12 2018-04-10
 */
public class ShuffleTest {
    public static void main(String[] args) {
        var numbers = new ArrayList<Integer>();
        for (int i = 1; i <= 49; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        var winningCombination = numbers.subList(0, 6);
        Collections.sort(winningCombination);
        System.out.println(winningCombination);
        System.out.println(numbers);
    }
}
