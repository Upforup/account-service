package my.backend.test.solution.data;

import my.backend.test.solution.config.DslConfigFactory;
import my.backend.test.solution.config.HikariDsFactory;
import my.backend.test.solution.data.generated.tables.records.AccountMovementRecord;
import my.backend.test.solution.data.generated.tables.records.OperationRecord;
import my.backend.test.solution.domain.Account;
import my.backend.test.solution.domain.Operation;
import my.backend.test.solution.factory.MovementFactory;
import my.backend.test.solution.factory.OperationFactory;
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
import static my.backend.test.solution.data.generated.Tables.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DefaultOperationRepositoryTest {


    private DSLContext dsl;
    private DefaultOperationRepository operationRepository;

    @Before
    public void setUp() {

        DataSource dataSource = HikariDsFactory.getDataSource();
        DataSourceConnectionProvider dataSourceConnectionProvider = new DataSourceConnectionProvider(dataSource);
        DefaultConfiguration defaultConfiguration = DslConfigFactory.getConfig(dataSourceConnectionProvider);
        SetupH2Database setupH2Database = new SetupH2Database(dataSourceConnectionProvider);

        dsl = new DefaultDSLContext(defaultConfiguration);
        operationRepository = new DefaultOperationRepository(dsl);

        setupH2Database.setUp();

    }

    private void clear() {
        dsl.deleteFrom(ACCOUNT_MOVEMENT).execute();
        dsl.deleteFrom(OPERATION).execute();
        dsl.deleteFrom(ACCOUNT).execute();
    }

    private Account initAcc(LocalDate dateOpen, String accNum, BigDecimal amt) {
        Long id = dsl.nextval(ACCOUNT_SEQ);

        dsl.insertInto(ACCOUNT)
                .set(ACCOUNT.ID, id)
                .set(ACCOUNT.ACCOUNT_NUMBER, accNum)
                .set(ACCOUNT.ACCOUNT_BALANCE, amt)
                .set(ACCOUNT.DATE_OPEN, Date.valueOf(dateOpen))
                .execute();

        return Account.fetched(id, accNum, amt, dateOpen);

    }


    @Test
    public void saveNewOperation() {
        String src = "src";
        String tgt = "tgt";
        LocalDate now = LocalDate.now();
        BigDecimal amt = BigDecimal.TEN;

        Account srcAcc = initAcc(now, src, amt);
        Account tgtAcc = initAcc(now, tgt, BigDecimal.ZERO);


        Operation operation = OperationFactory.transferOperation(
                MovementFactory.out(srcAcc, amt),
                MovementFactory.in(tgtAcc, amt),
                amt);

        Long operationId = operationRepository.saveNewOperation(operation);

        OperationRecord operationRecord = dsl.selectFrom(OPERATION).where(OPERATION.ID.eq(operationId)).fetchOne();

        assertNotNull(operationRecord);
        assertEquals(0, operationRecord.getAmount().compareTo(amt));

        AccountMovementRecord srcMv = dsl.selectFrom(ACCOUNT_MOVEMENT)
                .where(ACCOUNT_MOVEMENT.OPERATION_ID.eq(operationId)
                        .and(ACCOUNT_MOVEMENT.ACCOUNT_ID.eq(srcAcc.getId())))
                .fetchOne();

        assertEquals(0, amt.compareTo(srcMv.getAmount()));
        assertEquals(0, srcMv.getDirection().compareTo((byte) 0));

        AccountMovementRecord tgtMv = dsl.selectFrom(ACCOUNT_MOVEMENT)
                .where(ACCOUNT_MOVEMENT.OPERATION_ID.eq(operationId)
                        .and(ACCOUNT_MOVEMENT.ACCOUNT_ID.eq(tgtAcc.getId())))
                .fetchOne();

        assertEquals(0, amt.compareTo(tgtMv.getAmount()));
        assertEquals(0, tgtMv.getDirection().compareTo((byte) 1));



    }
}