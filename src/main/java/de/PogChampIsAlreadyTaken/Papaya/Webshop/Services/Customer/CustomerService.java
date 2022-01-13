package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Customer;

import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Customer.Customer;
import io.quarkus.security.Authenticated;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Franziska Hesselfeld
 */
@Authenticated
@Path("/user")
public class CustomerService {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") String id) {
        Customer foundCustomer = Customer.findById(id);
        if(foundCustomer == null) {
            return Response.status(404).build();
        }
        return Response.ok().entity(foundCustomer).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postCustomer(Customer customer) {
        Customer foundCustomer = Customer.findById(customer.id);
        if(foundCustomer == null){
        //todo: Error Handling
        //todo: check inputs!!
            customer.persist();
        }
        return Response.ok().entity(customer).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteCustomer(String customerId) {
        Customer foundCustomer = Customer.findById(customerId);
        if(foundCustomer == null)
            return Response.status(404).build();

        Customer.deleteById(customerId);

        return Response.ok().entity(customerId).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateCustomer(@PathParam("id") String customerId, Customer customer){
        Customer foundCustomer = Customer.findById(customerId);
        if(foundCustomer == null)
            return Response.status(404).build();

        Customer.getEntityManager().merge(customer);
        return Response.ok().entity(customer).build();
    }
}
