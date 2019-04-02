package my.backend.test.solution.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents business operation on account/s,
 * may consist of several movements on multiple parties;
 */
public class Operation {

    private final Account source;
    private final Account target;
    private final BigDecimal amount;


    private final List<Movement> movements;
    private final LocalDateTime operationDate;

    public Operation(Account source, Account target, BigDecimal amount, List<Movement> movements, LocalDateTime operationDate) {
        this.source = source;
        this.target = target;
        this.amount = amount;
        this.movements = movements;
        this.operationDate = operationDate;
    }


    public List<Movement> getMovements() {
        return Collections.unmodifiableList(movements);
    }

    public LocalDateTime getOperationDate() {
        return operationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Account getSource() {
        return source;
    }

    public Account getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(movements, operation.movements) &&
                Objects.equals(operationDate, operation.operationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movements, operationDate);
    }
}
