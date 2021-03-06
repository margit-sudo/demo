package ee.ttu.thesis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "reportrows")
@AllArgsConstructor
@NoArgsConstructor
public class ReportRow {
    @Id
    @GeneratedValue
    private Long id;
    private IncomeStatementType incomeStatementType;

    @Column(name = "sumOfTransactions")
    private BigDecimal sum;

    @OneToMany
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private List<Transaction> transactions;
}
