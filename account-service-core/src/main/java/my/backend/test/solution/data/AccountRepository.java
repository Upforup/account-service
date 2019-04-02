package my.backend.test.solution.data;

import my.backend.test.solution.domain.Account;

public interface AccountRepository {
    Account getAccountInfoByAccountNumber(String accNumber);
    long saveNewAccount(Account account);
}
