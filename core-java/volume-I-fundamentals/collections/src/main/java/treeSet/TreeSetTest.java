package treeSet;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * This program sorts a set of Item objects by comparing their descriptions.
 *
 * @author Cay Horstmann
 * @version 1.13 2018-04-10
 */
public class TreeSetTest {
    public static void main(String[] args) {
        var parts = new TreeSet<Item>();
        parts.add(new Item(1234, "Toaster"));
        parts.add(new Item(4562, "Widget"));
        parts.add(new Item(9912, "Modem"));
        System.out.println(parts);

        var sortByDescription = new TreeSet<>(Comparator.comparing(Item::getDescription));
        sortByDescription.addAll(parts);
        System.out.println(sortByDescription);
    }
}
