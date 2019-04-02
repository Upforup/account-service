package my.backend.test.solution.integration.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import my.backend.test.solution.controller.dto.AccountInfoResponse;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetAccountInfoTest extends BaseTest {

    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(7000)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.ANY).build();

    @Test
    public void getAccountInfo_200_when_account_exists() {
        String accNum = "408717001";


        AccountInfoResponse rs = given().spec(requestSpec)
                .when().get(Endpoints.ACCOUNT_INFO + accNum)
                .thenReturn().as(AccountInfoResponse.class);


        assertEquals(0, BigDecimal.valueOf(5000).compareTo(rs.getBalance()));
        assertEquals(accNum, rs.getNumber());
        assertEquals(LocalDate.now(), rs.getDateOpen());
        assertNotNull(rs.getToken());
        assertNotNull(rs.getId());

    }


    @Test
    public void getAccountInfo_404_when_account_not_exists() {
        String accNum = "408717011";


        given().spec(requestSpec)
                .when().get(Endpoints.ACCOUNT_INFO + accNum)
                .then().statusCode(404);


    }
}
