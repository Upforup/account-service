package my.backend.test.solution.usecase;

import my.backend.test.solution.domain.Account;
import my.backend.test.solution.exception.AccountNotFound;
import my.backend.test.solution.data.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAccountInfoTest {

    @Mock
    AccountRepository infoProvider;


    @Test
    public void getAccountInfoWhenAccountExists() {
        String accNum = "account-number";
        Account output = Account.newInstance(accNum, BigDecimal.TEN, LocalDate.now());
        when(infoProvider.getAccountInfoByAccountNumber(accNum)).thenReturn(output);
        GetAccountInfo info = new GetAccountInfo(infoProvider);
        Account accountInfo = info.getAccountInfo(accNum);
        assertSame(output, accountInfo);
    }

    @Test(expected = AccountNotFound.class)
    public void getAccountInfoWhenAccountNotFound() {
        String accNum = "account-number";
        Account output = null;
        when(infoProvider.getAccountInfoByAccountNumber(accNum)).thenReturn(output);
        GetAccountInfo info = new GetAccountInfo(infoProvider);
        Account accountInfo = info.getAccountInfo(accNum);
    }
}