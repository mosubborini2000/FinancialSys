package org.acme.Resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.model.Account;
import org.acme.service.AccountService;

import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    AccountService accountService;

    @GET
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @POST
    public Account createAccount(Account account) {
        return accountService.createAccount(account);
    }

    @PUT
    @Path("/{id}/balance")
    public void updateBalance(@PathParam("id") Long accountId, Account account) {
        accountService.updateBalance(accountId, account.getBalance());
    }
    @GET
    @Path("/account/{accountNumber}")
    public Account getAccountByAccountNumber(@PathParam("accountNumber") String accountNumber) {
        return accountService.findByAccountNumber(accountNumber);
    }
}
