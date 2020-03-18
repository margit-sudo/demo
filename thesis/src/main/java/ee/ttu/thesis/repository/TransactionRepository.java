package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
