package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Admin;

import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Admin.Timemanagement.Delivertime;
import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Franziska Hesselfeld
 */
@Path("/timemanagement")
@Authenticated
public class TimeManagementService {

    private static final Logger LOG = Logger.getLogger(TimeManagementService.class);

    @GET
    @Path("/delivertime")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCurrentDeliverTime() {
        LOG.info("GET current deliver time");

        Delivertime delivertime = Delivertime.findAll().firstResult();

        if(delivertime == null) {
            LOG.error("Delivertime entry dont exist in database");
            return Response.status(404).build();
        }

        LOG.info("Return current deliver time: "+delivertime.time_in_minutes+" with id: "+delivertime.id);
        return Response.ok().entity(delivertime.time_in_minutes).build();
    }


    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/delivertime")
    public Response putDeliverTime(Delivertime delivertime) {
        LOG.info("PUT delivertime with value:"+delivertime);
        Delivertime foundDelivertime = Delivertime.findAll().firstResult();

        if(foundDelivertime == null){
            LOG.error("Delivertime entry dont exist in database");
            return Response.status(404).build();
        }

        Delivertime.getEntityManager().merge(delivertime);

        LOG.info("Succesful update of delivertime");
        return Response.ok().build();
    }
}
