package org.acme.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NamedStoredProcedureQuery(
        name = "updateAccountBalance",
        procedureName = "update_account_balance",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_account_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_new_balance", type = Double.class)
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String ownerName;
    private Double balance;
}
