package teste;

import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;

public class OauthTest {
    RequestSpecification requestSpecification;

    @Before()
    public void setUp() {
        requestSpecification = new UtilsApi().getRestAssured(false);
    }

    @Test
    public void testaUm() {
        requestSpecification.param("usuario.id", 1);
//                .header("Accept", "application/json")


        assert "a" == "a";

    }


}
