package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Rule;
import ee.ttu.thesis.service.RuleService;
import ee.ttu.thesis.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        transactionService.updateTransactionIncomeStatementTypesWithRule(rule);
    }

    @GetMapping("/all")
    public List<Rule> getAllRules(){
        return ruleService.getRuleList();
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteRuleById(@PathVariable("id") Long id){
       ruleService.deleteRule(id);
    }
}
