package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Rule;
import ee.ttu.thesis.service.RuleService;
import ee.ttu.thesis.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rule")
@RequiredArgsConstructor
public class RuleController {

    private final RuleService ruleService;
    private final TransactionService transactionService;

    @PostMapping("/insert")
    @ResponseBody
    public void insertNewRule(@RequestBody Rule rule){
        ruleService.insertNewRule(rule);
        //transactionService.updateTransactionIncomeStatementTypesWithRule(rule);
    }
}
