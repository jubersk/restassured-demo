package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetRequestTest extends BaseTest {

    @Test
    public void testGetRequest() {
        given()
                .spec(getRequestSpec())
                .when()
                .get("/get")
                .then()
                .statusCode(200)
                .body("url", equalTo("https://httpbin.org/get"))
                .body("headers.Host", equalTo("httpbin.org"));
    }

    @Test
    public void testGetWithQueryParam() {
        given()
                .spec(getRequestSpec())
                .queryParam("name", "restassured")
                .when()
                .get("/get")
                .then()
                .statusCode(200)
                .body("args.name", equalTo("restassured"));
    }
}