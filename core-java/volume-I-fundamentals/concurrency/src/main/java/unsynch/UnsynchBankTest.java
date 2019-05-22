package unsynch;

import synch.Bank;

/**
 * This program shows data corruption when multiple threads access a data structure.
 *
 * @author Cay Horstmann
 * @version 1.32 2018-04-10
 */
public class UnsynchBankTest {

    private static final int NACCOUNTS = 100;
    private static final double INITIAL_BALANCE = 1000;
    private static final double MAX_AMOUNT = 1000;
    private static final int DELAY = 10;

    public static void main(String[] args) {
        var bank = new Bank(NACCOUNTS, INITIAL_BALANCE);
        for (int i = 0; i < NACCOUNTS; i++) {
            int fromAccount = i;
            Runnable r = () -> {
                try {
                    int toAccount = (int) (bank.size() * Math.random());
                    double amount = MAX_AMOUNT * Math.random();
                    bank.transfer(fromAccount, toAccount, amount);
                    Thread.sleep((int) (DELAY * Math.random()));
                } catch (InterruptedException ignore) {
                }
            };
            var t = new Thread(r);
            t.start();
        }
    }
}
