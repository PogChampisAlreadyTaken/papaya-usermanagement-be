package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Admin;

import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Admin.Timemanagement.Delivertime;
import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Customer.Customer;
import org.junit.jupiter.api.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Franziska Hesselfeld
 */
class TimeManagementServiceTest {

    @Test
    void putDeliverTime() {
        Jsonb jsonb = JsonbBuilder.create();

        Delivertime deliverTime = new Delivertime();
        deliverTime.id = 1;
        deliverTime.time_in_minutes = 10;



        given()
                .contentType("application/json")
                .body(deliverTime)
                .when().put("/timemanagement/delivertime/")
                .then()
                .statusCode(200)
                .body(is(10));
    }
}