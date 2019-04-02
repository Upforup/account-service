package my.backend.test.solution.config;

import org.jooq.ConnectionProvider;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.ThreadLocalTransactionProvider;

public class DslConfigFactory {


    public static DefaultConfiguration getConfig(ConnectionProvider connectionProvider) {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(connectionProvider);
        configuration.set(SQLDialect.H2);
        configuration.set(new ThreadLocalTransactionProvider(connectionProvider));
        return configuration;
    }
}


