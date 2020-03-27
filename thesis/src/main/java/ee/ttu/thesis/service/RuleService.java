package ee.ttu.thesis.service;

import ee.ttu.thesis.domain.Rule;
import ee.ttu.thesis.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RuleService {
    @Autowired
    private RuleRepository repo;


    public void insertNewRule(Rule rule) {
        if(rule.getName() == null) rule.setName("Default");
        rule.setIsAddedByUser(true);
        repo.save(rule);
    }
}
