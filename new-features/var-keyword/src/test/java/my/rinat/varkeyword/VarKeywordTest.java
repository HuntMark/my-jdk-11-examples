package my.rinat.varkeyword;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * <p>
 * As of Java 10, you can declare local variables with the <i>var</i> keyword instead
 * of specifying their type, provided their type can be inferred from the initial
 * value.
 * </p>
 *
 * <p>
 * Note that the var keyword can only be used with local variables inside methods.
 * You must always declare the types of parameters and fields.
 * </p>
 */
class VarKeywordTest {

    @Test
    void test() {
        var name = "Some Object's Name";
        var someObject = new SomeObject(name);
        Assertions.assertEquals("Some Object's Name", someObject.getName());
    }

    @Getter
    @RequiredArgsConstructor
    private static class SomeObject {
        private final String name;
    }
}
