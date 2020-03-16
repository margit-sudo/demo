package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {
    String csvFile = "C:\\Users\\birgi\\Documents\\Äriinfotehnoloogia\\lõputöö/marksu eng 2019.csv";
    List<TransactionDto> transactionList = new ArrayList<>();

    public TransactionDto getTransactionDto(String data) {
        return null;
        //return TransactionDto.builder().data("Hello world! My data is " + data).build();
    }

    public List<TransactionDto> parseCsvFileToTransaction() throws IOException {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String ignorefirstLine = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                String[] currentLine = line.split(";");

                transactionList.add(TransactionDto.builder().
                        Id(UUID.randomUUID().toString()).
                        AccountNumber(currentLine[0]).
                        Date(LocalDate.parse(currentLine[2], DateTimeFormatter.ofPattern("dd-MM-yyyy"))).
                        BeneficiaryOrPayerAccount(currentLine[3]).
                        BeneficiaryOrPayerName(currentLine[4]).
                        DebitOrCredit(currentLine[7]).
                        Amount(new BigDecimal(currentLine[8].replace(",", "."))).
                        Details(currentLine[11]).
                        Currency(currentLine[13]).build());
            }
        } catch (IOException e) {
            throw new IOException("Cannot parse the file!");
        }
        return transactionList;
    }

    public void updateTransactionIncomeStatementTypes(List<TransactionDto> transactions) {
       /* for (TransactionDto t : transactions) {
            for (TransactionDto databaseTransaction: transactionList){
                if(t.getId().equals(databaseTransaction.getId())){
                    databaseTransaction.setIncomeStatementType(t.getIncomeStatementType());
                }
            }
        }*/
    }
}
