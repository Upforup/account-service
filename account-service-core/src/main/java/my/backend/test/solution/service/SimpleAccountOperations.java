package my.backend.test.solution.service;


import my.backend.test.solution.domain.Account;
import my.backend.test.solution.domain.Operation;
import my.backend.test.solution.usecase.DefaultTransfer;
import my.backend.test.solution.usecase.GetAccountInfo;
import my.backend.test.solution.usecase.Transfer;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class SimpleAccountOperations implements AccountOperations {

    private final GetAccountInfo info;
    private final Transfer transfer;


    public SimpleAccountOperations(GetAccountInfo info, Transfer transfer) {
        this.info = info;
        this.transfer = transfer;
    }

    @Override
    public Operation transfer(String sourceNumber, String targetNumber, BigDecimal amount, Consumer<Account> srcValidate) {
        Account source = getAccountInfoValidated(sourceNumber, srcValidate);
        Account target = getAccountInfo(targetNumber);
        return this.transfer.transfer(source, target, amount);
    }



    @Override
    public Account getAccountInfo(String number) {
        return info.getAccountInfo(number);
    }

    private Account getAccountInfoValidated(String number, Consumer<Account> validate) {
        Account accountInfo = getAccountInfo(number);
        validate.accept(accountInfo);
        return accountInfo;
    }

}
