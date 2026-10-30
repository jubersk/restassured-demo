import base.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PutPatchDeleteTest extends BaseTest {

    @Test
    public void testPut() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "Updated Name");

        given()
                .spec(getRequestSpec())
                .body(body)
                .when()
                .put("/put")
                .then()
                .statusCode(200)
                .body("json.name", equalTo("Updated Name"));
    }

    @Test
    public void testPatch() {
        Map<String, String> body = new HashMap<>();
        body.put("job", "Senior QA");

        given()
                .spec(getRequestSpec())
                .body(body)
                .when()
                .patch("/patch")
                .then()
                .statusCode(200)
                .body("json.job", equalTo("Senior QA"));
    }

    @Test
    public void testDelete() {
        given()
                .spec(getRequestSpec())
                .when()
                .delete("/delete")
                .then()
                .statusCode(200);
    }
}