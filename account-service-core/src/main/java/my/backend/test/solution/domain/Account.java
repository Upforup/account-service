package my.backend.test.solution.domain;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Representation of client account used for transfers in this test app
 */
public class Account {

    private Long id;
    private String number;
    private BigDecimal currentBalance;
    private LocalDate dateOpen;

    private Account(Long id, String number, BigDecimal currentBalance, LocalDate dateOpen) {
        this.id = id;
        this.number = number;
        this.currentBalance = currentBalance.setScale(2, RoundingMode.HALF_UP);
        this.dateOpen = dateOpen;
    }

    public static Account newInstance(String number, BigDecimal currentBalance, LocalDate dateOpen) {
        return new Account(null,
                Objects.requireNonNull(number),
                Objects.requireNonNull(currentBalance),
                Objects.requireNonNull(dateOpen));
    }

    public static Account fetched(Long id, String number, BigDecimal currentBalance, LocalDate dateOpen) {
        return new Account(
                Objects.requireNonNull(id),
                Objects.requireNonNull(number),
                Objects.requireNonNull(currentBalance),
                Objects.requireNonNull(dateOpen)
        );
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public LocalDate getDateOpen() {
        return dateOpen;
    }

    public void acceptMovement(Movement movement) {
        this.currentBalance = movement.apply(currentBalance);
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", currentBalance=" + currentBalance +
                ", dateOpen=" + dateOpen +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number) &&
                Objects.equals(currentBalance, account.currentBalance) &&
                Objects.equals(dateOpen, account.dateOpen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, currentBalance, dateOpen);
    }


}
