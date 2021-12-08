package de.PogChampIsAlreadyTaken.Papaya.Webshop;


import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class CustomerService {
    @Inject
    EntityManager em;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") Long id) {
        Customer cus = em.find(Customer.class, id);
        if(cus == null) {
            return Response.status(404).build();
        }
        return Response.ok().entity(cus).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCustomer(Customer customer) {
        Customer cus = createCustomer(customer.getFirstName(), customer.getLastName());
        System.out.println(cus);
        return Response.ok().entity(cus).build();
    }

    @Transactional
    public Customer createCustomer(String firstName, String lastName) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        em.persist(customer);
        return customer;
    }
}
