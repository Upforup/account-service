package my.backend.test.solution.domain;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;

/**
 * This class represents accounting movement on account
 */
public class Movement {

    private final Account target;
    private final BigDecimal amount;
    private final LocalDateTime createdOn;
    private final Direction direction;
    private final Function<BigDecimal, BigDecimal> computeStrategy;

    public Movement(Account target,
                    BigDecimal amount,
                    LocalDateTime createdOn,
                    Direction direction,
                    Function<BigDecimal, BigDecimal> computeStrategy) {

        this.target = target;
        this.amount = amount;
        this.createdOn = createdOn;
        this.direction = direction;
        this.computeStrategy = computeStrategy;

    }

    public Account getTarget() {
        return target;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Direction getDirection() {
        return direction;
    }

    /**
     * Computes value of balance after applying movement amount
     * to passed parameter
     * Behaviour of this operation depends on movement type: IN/OUT;
     * Strategies are defined in  {@link my.backend.test.solution.factory.MovementFactory}
     *
     * @param initial
     * @return value after applying movement amount
     */
    public BigDecimal apply(BigDecimal initial) {
        return computeStrategy.apply(initial);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return Objects.equals(target, movement.target) &&
                Objects.equals(amount, movement.amount) &&
                Objects.equals(createdOn, movement.createdOn) &&
                direction == movement.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, amount, createdOn, direction);
    }

    public enum Direction {

        IN((byte) 1), OUT((byte) 0);

        private final byte value;

        Direction(byte value) {
            this.value = value;
        }

        public byte getValue() {
            return value;
        }
    }
}
