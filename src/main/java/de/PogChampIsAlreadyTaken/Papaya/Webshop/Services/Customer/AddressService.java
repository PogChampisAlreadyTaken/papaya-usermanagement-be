package de.PogChampIsAlreadyTaken.Papaya.Webshop.Services.Customer;

import de.PogChampIsAlreadyTaken.Papaya.Webshop.Baseclasses.Customer.Address;
import io.quarkus.security.Authenticated;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Authenticated
@Path("/user")
public class AddressService {

    @GET
    @Path("address/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomerById(@PathParam("id") long id) {
        Address foundAddress = Address.findById(id);
        if(foundAddress == null) {
            return Response.status(404).build();
        }
        return Response.ok().entity(foundAddress).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/address")
    public Response postAddress(Address address) {
        List<Address> addressList = Address.list("street", address.street);

        addressList = addressList
                .stream()
                .filter(f -> f.house_number == address.house_number && f.zip == address.zip && f.city.equals(address.city))
                .collect(Collectors.toList());
        Address dummyAddress = address;

        if(addressList.isEmpty()){
            address.persist();
        }else {
            dummyAddress = addressList.get(0);
        }

        return Response.ok().entity(dummyAddress.id).build();
    }


    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/address")
    public Response deleteAddress(long addressid) {
        //todo: Error Handling
        //todo: check inputs
        Address.deleteById(addressid);
        return Response.ok().entity(addressid).build();
    }

}
