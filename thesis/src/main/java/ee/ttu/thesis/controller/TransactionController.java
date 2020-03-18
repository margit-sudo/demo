package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/all")
    public List<Transaction> getAllTransactions() throws IOException {
        transactionService.addTransactionsFromFile();
        return transactionService.getTransactionsList();
    }

    @PutMapping("/update")
    public void updateTransactionIncomeStatementTypes(List<Transaction> transactions){
        transactionService.updateTransactionIncomeStatementTypes(transactions);
    }
}
