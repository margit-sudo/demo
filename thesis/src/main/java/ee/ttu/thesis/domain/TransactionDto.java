package ee.ttu.thesis.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransactionDto {
    private String AccountNumber;
    private String DocumentNumber;
    private LocalDate Date;
    private String BeneficiaryOrPayerAccount;
    private String BeneficiaryOrPayerName;
    private String Details; //selgitus
    private BigDecimal Amount;
    private String Currency;
    private String DebitOrCredit;
}
