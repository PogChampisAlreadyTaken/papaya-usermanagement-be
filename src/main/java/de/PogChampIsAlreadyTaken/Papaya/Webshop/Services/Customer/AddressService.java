package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Customer;

import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Customer.Address;
import io.quarkus.security.Authenticated;
import org.jboss.logging.Logger;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Franziska Hesselfeld
 */
//@Authenticated
@Path("/user")
public class AddressService {

    private static final Logger LOG = Logger.getLogger(AddressService.class);

    @GET
    @Path("address/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddressById(@PathParam("id") long id) {
        LOG.info("GET address with id: "+id);

        Address foundAddress = Address.findById(id);

        if(foundAddress == null) {
            LOG.error("Address with id: "+id+" dont exist");
            return Response.status(404).build();
        }else {
            LOG.info("Address was found with id: " + id);
            return Response.ok().entity(foundAddress).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/address")
    public Response postAddress(Address address) {
        LOG.info("POST address: "+address.toString());
        List<Address> addressList = Address.list("street", address.street);

        //filter addresslist to check if address is in the database
        addressList = addressList
                .stream()
                .filter(f -> f.house_number == address.house_number && f.zip == address.zip && f.city.equals(address.city))
                .collect(Collectors.toList());
        Address dummyAddress = address;

        if(addressList.isEmpty()){
            LOG.info("Address "+address.toString()+
                    " dont exist and will be created ");
            address.id = null;
            address.persist();
        }else {
            dummyAddress = addressList.get(0);
        }

        return Response.ok().entity(dummyAddress).build();
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/address")
    public Response deleteAddress(long addressId) {
        LOG.info("DELETE address with id: "+addressId);

        Address foundAddress = Address.findById(addressId);
        if( foundAddress == null){
            LOG.error("Address with id: " + addressId+" was not found");
            return Response.status(404).build();
        }else{
            Address.deleteById(addressId);
            LOG.info("Address with id: " + addressId+" was found and deleted");
            return Response.ok().entity(addressId).build();
        }
    }

}
