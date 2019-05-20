package threads;

public class ThreadTest {

    private static final int DELAY = 10;
    private static final int STEPS = 100;
    private static final double MAX_AMOUNT = 1000;

    public static void main(String[] args) {

        var bank = new Bank(4, 100000);

        Runnable task1 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(0, 1, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException ignored) {
            }
        };

        Runnable task2 = () -> {
            try {
                for (int i = 0; i < STEPS; i++) {
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(2, 3, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                }
            } catch (InterruptedException ignored) {
            }
        };

        new Thread(task1).start();
        new Thread(task2).start();
    }
}
