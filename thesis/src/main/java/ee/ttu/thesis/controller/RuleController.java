package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Rule;
import ee.ttu.thesis.domain.User;
import ee.ttu.thesis.service.RuleService;
import ee.ttu.thesis.service.TransactionService;
import ee.ttu.thesis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rule")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RuleController {

    private final RuleService ruleService;
    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping("/insert")
    @ResponseBody
    public void insertNewRule(@RequestBody Rule rule, @RequestHeader(name = "Authorization") String token){
        User user = userService.getUserFromToken(token);
        ruleService.insertNewRule(rule, user);
        transactionService.updateTransactionIncomeStatementTypesWithRule(rule, user);
    }

    @GetMapping("/all")
    public List<Rule> getRulesByUser(@RequestHeader(name = "Authorization") String token){
        return ruleService.getRuleListByUser(userService.getUserIdFromToken(token));
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteRuleById(@PathVariable("id") Long id){
       ruleService.deleteRule(id);
    }
}
