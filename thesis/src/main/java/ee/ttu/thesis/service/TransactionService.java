package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
@Transactional
public class TransactionService {
    String csvFile = "C:\\Users\\birgi\\Documents\\Äriinfotehnoloogia\\lõputöö/marksu eng 2019.csv";
    @Autowired
    private TransactionRepository repo;

    public void addTransactionsFromFile() throws IOException {
        List<Transaction> transactionsFromCvs = parseCsvFileToTransactionList();
        for (Transaction transactionFromCvs : transactionsFromCvs) {
            repo.save(transactionFromCvs);
        }
    }

    public List<Transaction> getTransactionsList(){
        return repo.findAll();
    }

    private List<Transaction> parseCsvFileToTransactionList() throws IOException {
        String line = "";
        List<Transaction> transactionList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String ignorefirstLine = br.readLine();

            while ((line = br.readLine()) != null) {
                line = line.replace("\"", "");
                String[] currentLine = line.split(";");

                transactionList.add(Transaction.builder().
                        accountNumber(currentLine[0]).
                        date(LocalDate.parse(currentLine[2], DateTimeFormatter.ofPattern("dd-MM-yyyy"))).
                        beneficiaryOrPayerAccount(currentLine[3]).
                        beneficiaryOrPayerName(currentLine[4]).
                        debitOrCredit(currentLine[7]).
                        amount(new BigDecimal(currentLine[8].replace(",", "."))).
                        details(currentLine[11]).
                        currency(currentLine[13]).build());
            }
        } catch (IOException e) {
            throw new IOException("Cannot parse the file!");
        }
        return transactionList;
    }

    public void updateTransactionIncomeStatementTypes(List<Transaction> transactions) {
       /* for (TransactionDto t : transactions) {
            for (TransactionDto databaseTransaction: transactionList){
                if(t.getId().equals(databaseTransaction.getId())){
                    databaseTransaction.setIncomeStatementType(t.getIncomeStatementType());
                }
            }
        }*/
    }
}
