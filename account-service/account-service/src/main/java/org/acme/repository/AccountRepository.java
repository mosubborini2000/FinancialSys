package org.acme.repository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Account;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
    @PersistenceContext
    EntityManager em;

    public void updateAccountBalance(Long accountId, Double newBalance) {
        StoredProcedureQuery query = em.createNamedStoredProcedureQuery("updateAccountBalance");
        query.setParameter("p_account_id", accountId);
        query.setParameter("p_new_balance", newBalance);
        query.execute();
    }
}
