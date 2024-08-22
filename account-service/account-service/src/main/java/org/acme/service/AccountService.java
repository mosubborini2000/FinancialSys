package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.ibm.JmsProducer;
import org.acme.model.Account;
import org.acme.repository.AccountRepository;

import java.util.List;

@ApplicationScoped
public class AccountService {

    @Inject
    AccountRepository accountRepository;
    @Inject
    JmsProducer jmsProducer;


    public List<Account> getAllAccounts() {
        return accountRepository.listAll();
    }

    @Transactional
    public Account createAccount(Account account) {
        accountRepository.persist(account);
        jmsProducer.sendMessage("Account created: " + account.getAccountNumber());
        return account;
    }

    @Transactional
    public void updateBalance(Long accountId, Double newBalance) {
        Account account = accountRepository.findById(accountId);
        if (account != null) {
            account.setBalance(newBalance);
            jmsProducer.sendMessage("Balance updated for account:"+ account.getAccountNumber());
        }
    }
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.find("accountNumber", accountNumber).firstResult();
    }
}
