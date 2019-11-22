package completableFutures;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletableFutureExampleTest {
    @Test
    void should_be_created_completed_future() {
        var actual = CompletableFuture.completedFuture("completed").getNow("not completed");
        assertEquals("completed", actual);
    }
}
