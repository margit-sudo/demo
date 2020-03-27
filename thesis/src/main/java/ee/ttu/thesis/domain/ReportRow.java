package ee.ttu.thesis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "reportRow")
@AllArgsConstructor
@NoArgsConstructor
    public class ReportRow {
    @Id
    @GeneratedValue
        private Long id;
        private IncomeStatementType incomeStatementType;
        private BigDecimal sum;

    @OneToMany
    @JoinTable(name="reportRow",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "transaction_id") })
        private List<Transaction> transactions;
}
