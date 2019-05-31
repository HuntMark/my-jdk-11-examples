package map;

import java.util.HashMap;
import java.util.Objects;

/**
 * This program demonstrates the use of a map with key type String and value type Employee.
 *
 * @author Cay Horstmann
 * @version 1.12 2015-06-21
 */
public class MapTest {
    public static void main(String[] args) {
        var staff = new HashMap<String, Employee>();
        staff.put("144-25-5464", new Employee("Amy Lee"));
        staff.put("567-24-2546", new Employee("Harry Hacker"));
        staff.put("157-62-7935", new Employee("Gary Cooper"));
        staff.put("456-62-5527", new Employee("Francesca Cruz"));

        // print all entries
        System.out.println(staff);

        // remove an entry
        staff.remove("567-24-2546");

        // replace an entry
        staff.put("456-62-5527", new Employee("Francesca Miller"));

        // look up a value
        System.out.println(staff.get("157-62-7935"));

        // iterate through all entries
        staff.forEach((k, v) -> System.out.println("key=" + k + ", value=" + v));
    }
}

class Employee {

    private final String name;

    Employee(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return name.equals(employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                '}';
    }
}
