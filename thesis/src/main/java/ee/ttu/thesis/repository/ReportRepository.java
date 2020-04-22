package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.File;
import ee.ttu.thesis.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository  extends JpaRepository<Report, Long> {
    List<Report> findByUserId(Long userId);
}
