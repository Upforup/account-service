package my.backend.test.solution.data;

import my.backend.test.solution.data.generated.tables.records.AccountRecord;
import my.backend.test.solution.domain.Account;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;

import java.math.BigDecimal;
import java.sql.Date;

import static my.backend.test.solution.data.generated.Sequences.ACCOUNT_SEQ;
import static my.backend.test.solution.data.generated.Tables.ACCOUNT;

public class DefaultAccountRepository implements AccountRepository {

    private final DSLContext dsl;

    private final RecordMapper<AccountRecord, Account> mapper = record -> record == null ? null : Account.fetched(
            record.getId(),
            record.getAccountNumber(),
            record.getAccountBalance(),
            record.getDateOpen().toLocalDate());

    public DefaultAccountRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public Account getAccountInfoByAccountNumber(String accNumber) {
        AccountRecord accountRecord = dsl.selectFrom(ACCOUNT).where(ACCOUNT.ACCOUNT_NUMBER.eq(accNumber)).fetchOne();
        return mapper.map(accountRecord);
    }

    @Override
    public long saveNewAccount(Account account) {
        AccountRecord accountRecord = dsl.insertInto(ACCOUNT)
                .set(ACCOUNT.ID, ACCOUNT_SEQ.nextval())
                .set(ACCOUNT.ACCOUNT_NUMBER, account.getNumber())
                .set(ACCOUNT.DATE_OPEN, Date.valueOf(account.getDateOpen()))
                .set(ACCOUNT.ACCOUNT_BALANCE, BigDecimal.ZERO)
                .returning(ACCOUNT.ID)
                .fetchOne();
        return accountRecord.getId();
    }


}
