package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.Entry;
import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.Rule;
import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repo;
    private final EnumService enumService;

    public List<Transaction> saveAll(List<Transaction> list){
       return repo.saveAll(list);
    }

    public List<Transaction> getTransactionsList(){
        return repo.findAll();
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
        List<IncomeStatementType> incomeStatements = enumService.getIncomeStatementTypes();

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

    public void deleteTransaction(Long id) {
        repo.deleteById(id);
    }
}
