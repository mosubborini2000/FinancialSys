package org.acme.account;

import org.acme.model.Account;
import org.acme.repository.AccountRepository;
import org.acme.ibm.JmsProducer;
import org.acme.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    JmsProducer jmsProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAccounts() {
        List<Account> accounts = List.of(new Account(1L, "123456789", "mosuab rami", 1000.0));
        when(accountRepository.listAll()).thenReturn(accounts);

        List<Account> result = accountService.getAllAccounts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("mosuab rami", result.get(0).getOwnerName());
        verify(accountRepository, times(1)).listAll();
    }

    @Test
    void testCreateAccount() {
        Account account = new Account(1L, "123456789", "mosuab rami", 1000.0);
        doNothing().when(accountRepository).persist(account);
        doNothing().when(jmsProducer).sendMessage(anyString());

        Account result = accountService.createAccount(account);

        assertNotNull(result);
        verify(accountRepository, times(1)).persist(account);
        verify(jmsProducer, times(1)).sendMessage("Account created: " + account.getAccountNumber());
    }


    @Test
    void testUpdateBalance() {
        Account account = new Account(1L, "123456789", "mosuab rami", 1000.0);
        when(accountRepository.findById(1L)).thenReturn(account);
        doNothing().when(jmsProducer).sendMessage(anyString());

        accountService.updateBalance(1L, 2000.0);

        verify(accountRepository, times(1)).findById(1L);
        assertEquals(2000.0, account.getBalance());
        verify(jmsProducer, times(1)).sendMessage("Balance updated for account:" + account.getAccountNumber());
    }
}
