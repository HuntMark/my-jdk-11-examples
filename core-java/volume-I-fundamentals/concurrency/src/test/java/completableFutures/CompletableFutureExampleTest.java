package completableFutures;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureExampleTest {

    @Test
    void should_be_created_completed_future() {
        var actual = CompletableFuture.completedFuture("completed").getNow("not completed");
        assertEquals("completed", actual);
    }

    @Test
    void should_not_be_completed_immediately() {
        Runnable task = () -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        };
        assertFalse(CompletableFuture.runAsync(task).isDone());
    }

    @Test
    void should_be_completed_later() throws InterruptedException {
        Runnable task = () -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        };
        var future = CompletableFuture.runAsync(task);
        //  let future complete
        Thread.sleep(100);
        assertTrue(future.isDone());
    }

    @Test
    void should_be_completed_exceptionally_when_is_daemon_assertion_failed_using_default_executor() throws InterruptedException {
        Runnable task = () -> {
            // when the ForkJoinPool.commonPool() is used then workers are daemon
            assertFalse(Thread.currentThread().isDaemon());
        };
        var future = CompletableFuture.runAsync(task);
        //  let future complete
        Thread.sleep(50);
        assertTrue(future.isDone());
        assertTrue(future.isCompletedExceptionally());
    }

    @Test
    void should_be_completed_successfully_when_is_daemon_assertion_successful_using_default_executor() throws InterruptedException {
        Runnable task = () -> {
            // when the ForkJoinPool.commonPool() is used then workers are daemon
            assertTrue(Thread.currentThread().isDaemon());
        };
        var future = CompletableFuture.runAsync(task);
        //  let future complete
        Thread.sleep(50);
        assertTrue(future.isDone());
        assertFalse(future.isCompletedExceptionally());
    }

    @Test
    void should_be_completed_exceptionally_when_is_daemon_assertion_failed_using_defined_executor() throws InterruptedException {
        var executor = Executors.newCachedThreadPool();
        Runnable task = () -> {
            // when the ExecutorService is used then workers aren't daemon
            assertTrue(Thread.currentThread().isDaemon());
        };
        var future = CompletableFuture.runAsync(task, executor);
        //  let future complete
        Thread.sleep(50);
        assertTrue(future.isDone());
        assertTrue(future.isCompletedExceptionally());
    }

    @Test
    void should_be_completed_successfully_when_is_daemon_assertion_successful_using_defined_executor() throws InterruptedException {
        var executor = Executors.newCachedThreadPool();
        Runnable task = () -> {
            // when the ExecutorService is used then workers aren't daemon
            assertFalse(Thread.currentThread().isDaemon());
        };
        var future = CompletableFuture.runAsync(task, executor);
        //  let future complete
        Thread.sleep(50);
        assertTrue(future.isDone());
        assertFalse(future.isCompletedExceptionally());
    }

    @Test
    void should_be_completed_later_as_function_in_the_then_apply_blocks_execution() {
        final long expectedExecutionTimeInMillis = 50;
        var start = Instant.now();
        CompletableFuture<String> future = CompletableFuture
                .completedFuture("message")
                .thenApply(string -> {
                    try {
                        Thread.sleep(expectedExecutionTimeInMillis);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    return string.toUpperCase();
                });
        var finish = Instant.now();
        assertEquals("MESSAGE", future.getNow("future will always be completed"));
        assertTrue(Duration.between(start, finish).toMillis() >= expectedExecutionTimeInMillis);
        assertFalse(future.isCompletedExceptionally());
    }
}
