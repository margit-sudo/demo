package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.Rule;
import ee.ttu.thesis.domain.User;
import ee.ttu.thesis.security.UserDetailsImpl;
import ee.ttu.thesis.security.jwt.AuthTokenFilter;
import ee.ttu.thesis.security.jwt.JwtUtils;
import ee.ttu.thesis.service.RuleService;
import ee.ttu.thesis.service.TransactionService;
import ee.ttu.thesis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
    public void insertNewRule(@RequestBody Rule rule){
        ruleService.insertNewRule(rule);
        transactionService.updateTransactionIncomeStatementTypesWithRule(rule);
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
