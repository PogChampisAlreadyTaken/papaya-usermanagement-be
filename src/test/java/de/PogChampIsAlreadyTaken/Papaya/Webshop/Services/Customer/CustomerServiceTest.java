package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Customer;

import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Customer.Customer;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Franziska Hesselfeld
 */
class CustomerServiceTest {

    @Test
    void getCustomerById() {
        Jsonb jsonb = JsonbBuilder.create();


        Customer newCustomer = new Customer();
        newCustomer.id = "d81890cd-8601-49e2-9b46-2c700dbfb899";
        newCustomer.first_name = "Peter";
        newCustomer.last_name = "Parker";
        newCustomer.customer_address_id = 0;

        String encodedString = "hallo1"; //
        //keycloak irgendwie einen token holen

        given()
                .header("Authorization", "Bearer " + encodedString)
                .when()
                .get("/user/d81890cd-8601-49e2-9b46-2c700dbfb899")
                .then()
                .statusCode(200)
                .body(is(jsonb.toJson(newCustomer)));
    }

    @Test
    void postCustomer() {
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void updateCustomer() {
    }
}