package ee.ttu.thesis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rules")
public class Rule {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LocalDate dateMade;
    private String transactionBeneficiaryOrPayerAccount;
    private String transactionBeneficiaryOrPayerName;
    private String transactionDetails;
    private IncomeStatementType incomeStatementType;
    private Boolean isAddedByUser;
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
