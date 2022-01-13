package de.PogChampIsAlreadyTaken.Papaya.Webshop;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class UsermanagementTest {

    @Test
    public void testHelloEndpoint() {
        Jsonb jsonb = JsonbBuilder.create();


        given()
                .when().get("/hello")
                .then()
                .statusCode(200)
                .body(is(jsonb.toJson("Hello Usermanagement, OwO")));
    }


}