package my.backend.test.solution.exception;

public class SameAccountTransfer extends RuntimeException {
    public SameAccountTransfer() {
        super("Invalid target account for transfer!");
    }
}
