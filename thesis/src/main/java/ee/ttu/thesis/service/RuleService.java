package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.Rule;
import ee.ttu.thesis.domain.User;
import ee.ttu.thesis.repository.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RuleService {

    private final RuleRepository repo;

    public Rule insertNewRule(Rule rule, User u) {
        if(rule.getName() == null) rule.setName("Default");
        rule.setUser(u);
        rule.setIsAddedByUser(true);
        return repo.save(rule);
    }

    public List<Rule> getRuleList() {
        return repo.findAll();
    }

    public void deleteRule(Long id) {
        repo.deleteById(id);
    }

    public List<Rule> getRuleListByUser(Long userId) {
        return repo.findByUserId(userId);
    }
}
