package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Customer;

import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Customer.Customer;
import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;

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

    private static final Logger LOG = Logger.getLogger(CustomerService.class);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") String id) {
        LOG.info("GET customer with id: "+id);

        Customer foundCustomer = Customer.findById(id);

        if(foundCustomer == null) {
            LOG.error("Customer with id: "+id+" dont exist");
            return Response.status(404).entity("Customer not found with given Id: "+id).build();
        }

        LOG.info("Return found customer: "+foundCustomer.toString());
        return Response.ok().entity(foundCustomer).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response postCustomer(Customer customer) {
        LOG.info("POST Customer: "+customer.toString());

        Customer foundCustomer = Customer.findById(customer.id);

        if(foundCustomer == null){
            LOG.info("Customer was not found but will be created: "+customer.toString());
            customer.persist();
        } else {
            return Response.status(409).entity("Customer already exist. Id: "+customer.id).build();
        }

        LOG.info("Successful created Customer: "+customer.toString());
        return Response.ok().entity(customer).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteCustomer(String customerId) {
        LOG.info("DELETE customer with id: "+customerId);
        Customer foundCustomer = Customer.findById(customerId);

        if(foundCustomer == null){
            LOG.error("Customer with id: " + customerId+" was not found");
            return Response.status(404).entity("Customer not found with given Id: "+customerId).build();
        }

        Customer.deleteById(customerId);

        LOG.info("Successful deleted Customer: "+customerId);
        return Response.ok().entity(customerId).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateCustomer(@PathParam("id") String customerId, Customer customer){
        LOG.info("PUT customer with id: "+customerId);

        Customer foundCustomer = Customer.findById(customerId);

        if(foundCustomer == null){
            LOG.error("Customer with id: " + customerId+" was not found");
            return Response.status(404).build();
        }

        Customer.getEntityManager().merge(customer);

        LOG.info("Successful update customer: "+customer);
        return Response.ok().entity(customer).build();
    }
}
