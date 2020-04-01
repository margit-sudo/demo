package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/update")
    @ResponseBody
    public void updateTransactionIncomeStatementTypes(@RequestBody List<Transaction> transactions){
        transactionService.updateTransactionIncomeStatementTypes(transactions);
    }

    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public void updateTransactionIncomeStatementTypes(@PathVariable("id") Long id, @RequestBody String type){
        IncomeStatementType incomeStatementType = IncomeStatementType.valueOf(type);
        transactionService.updateOneTransactionIncomeStatementType(id, incomeStatementType);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteTransactionById(@PathVariable("id") Long id){
        transactionService.deleteTransaction(id);
    }
}
