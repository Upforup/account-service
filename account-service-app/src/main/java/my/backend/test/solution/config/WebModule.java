package my.backend.test.solution.config;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.javalin.Javalin;
import my.backend.test.solution.WebEntryPoint;
import my.backend.test.solution.controller.AccountController;
import my.backend.test.solution.controller.TokenProvider;
import my.backend.test.solution.service.AccountOperations;
import my.backend.test.solution.setup.SetupH2Database;
import org.jooq.ConnectionProvider;

public class WebModule extends AbstractModule {


    private Javalin app;

    private WebModule(Javalin app) {
        this.app = app;
    }

    public static WebModule create() {
        return new WebModule(Javalin.create());
    }

    @Provides @Singleton
    public HashFunction sha256() {
        return Hashing.sha256();
    }

    @Provides @Singleton
    public TokenProvider tokenProvider(HashFunction hashFunction) {
        return new TokenProvider(hashFunction);
    }

    @Provides @Singleton
    public AccountController accountController(AccountOperations operations, TokenProvider tokenProvider) {
        return new AccountController(operations, tokenProvider);
    }

    @Provides @Singleton
    public SetupH2Database setupH2Database(ConnectionProvider connectionProvider) {
        return new SetupH2Database(connectionProvider);
    }

    @Provides @Singleton
    public WebEntryPoint webEntryPoint(Javalin app, AccountController controller, SetupH2Database setupH2Database) {
        return new WebEntryPoint(app, controller, setupH2Database);
    }

    @Override
    protected void configure() {
        bind(Javalin.class).toInstance(app);
    }
}
