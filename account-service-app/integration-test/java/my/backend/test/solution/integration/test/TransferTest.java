package my.backend.test.solution.integration.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import my.backend.test.solution.controller.dto.AccountInfoResponse;
import my.backend.test.solution.controller.dto.TransferRequest;
import org.junit.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class TransferTest extends BaseTest {

    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(7000)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.ANY).build();


    /**
     * Transfer is two-step operation:
     * 1. fetch source account info with token that will be used for transfer
     * 2. send transfer rq with header If-Match set to token value
     */
    @Test
    public void transfer_when_enough_funds_on_account_200() {
        TransferRequest rq = new TransferRequest();

        String sourceNumber = "408717001";
        String targetNumber = "408717002";
        BigDecimal amount = BigDecimal.valueOf(1000);

        rq.setAmount(amount);
        rq.setSourceNumber(sourceNumber);
        rq.setTargetNumber(targetNumber);

        AccountInfoResponse sourceBeforeTransfer = given().spec(requestSpec)
                .when().get(Endpoints.ACCOUNT_INFO + sourceNumber)
                .thenReturn().as(AccountInfoResponse.class);
        AccountInfoResponse targetBeforeTransfer = given().spec(requestSpec)
                .when().get(Endpoints.ACCOUNT_INFO + targetNumber)
                .thenReturn().as(AccountInfoResponse.class);


        given().spec(requestSpec).body(rq)
                .contentType(ContentType.JSON)
                .header("If-Match", sourceBeforeTransfer.getToken())
                .when().put(Endpoints.TRANSFER)
                .then().statusCode(200);

        AccountInfoResponse sourceAfterTransfer = given().spec(requestSpec)
                .when().get(Endpoints.ACCOUNT_INFO + sourceNumber)
                .thenReturn().as(AccountInfoResponse.class);

        AccountInfoResponse targetAfterTransfer = given().spec(requestSpec)
                .when().get(Endpoints.ACCOUNT_INFO + targetNumber)
                .thenReturn().as(AccountInfoResponse.class);

        assertTrue(sourceBeforeTransfer.getBalance().compareTo(sourceAfterTransfer.getBalance()) > 0);
        assertTrue(targetBeforeTransfer.getBalance().compareTo(targetAfterTransfer.getBalance()) < 0);

    }

    @Test
    public void transfer_without_token_400() {
        TransferRequest rq = new TransferRequest();

        String sourceNumber = "408717001";
        String targetNumber = "408717002";
        BigDecimal amount = BigDecimal.valueOf(1000);

        rq.setAmount(amount);
        rq.setSourceNumber(sourceNumber);
        rq.setTargetNumber(targetNumber);



        given().spec(requestSpec).body(rq)
                .contentType(ContentType.JSON)
                .when().put(Endpoints.TRANSFER)
                .then().statusCode(400);

    }

    @Test
    public void transfer_with_invalid_token_400() {
        TransferRequest rq = new TransferRequest();

        String sourceNumber = "408717001";
        String targetNumber = "408717002";
        BigDecimal amount = BigDecimal.valueOf(1000);

        rq.setAmount(amount);
        rq.setSourceNumber(sourceNumber);
        rq.setTargetNumber(targetNumber);

        given().spec(requestSpec).body(rq)
                .contentType(ContentType.JSON).header("If-Match", "this-is-invalid-header")
                .when().put(Endpoints.TRANSFER)
                .then().statusCode(400);

    }


    @Test
    public void transfer_when_not_enough_funds_on_account_500() {
        TransferRequest rq = new TransferRequest();

        String sourceNumber = "408717001";
        String targetNumber = "408717002";
        BigDecimal amount = BigDecimal.valueOf(10000);

        rq.setAmount(amount);
        rq.setSourceNumber(sourceNumber);
        rq.setTargetNumber(targetNumber);

        AccountInfoResponse sourceBeforeTransfer = given().spec(requestSpec)
                .when().get(Endpoints.ACCOUNT_INFO + sourceNumber)
                .thenReturn().as(AccountInfoResponse.class);

        given().spec(requestSpec).body(rq)
                .contentType(ContentType.JSON).header("If-Match", sourceBeforeTransfer.getToken())
                .when().put(Endpoints.TRANSFER)
                .then().statusCode(500);

    }

    @Test
    public void transfer_when_target_not_found_500() {
        TransferRequest rq = new TransferRequest();

        String sourceNumber = "408717001";
        String targetNumber = "4087170022";
        BigDecimal amount = BigDecimal.valueOf(1000);

        rq.setAmount(amount);
        rq.setSourceNumber(sourceNumber);
        rq.setTargetNumber(targetNumber);

        AccountInfoResponse sourceBeforeTransfer = given().spec(requestSpec)
                .when().get(Endpoints.ACCOUNT_INFO + sourceNumber)
                .thenReturn().as(AccountInfoResponse.class);
        given().spec(requestSpec).body(rq)
                .contentType(ContentType.JSON).header("If-Match", sourceBeforeTransfer.getToken())
                .when().put(Endpoints.TRANSFER)
                .then().statusCode(500);

    }
}
