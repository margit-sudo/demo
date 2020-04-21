package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepository  extends JpaRepository<Rule, Long> {
    List<Rule> findByUserId(Long userId);
}
