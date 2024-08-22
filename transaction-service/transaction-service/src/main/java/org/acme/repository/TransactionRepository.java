package org.acme.repository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Transaction;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {

}
