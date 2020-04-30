package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.*;
import ee.ttu.thesis.repository.TransactionRepository;
import ee.ttu.thesis.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private final RuleService ruleService;
    private final UploadRepository uploadRepo;

    public void saveAll(List<Transaction> list) {
         repo.saveAll(list);
    }

    public List<Transaction> getTransactionsByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    public Optional<Transaction> updateOneTransactionIncomeStatementType(Long id, IncomeStatementType type) {
        repo.findById(id).ifPresent(
                t -> t.setIncomeStatementType(type));
        return repo.findById(id);
    }

    public void updateTransactionIncomeStatementTypesWithRule(Rule rule, User u) {
        List<Transaction> transactions = repo.findByUserId(u.getId());
        if (rule.getType().equals("Contains")) {
            updateTransactionsWithContainsRule(transactions, rule);
        } else if (rule.getType().equals("Begins")) {
            updateTransactionsWithBeginsRule(transactions, rule);
        } else if (rule.getType().equals("Ends")) {
            updateTransactionsWithEndsRule(transactions, rule);
        }
    }

    public List<Transaction> updateTransactionIncomeStatementTypesWithList(User u, List<Transaction> transactions) {
        List<Rule> rules = ruleService.getRuleListByUser(u.getId());
        for (Rule rule : rules) {
            if(rule.getType() != null) {
                if (rule.getType().equals("Contains")) {
                    updateTransactionsWithContainsRule(transactions, rule);
                } else if (rule.getType().equals("Begins")) {
                    updateTransactionsWithBeginsRule(transactions, rule);
                } else if (rule.getType().equals("Ends")) {
                    updateTransactionsWithEndsRule(transactions, rule);
                }
            }
        }
        return transactions;
    }

    private void updateTransactionsWithContainsRule(List<Transaction> transactions, Rule rule) {
        for (Transaction t : transactions) {
            if (!rule.getTransactionDetails().equals("") &&
                    rule.getTransactionDetails() != null && t.getDetails() != null &&
                    t.getDetails().contains(rule.getTransactionDetails())) {
                t.setIncomeStatementType(rule.getIncomeStatementType());
            }
            if (!rule.getTransactionBeneficiaryOrPayerAccount().equals("") &&
                    rule.getTransactionBeneficiaryOrPayerAccount() != null && t.getBeneficiaryOrPayerAccount() != null &&
                    t.getBeneficiaryOrPayerAccount().contains(rule.getTransactionBeneficiaryOrPayerAccount())) {
                t.setIncomeStatementType(rule.getIncomeStatementType());
            }
            if (!rule.getTransactionBeneficiaryOrPayerName().equals("") &&
                    rule.getTransactionBeneficiaryOrPayerName() != null && t.getBeneficiaryOrPayerName() != null &&
                    t.getBeneficiaryOrPayerName().contains(rule.getTransactionBeneficiaryOrPayerName())) {
                t.setIncomeStatementType(rule.getIncomeStatementType());
            }
        }
    }

    private void updateTransactionsWithBeginsRule(List<Transaction> transactions, Rule rule) {
        for (Transaction t : transactions) {
            if (rule.getTransactionDetails() != null && t.getDetails() != null &&
                    t.getDetails().startsWith(rule.getTransactionDetails())) {
                t.setIncomeStatementType(rule.getIncomeStatementType());
            }
            if (rule.getTransactionBeneficiaryOrPayerAccount() != null && t.getBeneficiaryOrPayerAccount() != null &&
                    t.getBeneficiaryOrPayerAccount().startsWith(rule.getTransactionBeneficiaryOrPayerAccount())) {
                t.setIncomeStatementType(rule.getIncomeStatementType());
            }
            if (rule.getTransactionBeneficiaryOrPayerName() != null && t.getBeneficiaryOrPayerName() != null &&
                    t.getBeneficiaryOrPayerName().startsWith(rule.getTransactionBeneficiaryOrPayerName())) {
                t.setIncomeStatementType(rule.getIncomeStatementType());
            }
        }
    }

    private void updateTransactionsWithEndsRule(List<Transaction> transactions, Rule rule) {
        for (Transaction t : transactions) {
            if (rule.getTransactionDetails() != null && t.getDetails() != null &&
                    t.getDetails().endsWith(rule.getTransactionDetails())) {
                t.setIncomeStatementType(rule.getIncomeStatementType());
            }
            if (rule.getTransactionBeneficiaryOrPayerAccount() != null && t.getBeneficiaryOrPayerAccount() != null &&
                    t.getBeneficiaryOrPayerAccount().endsWith(rule.getTransactionBeneficiaryOrPayerAccount())) {
                t.setIncomeStatementType(rule.getIncomeStatementType());
            }
            if (rule.getTransactionBeneficiaryOrPayerName() != null && t.getBeneficiaryOrPayerName() != null &&
                    t.getBeneficiaryOrPayerName().endsWith(rule.getTransactionBeneficiaryOrPayerName())) {
                t.setIncomeStatementType(rule.getIncomeStatementType());
            }
        }
    }

    public HashMap<IncomeStatementType, Entry> getGroupedTransactions(Long userId, LocalDate startDate, LocalDate endDate, List<Transaction> transactionsFromAnon) {
        HashMap<IncomeStatementType, Entry> groupedList = new HashMap<>();
        List<IncomeStatementType> incomeStatements = enumService.getIncomeStatementTypes();

        //add incomestatement to hashmap without transactions
        for (IncomeStatementType incomeStatement : incomeStatements) {
            groupedList.put(incomeStatement, new Entry(new ArrayList<>(), BigDecimal.valueOf(0)));
        }

        //then add each transaction according to the statement type aka key
        List<Transaction> transactions = new ArrayList<>();
              if(userId != null)  transactions = repo.findByUserIdAndDateBetween(userId, startDate, endDate);
              else transactions = transactionsFromAnon;

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
        File f = uploadRepo.findByTransactionsId(id);
        List<Transaction> fileTransactions = f.getTransactions();
        int i = 0;
        for (Transaction t : fileTransactions) {
            if(t.getId().equals(id)) {
                fileTransactions.remove(i);
                return;
            }
            i++;
        }
    }
}
