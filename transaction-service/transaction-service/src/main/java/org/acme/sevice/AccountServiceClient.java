package org.acme.sevice;

import org.acme.dto.AccountDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "http://localhost:8081/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AccountServiceClient {

    @GET
    @Path("/account/{accountNumber}")
    AccountDTO getAccountByAccountNumber(@PathParam("accountNumber") String accountNumber);

    @PUT
    @Path("/{id}/balance")
    void updateBalance(@PathParam("id") Long accountId, AccountDTO account);
}
