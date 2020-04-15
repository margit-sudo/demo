package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository  extends JpaRepository<Rule, Long> {
}
