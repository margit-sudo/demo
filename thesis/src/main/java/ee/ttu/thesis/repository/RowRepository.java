package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.ReportRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RowRepository extends JpaRepository<ReportRow, Long> {
}
