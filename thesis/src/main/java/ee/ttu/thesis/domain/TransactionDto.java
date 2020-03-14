package ee.ttu.thesis.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransactionDto {
    private String AccountNumber;
    private LocalDate Date;
    private String BeneficiaryOrPayerAccount;
    private String BeneficiaryOrPayerName;
    private String Details;
    private BigDecimal Amount;
    private String Currency;
    private String DebitOrCredit;
    private IncomeStatementType IncomeStatementType;
}
