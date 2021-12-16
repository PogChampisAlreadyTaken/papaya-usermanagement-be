package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Customer;


import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Address;
import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Customer;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class CustomerService {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") Long id) {
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
        //todo: Error Handling
        //todo: check inputs!!
        customer.persist();
        return Response.ok().entity(customer).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteCustomer(String customerId) {
        //first check, if customer exists
        //todo: check inputs!!!
        Customer foundCustomer = Customer.findById(customerId);
        if(foundCustomer == null)
            return Response.status(404).build();

        Customer.deleteById(customerId);

        return Response.ok().entity(customerId).build();
    }
}
