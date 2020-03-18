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
    public List<Transaction> getAllTransactions(){
        //transactionService.addTransactionsFromFile();
        return transactionService.getTransactionsList();
    }

    @GetMapping("/insert")
    public void insertTestData() throws IOException {
        transactionService.addTransactionsFromFile();
    }


    @PostMapping("/update")
    @ResponseBody
    public void updateTransactionIncomeStatementTypes(@RequestBody List<Transaction> transactions){
        transactionService.updateTransactionIncomeStatementTypes(transactions);
    }
}
