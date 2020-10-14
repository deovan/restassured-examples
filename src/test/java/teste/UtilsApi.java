package teste;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UtilsApi {

    public RequestSpecification getRestAssured(boolean generateToken) {

        if (generateToken) {
            return given()
                    .header("Authorization", "bearer " + getTokenUser("user", "pass", "url"));
        } else {
            return RestAssured.given();
        }

    }

    private String getTokenUser(String user, String pass, String url) {
        try {
            Response client = given()
                    .contentType(ContentType.URLENC.withCharset("UTF-8"))
                    .formParam("grant_type", "password")
                    .formParam("client_id", "frontend-developers")
                    .formParam("username", user)
                    .formParam("password", pass)
                    .post(url)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

            return new JsonPath(client.body().asString()).get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
