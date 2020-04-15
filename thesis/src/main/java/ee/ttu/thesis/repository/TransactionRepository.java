package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
