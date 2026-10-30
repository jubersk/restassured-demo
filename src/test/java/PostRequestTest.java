package tests;

import base.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostRequestTest extends BaseTest {

    @Test
    public void testPostWithMapBody() {
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "Jane");
        payload.put("job", "SDET");

        given()
                .spec(getRequestSpec())
                .body(payload)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                .body("json.name", equalTo("Jane"))
                .body("json.job", equalTo("SDET"))
                .body("headers.Content-Type", containsString("application/json"));
    }
}