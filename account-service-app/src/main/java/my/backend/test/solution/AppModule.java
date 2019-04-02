package my.backend.test.solution;

import com.google.inject.AbstractModule;
import my.backend.test.solution.config.DbModule;
import my.backend.test.solution.config.TransferModule;
import my.backend.test.solution.config.WebModule;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new DbModule());
        install(new TransferModule());
        install(WebModule.create());
    }
}
