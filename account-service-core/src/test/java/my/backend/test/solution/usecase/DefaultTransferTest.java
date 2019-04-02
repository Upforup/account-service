package my.backend.test.solution.usecase;

import my.backend.test.solution.data.OperationRepository;
import my.backend.test.solution.domain.Account;
import my.backend.test.solution.domain.Movement;
import my.backend.test.solution.domain.Operation;
import my.backend.test.solution.exception.NotEnoughFundsAvailable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTransferTest {

    private DefaultTransfer transfer;
    private OperationRepository operationRepository;

    @Before
    public void setUp() {
        operationRepository = mock(OperationRepository.class);
        transfer = new DefaultTransfer(operationRepository);
    }


    /**
     * DefaultTransfer operation should return Operation object
     * with two movements, both with amount equal to passed amount parameter:
     * <ul>
     * <li>one movement should be of type OUT on source account
     * <li>second movement should be of type IN on target account
     * <ul/>
     * Accounts balnces should be corrected
     *
     * @see Operation
     * @see Movement
     * @see Account
     */
    @Test
    public void transferWhenSourceHasEnoughFunds() {


        BigDecimal amt = BigDecimal.TEN;
        Account source = Account.newInstance("1", amt, LocalDate.now());
        Account target = Account.newInstance("2", BigDecimal.ZERO, LocalDate.now());


        Operation transferResult = transfer.transfer(source, target, amt);

        List<Movement> movements = transferResult.getMovements();

        assertEquals(2, movements.size());
        assertEquals(0, amt.compareTo(target.getCurrentBalance()));
        assertEquals(0, BigDecimal.ZERO.compareTo(source.getCurrentBalance()));

        Movement withdrawal = movements.get(0);

        assertEquals(Movement.Direction.OUT, withdrawal.getDirection());
        assertEquals(amt, withdrawal.getAmount());
        assertSame(source, withdrawal.getTarget());


        Movement deposit = movements.get(1);

        assertEquals(Movement.Direction.IN, deposit.getDirection());
        assertEquals(amt, deposit.getAmount());
        assertSame(target, deposit.getTarget());

        verify(operationRepository).saveNewOperation(transferResult);
    }

    @Test(expected = NotEnoughFundsAvailable.class)
    public void transferWhenSourceHasNotEnoughFunds() {


        BigDecimal amt = BigDecimal.TEN;
        Account source = Account.newInstance( "1", BigDecimal.ZERO, LocalDate.now());
        Account target = Account.newInstance("2", BigDecimal.TEN, LocalDate.now());


        Operation transferResult = transfer.transfer(source, target, amt);

    }
}