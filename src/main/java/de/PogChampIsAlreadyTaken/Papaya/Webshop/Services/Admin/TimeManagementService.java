package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Admin;

import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Admin.Timemanagement.Delivertime;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/timemanagement")
public class TimeManagementService {

    @GET
    @Path("/delivertime")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById() {
        Delivertime delivertime = Delivertime.findAll().firstResult();
        if(delivertime == null) {
            return Response.status(404).build();
        }
        return Response.ok().entity(delivertime).build();
    }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/delivertime")
    public Response postAddress(Delivertime delivertime) {
        Delivertime foundDelivertime = Delivertime.findAll().firstResult();
        if(foundDelivertime == null){
            Delivertime.persist((delivertime));
            return Response.ok().build();
        }else{
            Delivertime.getEntityManager().merge(delivertime);
            return Response.ok().entity(foundDelivertime).build();
        }
    }
}
