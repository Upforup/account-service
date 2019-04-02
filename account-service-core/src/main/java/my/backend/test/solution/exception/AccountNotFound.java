package my.backend.test.solution.exception;

public class AccountNotFound extends RuntimeException {
    public AccountNotFound() {
        super("Account not found with such number!");
    }
}
