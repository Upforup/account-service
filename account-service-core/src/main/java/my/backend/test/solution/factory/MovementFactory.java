package my.backend.test.solution.factory;

import my.backend.test.solution.domain.Account;
import my.backend.test.solution.domain.Movement;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Factory class for used for creation of Movement instances
 * this class defines strategies for applying movements to balance
 */
public class MovementFactory {

    /**
     * Creates OUT movement on account,
     * it means that account balance was decreased by amount of this movement.
     *
     * @param acc    target account of movement
     * @param amount - amount of movement, always positive
     * @return instance
     */
    public static Movement out(Account acc, BigDecimal amount) {
        return new Movement(
                acc,
                amount,
                LocalDateTime.now(),
                Movement.Direction.OUT,
                (BigDecimal initial) -> initial.add(amount.negate())
        );
    }

    /**
     * Creates IN movement on account,
     * it means that account balance was increased by amount of this movement
     *
     * @param acc    target account of movement
     * @param amount - amount of movement
     * @return instance
     */
    public static Movement in(Account acc, BigDecimal amount) {
        return new Movement(
                acc,
                amount,
                LocalDateTime.now(),
                Movement.Direction.IN,
                (BigDecimal initial) -> initial.add(amount)
        );
    }

}
