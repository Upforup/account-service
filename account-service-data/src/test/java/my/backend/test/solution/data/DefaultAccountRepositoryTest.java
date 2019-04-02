package my.backend.test.solution.data;

import my.backend.test.solution.config.DslConfigFactory;
import my.backend.test.solution.config.HikariDsFactory;
import my.backend.test.solution.domain.Account;
import my.backend.test.solution.setup.SetupH2Database;
import org.jooq.DSLContext;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static my.backend.test.solution.data.generated.Sequences.ACCOUNT_SEQ;
import static my.backend.test.solution.data.generated.Tables.ACCOUNT;
import static my.backend.test.solution.data.generated.Tables.ACCOUNT_MOVEMENT;
import static my.backend.test.solution.data.generated.Tables.OPERATION;
import static org.junit.Assert.*;

public class DefaultAccountRepositoryTest {

    private DSLContext dsl;
    private DefaultAccountRepository accountRepository;

    @Before
    public void setUp() {

        DataSource dataSource = HikariDsFactory.getDataSource();
        DataSourceConnectionProvider dataSourceConnectionProvider = new DataSourceConnectionProvider(dataSource);
        DefaultConfiguration defaultConfiguration = DslConfigFactory.getConfig(dataSourceConnectionProvider);
        SetupH2Database setupH2Database = new SetupH2Database(dataSourceConnectionProvider);

        dsl = new DefaultDSLContext(defaultConfiguration);
        accountRepository = new DefaultAccountRepository(dsl);

        setupH2Database.setUp();
    }




    private void initData(LocalDate dateOpen, String accNum) {
        dsl.insertInto(ACCOUNT)
                .set(ACCOUNT.ID, ACCOUNT_SEQ.nextval())
                .set(ACCOUNT.ACCOUNT_NUMBER, accNum)
                .set(ACCOUNT.ACCOUNT_BALANCE, BigDecimal.TEN)
                .set(ACCOUNT.DATE_OPEN, Date.valueOf(dateOpen))
                .execute();
    }


    @Test
    public void getAccountInfoByAccountNumber() {
        LocalDate now = LocalDate.now();
        String accNum = "1";
        initData(now, accNum);

        Account accountInfoByAccountNumber = accountRepository.getAccountInfoByAccountNumber(accNum);
        assertEquals(0, BigDecimal.TEN.compareTo(accountInfoByAccountNumber.getCurrentBalance()));
        assertEquals(accNum, accountInfoByAccountNumber.getNumber());
        assertEquals(now, accountInfoByAccountNumber.getDateOpen());

    }
}