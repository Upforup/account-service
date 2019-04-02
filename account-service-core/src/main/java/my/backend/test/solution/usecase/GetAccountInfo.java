package my.backend.test.solution.usecase;

import my.backend.test.solution.domain.Account;
import my.backend.test.solution.exception.AccountNotFound;
import my.backend.test.solution.data.AccountRepository;

public class GetAccountInfo {

    private final AccountRepository provider;

    public GetAccountInfo(AccountRepository provider) {
        this.provider = provider;
    }

    public Account getAccountInfo(String accNumber) {
        Account accountInfoByAccountNumber = provider.getAccountInfoByAccountNumber(accNumber);
        if (accountInfoByAccountNumber == null) {
            throw new AccountNotFound();
        }
        return accountInfoByAccountNumber;
    }


}
