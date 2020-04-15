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
    private String accountNumber;
    private LocalDate date;
    private String beneficiaryOrPayerAccount;
    private String beneficiaryOrPayerName;
    private String details;
    private BigDecimal amount;
    private String currency;
    private String debitOrCredit;

    @Enumerated(EnumType.STRING)
    private IncomeStatementType incomeStatementType;

    @ManyToOne()
    @JoinColumn(name = "report_row_id", referencedColumnName="id")
    private ReportRow row;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
