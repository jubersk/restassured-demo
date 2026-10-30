import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthenticationTest extends BaseTest {

    @Test
    public void testBasicAuthSuccess() {
        given()
                .spec(getRequestSpec())
                .auth().basic("user", "pass")
                .when()
                .get("/basic-auth/user/pass")
                .then()
                .statusCode(200)
                .body("authenticated", equalTo(true));
    }

    @Test
    public void testBasicAuthFail() {
        given()
                .spec(getRequestSpec())
                .auth().basic("wrong", "wrong")
                .when()
                .get("/basic-auth/user/pass")
                .then()
                .statusCode(401);
    }
}