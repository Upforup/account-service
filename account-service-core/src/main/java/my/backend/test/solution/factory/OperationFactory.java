package my.backend.test.solution.factory;

import my.backend.test.solution.domain.Account;
import my.backend.test.solution.domain.Movement;
import my.backend.test.solution.domain.Operation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class OperationFactory {


    public static Operation transferOperation(Movement withdrawal, Movement deposit, BigDecimal amount) {
        return new Operation(
                withdrawal.getTarget(),
                deposit.getTarget(),
                amount,
                Arrays.asList(withdrawal, deposit),
                LocalDateTime.now());
    }
}
