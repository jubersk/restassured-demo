package tests;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class ResponseExtractionTest extends BaseTest {

    @Test
    public void testExtractHeaderAndUrl() {
        Response response = given()
                .spec(getRequestSpec())
                .queryParam("course", "restassured")
                .when()
                .get("/get")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String url = jsonPath.getString("url");
        String host = jsonPath.getString("headers.Host");

        assertTrue(url.contains("course=restassured"));
        assertEquals(host, "httpbin.org");
    }
}