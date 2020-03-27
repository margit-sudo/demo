package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.Entry;
import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.Rule;
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
import java.util.*;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository repo;
    private String csvFile = "C:\\Users\\birgi\\Documents\\Äriinfotehnoloogia\\lõputöö/marksu eng 2019.csv";
    @Autowired
    private IncomeStatementTypeService incomeStatementTypeService;

    public void addTransactionsFromFile() throws IOException {
        List<Transaction> transactionsFromCvs = parseTransactionFileToList();
        for (Transaction t : transactionsFromCvs) {
            repo.save(t);
        }
    }

    public List<Transaction> getTransactionsList(){
        return repo.findAll();
    }

    private List<Transaction> parseTransactionFileToList() throws IOException {
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
                        currency(currentLine[13]).
                        incomeStatementType(IncomeStatementType.MÄÄRAMATA).build());
            }
        } catch (IOException e) {
            throw new IOException("Cannot parse the file!");
        }
        return transactionList;
    }

    public void updateTransactionIncomeStatementTypes(List<Transaction> transactions) {
        for (Transaction t : transactions) {
            Optional<Transaction> dbTransaction = repo.findById(t.getId());
            dbTransaction.ifPresent(transaction -> {
                    transaction = Transaction.class.cast(dbTransaction);
                    transaction.setIncomeStatementType(t.getIncomeStatementType());
                    });
        }
    }

    public Optional<Transaction> updateOneTransactionIncomeStatementType(Long id, IncomeStatementType type) {
        repo.findById(id).ifPresent(
                t -> t.setIncomeStatementType(type));
        return repo.findById(id);
    }

    public void updateTransactionIncomeStatementTypesWithRule(Rule rule) {
        List<Transaction> transactions = repo.findAll();
        for (Transaction t : transactions) {
            //details
            if(rule.getTransactionDetails() != null && t.getDetails().contains(rule.getTransactionDetails())) t.setIncomeStatementType(rule.getIncomeStatementType());
            //account
            if(rule.getTransactionBeneficiaryOrPayerAccount() != null && t.getBeneficiaryOrPayerAccount().contains(rule.getTransactionBeneficiaryOrPayerAccount())) t.setIncomeStatementType(rule.getIncomeStatementType());
            //name
            if(rule.getTransactionBeneficiaryOrPayerName() != null && t.getBeneficiaryOrPayerName().contains(rule.getTransactionBeneficiaryOrPayerName())) t.setIncomeStatementType(rule.getIncomeStatementType());
        }
    }

    public HashMap<IncomeStatementType, Entry>  getTransactionsGroupedByIncomeStatementType(){
        HashMap<IncomeStatementType, Entry> groupedList = new HashMap<>();
        List<IncomeStatementType> incomeStatements = incomeStatementTypeService.getIncomeStatementTypes();

        //add incomestatement to hashmap without transactions
        for (IncomeStatementType incomeStatement : incomeStatements) {
            groupedList.put(incomeStatement, new Entry(new ArrayList<>(), BigDecimal.valueOf(0)));
        }

        //then add each transaction according to the statement type aka key
        List<Transaction> transactions = repo.findAll();

        for (Transaction t : transactions) {
                List<Transaction> list = groupedList.get(t.getIncomeStatementType()).getTransactions();
                BigDecimal sum = groupedList.get(t.getIncomeStatementType()).getSum();
                list.add(t);
                sum = sum.add(t.getAmount());
                groupedList.put(t.getIncomeStatementType(), new Entry(list, sum));
        }

        return groupedList;
    }
}
