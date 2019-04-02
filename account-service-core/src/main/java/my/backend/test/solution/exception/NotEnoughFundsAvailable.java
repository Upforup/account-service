package my.backend.test.solution.exception;

public class NotEnoughFundsAvailable extends RuntimeException {

    public NotEnoughFundsAvailable() {
        super("Not enough funds available for transfer!");
    }
}
