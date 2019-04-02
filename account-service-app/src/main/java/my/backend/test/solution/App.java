package my.backend.test.solution;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

@Singleton
public class App {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        injector.getInstance(WebEntryPoint.class).boot(args);
    }
}
