package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.service.TransactionService;
import ee.ttu.thesis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping("/all")
    public List<Transaction> getAllTransactions(@RequestHeader(name = "Authorization") String token){
        return transactionService.getTransactionsByUserId(userService.getUserIdFromToken(token));
    }

    @PostMapping(value = "/{id}")
    @ResponseBody
    public void updateTransactionIncomeStatementTypes(@PathVariable("id") Long transactionId, @RequestBody String type){
        IncomeStatementType incomeStatementType = IncomeStatementType.valueOf(type);
        transactionService.updateOneTransactionIncomeStatementType(transactionId, incomeStatementType);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTransactionById(@PathVariable("id") Long id){
        transactionService.deleteTransaction(id);
    }
}
