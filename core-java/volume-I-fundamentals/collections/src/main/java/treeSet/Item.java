package treeSet;

import java.util.Objects;

/**
 * An item with a description and a part number.
 */
class Item implements Comparable<Item> {

    private final int partNumber;
    private final String description;

    /**
     * Constructs an item.
     *
     * @param partNumber  the item's part number
     * @param description the item's description
     */
    Item(int partNumber, String description) {
        this.partNumber = partNumber;
        this.description = description;
    }

    String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return partNumber == item.partNumber &&
                description.equals(item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(partNumber, description);
    }

    @Override
    public String toString() {
        return "Item{" +
                "partNumber=" + partNumber +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(Item o) {
        int diff = Integer.compare(partNumber, o.partNumber);
        return diff != 0 ? diff : description.compareTo(o.description);
    }
}
