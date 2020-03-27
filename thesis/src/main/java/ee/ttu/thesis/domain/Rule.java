package ee.ttu.thesis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Rule {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String transactionBeneficiaryOrPayerAccount;
    private String transactionBeneficiaryOrPayerName;
    private String transactionDetails;
    private IncomeStatementType incomeStatementType;
    private Boolean isAddedByUser;
}
