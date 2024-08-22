package org.acme.sevice;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.AccountDTO;
import org.acme.model.Transaction;
import org.acme.repository.TransactionRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class TransactionService {

    @Inject
    TransactionRepository transactionRepository;

    @Inject
    @RestClient
    AccountServiceClient accountServiceClient;

    @Transactional
    public Transaction processTransaction(Transaction transaction) {
        AccountDTO fromAccount = accountServiceClient.getAccountByAccountNumber(transaction.getFromAccount());

        Double newFromBalance = fromAccount.getBalance() - transaction.getAmount();

        accountServiceClient.updateBalance(fromAccount.getId(),
                new AccountDTO(fromAccount.getId(), fromAccount.getAccountNumber(), fromAccount.getOwnerName(), newFromBalance));

        AccountDTO toAccount = accountServiceClient.getAccountByAccountNumber(transaction.getToAccount());

        Double newToBalance = toAccount.getBalance() + transaction.getAmount();

        accountServiceClient.updateBalance(toAccount.getId(),
                new AccountDTO(toAccount.getId(), toAccount.getAccountNumber(), toAccount.getOwnerName(), newToBalance));

        transactionRepository.persist(transaction);
        return transaction;
    }

}
