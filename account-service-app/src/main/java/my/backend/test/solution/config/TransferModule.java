package my.backend.test.solution.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import my.backend.test.solution.data.AccountRepository;
import my.backend.test.solution.data.DefaultAccountRepository;
import my.backend.test.solution.data.DefaultOperationRepository;
import my.backend.test.solution.data.OperationRepository;
import my.backend.test.solution.service.AccountOperations;
import my.backend.test.solution.service.SimpleAccountOperations;
import my.backend.test.solution.usecase.DefaultTransfer;
import my.backend.test.solution.usecase.GetAccountInfo;
import my.backend.test.solution.usecase.Transfer;
import org.jooq.DSLContext;


public class TransferModule extends AbstractModule {


    @Provides @Singleton
    public AccountRepository accountRepository(DSLContext dsl) {
        return new DefaultAccountRepository(dsl);
    }

    @Provides @Singleton
    public OperationRepository operationRepository(DSLContext dsl) {
        return new DefaultOperationRepository(dsl);
    }

    @Provides @Singleton
    public GetAccountInfo getAccountInfo(AccountRepository accountRepository) {
        return new GetAccountInfo(accountRepository);
    }

    @Provides @Singleton
    public Transfer transfer(OperationRepository operationRepository) {
        return new DefaultTransfer(operationRepository);
    }

    @Provides @Singleton
    public AccountOperations accountOperations(GetAccountInfo getAccountInfo, Transfer transfer) {
        return new SimpleAccountOperations(getAccountInfo, transfer);
    }

}
