package ee.ttu.thesis;

import ee.ttu.thesis.domain.IncomeStatementType;
import ee.ttu.thesis.domain.Rule;
import ee.ttu.thesis.domain.User;
import ee.ttu.thesis.repository.RuleRepository;
import ee.ttu.thesis.service.RuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RuleServiceTest {

    @Mock
    private RuleRepository repo;
    @InjectMocks
    private RuleService service;

    @Test
    public void testInsertNewRule() throws Exception {
        when(repo.save(any(Rule.class))).then(returnsFirstArg());
        User user = getUser();
        Rule rule = getRule(user);
        Rule testRule = service.insertNewRule(rule, user);
        assertThat(testRule.getName()).isEqualTo("Default");
        assertThat(testRule.getIsAddedByUser()).isTrue();
        assertThat(testRule.getUser().getUsername()).isEqualTo("markoka");
    }

    private Rule getRule(User user) {
        return new Rule(1L,
                null,
                LocalDate.now(),
                "EE100",
                null, "makse",
                IncomeStatementType.INTRESSIKULUD, true, "CONTAINS", user);
    }

    private User getUser() {
        return new User(2L,
                "markoka", null, "asd", null);
    }



    @Test
    public void testDeleteRule()  {
        when(repo.save(any(Rule.class))).then(returnsFirstArg());
        User user = getUser();
        Rule rule = getRule(user);
        Rule testRule = service.insertNewRule(rule, user);
        service.deleteRule(testRule.getId());
        assertThat(service.getRuleListByUser(user.getId()).isEmpty());
    }
}
