package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.TransactionDto;
import ee.ttu.thesis.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/all")
    public List<TransactionDto> getAllTransactions() throws IOException {
        return transactionService.parseCsvFileToTransaction();
    }

}
