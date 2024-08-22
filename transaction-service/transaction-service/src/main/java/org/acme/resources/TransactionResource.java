package org.acme.resources;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Transaction;
import org.acme.sevice.TransactionService;

@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @Inject
    TransactionService transactionService;

    @POST
    public void createTransaction(Transaction transaction) {
        transactionService.processTransaction(transaction);
    }
}
