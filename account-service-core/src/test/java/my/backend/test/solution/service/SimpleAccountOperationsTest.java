package my.backend.test.solution.service;

import my.backend.test.solution.data.AccountRepository;
import my.backend.test.solution.data.OperationRepository;
import my.backend.test.solution.domain.Account;
import my.backend.test.solution.domain.Operation;
import my.backend.test.solution.usecase.DefaultTransfer;
import my.backend.test.solution.usecase.GetAccountInfo;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class SimpleAccountOperationsTest {

    private AccountRepository infoProvider;
    private GetAccountInfo info;
    private DefaultTransfer transfer;
    private SimpleAccountOperations operations;
    private OperationRepository store;

    @Before
    public void setUp() {
        infoProvider = mock(AccountRepository.class);
        store = mock(OperationRepository.class);
        info = spy(new GetAccountInfo(infoProvider));
        transfer = spy(new DefaultTransfer(store));
        operations = new SimpleAccountOperations(info, transfer);
    }

    @Test
    public void transfer() {

        String sourceNumber = "sourceNumber";
        String targetNumber = "targetNumber";
        BigDecimal transferAmount = BigDecimal.TEN;

        Account source = Account.newInstance(sourceNumber, BigDecimal.TEN, LocalDate.now());
        Account target = Account.newInstance(targetNumber, BigDecimal.TEN, LocalDate.now());

        when(infoProvider.getAccountInfoByAccountNumber(sourceNumber)).thenReturn(source);
        when(infoProvider.getAccountInfoByAccountNumber(targetNumber)).thenReturn(target);

        Operation transferResult = operations.transfer(sourceNumber, targetNumber, transferAmount, noOp -> {});
        verify(transfer).transfer(source, target, transferAmount);

    }

    @Test
    public void getAccountInfo() {

        String sourceNumber = "sourceNumber";
        Account source = Account.newInstance(sourceNumber, BigDecimal.TEN, LocalDate.now());
        when(infoProvider.getAccountInfoByAccountNumber(sourceNumber)).thenReturn(source);
        Account accountInfo = operations.getAccountInfo(sourceNumber);
        assertSame(source, accountInfo);

    }
}