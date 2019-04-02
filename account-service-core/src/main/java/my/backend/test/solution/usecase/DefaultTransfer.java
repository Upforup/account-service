package my.backend.test.solution.usecase;

import my.backend.test.solution.data.OperationRepository;
import my.backend.test.solution.domain.Account;
import my.backend.test.solution.domain.Movement;
import my.backend.test.solution.domain.Operation;
import my.backend.test.solution.exception.NotEnoughFundsAvailable;
import my.backend.test.solution.exception.SameAccountTransfer;
import my.backend.test.solution.factory.MovementFactory;
import my.backend.test.solution.factory.OperationFactory;

import java.math.BigDecimal;

public class DefaultTransfer implements Transfer {

    private final OperationRepository store;

    public DefaultTransfer(OperationRepository store) {
        this.store = store;
    }

    /**
     * @param source
     * @param target
     * @param amount
     * @return
     */
    public Operation transfer(Account source, Account target, BigDecimal amount) {

        if (source.equals(target)) {
            throw new SameAccountTransfer();
        }

        Movement withdrawn = withdraw(source, amount);
        Movement deposit = deposit(target, amount);
        Operation operation = OperationFactory.transferOperation(withdrawn, deposit, amount);
        store.saveNewOperation(operation);
        return operation;
    }


    private Movement withdraw(Account source, BigDecimal amount) {
        checkSourceBalance(source, amount);
        Movement outMovement = MovementFactory.out(source, amount);
        source.acceptMovement(outMovement);
        return outMovement;
    }

    private Movement deposit(Account target, BigDecimal amount) {
        Movement in = MovementFactory.in(target, amount);
        target.acceptMovement(in);
        return in;
    }

    private void checkSourceBalance(Account source, BigDecimal amount) {
        if (source.getCurrentBalance().compareTo(amount) < 0)
            throw new NotEnoughFundsAvailable();
    }

}
