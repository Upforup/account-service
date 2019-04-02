package my.backend.test.solution.integration.test;


import com.google.inject.Guice;
import com.google.inject.Injector;
import my.backend.test.solution.AppModule;
import my.backend.test.solution.WebEntryPoint;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public abstract class BaseTest {

    private static WebEntryPoint instance;

    @BeforeClass
    public static void setUp() {
        Injector injector = Guice.createInjector(new AppModule());
        instance = injector.getInstance(WebEntryPoint.class);
        instance.boot(new String[] {});
    }

    @AfterClass
    public static void tearDown() {
        instance.stop();
    }

}
