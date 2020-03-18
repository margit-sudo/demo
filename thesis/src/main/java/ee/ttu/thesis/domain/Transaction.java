package ee.ttu.thesis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="accountNumber")
    private String accountNumber;

    @Column(name="date")
    private LocalDate date;

    @Column(name = "beneficiaryOrPayerAccount")
    private String beneficiaryOrPayerAccount;

    @Column(name = "beneficiaryOrPayerName")
    private String beneficiaryOrPayerName;

    @Column(name = "details")
    private String details;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "debitOrCredit")
    private String debitOrCredit;

    @Column(name = "incomeStatementType")
    @Enumerated(EnumType.STRING)
    private IncomeStatementType incomeStatementType;
}
