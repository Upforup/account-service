package my.backend.test.solution.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultDSLContext;

import javax.sql.DataSource;

public class DbModule extends AbstractModule {

    @Provides @Singleton
    public DataSource dataSource() {
        return HikariDsFactory.getDataSource();
    }

    @Provides @Singleton
    public ConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider(dataSource);
    }

    @Provides @Singleton
    public Configuration configuration(ConnectionProvider connectionProvider) {
        return DslConfigFactory.getConfig(connectionProvider);
    }

    @Provides @Singleton
    public DSLContext configuration(Configuration configuration) {
        return new DefaultDSLContext(configuration);
    }

}
