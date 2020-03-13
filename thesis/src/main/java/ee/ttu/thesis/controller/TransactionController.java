package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.TransactionDto;
import ee.ttu.thesis.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{data}")
    public TransactionDto getTransactionData(@PathVariable String data) {
        return transactionService.getTransactionDto(data);
    }

}
