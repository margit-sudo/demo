package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.Transaction;
import ee.ttu.thesis.service.TransactionService;
import ee.ttu.thesis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PutMapping(value = "/{id}")
    @ResponseBody
    public Optional<Transaction> updateTransactionIncomeStatementTypes(@PathVariable("id") Long transactionId, @RequestBody String type){
        IncomeStatementType incomeStatementType = IncomeStatementType.valueOf(type);
        return transactionService.updateOneTransactionIncomeStatementType(transactionId, incomeStatementType);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTransactionById(@PathVariable("id") Long id){
        transactionService.deleteTransaction(id);
    }
}
