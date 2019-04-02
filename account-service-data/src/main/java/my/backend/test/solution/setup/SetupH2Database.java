package my.backend.test.solution.setup;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import my.backend.test.solution.data.AccountRepository;
import org.jooq.ConnectionProvider;

import java.sql.Connection;

/**
 * This class is used to create H2 database on startup of this app
 */
@Slf4j
public class SetupH2Database {

    private final ConnectionProvider connectionProvider;

    public SetupH2Database(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public void setUp() {
        Connection connection = connectionProvider.acquire();
        try {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new liquibase.Liquibase("changelog/db.changelog-master.yaml", new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Exception e) {
            log.error("Exception occurred while updating database: ", e);
        } finally {
            connectionProvider.release(connection);
        }
    }
}
