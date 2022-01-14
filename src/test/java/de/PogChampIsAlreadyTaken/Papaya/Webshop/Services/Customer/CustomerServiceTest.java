package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Customer;

import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Customer.Customer;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Franziska Hesselfeld, Moritz Lintterer
 */
@Transactional
@QuarkusTest
@TestTransaction
class CustomerServiceTest {
    Jsonb jsonb = JsonbBuilder.create();

    Customer testCustomerNotInDatabase = new Customer();
    Customer testCustomerAlreadyInDatabase = new Customer();
    Customer testCustomerToChange = new Customer();


    @BeforeEach
    void createUser(){
        testCustomerNotInDatabase.id = "0";
        testCustomerNotInDatabase.first_name = "Testi";
        testCustomerNotInDatabase.last_name = "Test";
        testCustomerNotInDatabase.customer_address_id = 0;

        testCustomerAlreadyInDatabase.id ="d81890cd-8601-49e2-9b46-2c700dbfb899";
        testCustomerAlreadyInDatabase.first_name ="Peter";
        testCustomerAlreadyInDatabase.last_name="Parker";
        testCustomerAlreadyInDatabase.customer_address_id=0;

        testCustomerToChange.id ="d81890cd-8601-49e2-9b46-2c700dbfb899";
        testCustomerToChange.first_name ="Peter";
        testCustomerToChange.last_name="ParkerChanged";
        testCustomerToChange.customer_address_id=0;
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testPostCustomerAndDeleteSuccessful() {
        given()
                .contentType("application/json")
                .body(jsonb.toJson(testCustomerNotInDatabase))
                .when()
                .post("/user")
                .then()
                .statusCode(200)
                .body(is(jsonb.toJson(testCustomerNotInDatabase)));

        given()
                .contentType("application/json")
                .body("0")
                .when()
                .delete("/user")
                .then()
                .statusCode(200)
                .body(is("0"));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    void testPostDuplicateCustomerFailed() {
        //duplicate post request
        given()
                .contentType("application/json")
                .body(jsonb.toJson(testCustomerAlreadyInDatabase))
                .when()
                .post("/user")
                .then()
                .statusCode(409)
                .body(is("Customer already exist. Id: d81890cd-8601-49e2-9b46-2c700dbfb899"));
    }


    @Test
    @TestSecurity(authorizationEnabled = false)
    void getCustomerById() {
        given()
                .when()
                .get("/user/d81890cd-8601-49e2-9b46-2c700dbfb899")
                .then()
                .statusCode(200)
                .body(is(jsonb.toJson(testCustomerAlreadyInDatabase)));
    }


    @Test
    @TestSecurity(authorizationEnabled = false)
    void deleteCustomerFailed() {
        given()
                .contentType("application/json")
                .body("1")
                .when()
                .delete("/user")
                .then()
                .statusCode(404)
                .body(is("Customer not found with given Id: 1"));
    }

    @Test
    @TestSecurity(authorizationEnabled = false)
    @TestTransaction
    void updateCustomer() {
        //first change it
        given()
                .contentType("application/json")
                .body(jsonb.toJson(testCustomerToChange))
                .when()
                .put("/user/d81890cd-8601-49e2-9b46-2c700dbfb899")
                .then()
                .statusCode(200)
                .body(is(jsonb.toJson(testCustomerToChange)));

       given()
                .contentType("application/json")
                .body(jsonb.toJson(testCustomerAlreadyInDatabase))
                .when()
                .put("/user/d81890cd-8601-49e2-9b46-2c700dbfb899")
                .then()
                .statusCode(200)
                .body(is(jsonb.toJson(testCustomerAlreadyInDatabase)));
    }
}