package my.backend.test.solution.usecase;

import my.backend.test.solution.domain.Account;
import my.backend.test.solution.domain.Operation;

import java.math.BigDecimal;

public interface Transfer {
    Operation transfer(Account source, Account target, BigDecimal amount);
}
