package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ReportRepository  extends JpaRepository<Report, Long> {
}
