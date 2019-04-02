package my.backend.test.solution.service;

import my.backend.test.solution.domain.Account;
import my.backend.test.solution.domain.Operation;

import java.math.BigDecimal;
import java.util.function.Consumer;

public interface AccountOperations {

    Operation transfer(String sourceNumber, String targetNumber, BigDecimal amount, Consumer<Account> srcValidate);
    Account getAccountInfo(String number);


}
