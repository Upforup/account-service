package my.backend.test.solution;

import io.javalin.Context;
import io.javalin.Javalin;
import io.javalin.JavalinEvent;
import my.backend.test.solution.controller.AccountController;
import my.backend.test.solution.controller.dto.TransferRequest;
import my.backend.test.solution.setup.SetupH2Database;

import static io.javalin.apibuilder.ApiBuilder.*;

/**
 * This class defines startup logic of this app, it also contains route definitions
 */
public class WebEntryPoint {
    private final Javalin app;
    private final AccountController controller;
    private final SetupH2Database setup;

    public WebEntryPoint(Javalin app, AccountController controller, SetupH2Database setup) {
        this.app = app;
        this.controller = controller;
        this.setup = setup;
    }

    public void boot(String[] args) {
        bindRoutes();
        app.event(JavalinEvent.SERVER_STARTING, setup::setUp);
        app.port(7000);
        app.start();
    }

    public void stop() {
        app.stop();
    }

    private void bindRoutes() {
        app.routes(() -> {
            path("/", () -> {
                get(ctx -> ctx.json(app.getHandlerMetaInfo()));
                path("api/account/", () -> {
                    get(":account-number", ctx -> controller.accountInfo(ctx, ctx.pathParam("account-number")));
                });
                path("api/transfer/", () -> {
                    put((Context ctx) -> {
                        TransferRequest request = ctx.bodyAsClass(TransferRequest.class);
                        controller.transfer(ctx, request.getSourceNumber(), request.getTargetNumber(), request.getAmount());
                    });
                });
            });

        });
    }
}