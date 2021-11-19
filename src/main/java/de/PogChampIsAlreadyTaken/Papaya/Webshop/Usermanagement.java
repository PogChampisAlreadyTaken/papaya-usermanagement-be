package de.PogChampIsAlreadyTaken.Papaya.Webshop;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class Usermanagement {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello() {
        return "Hello Usermanagment, uwu";
    }
}