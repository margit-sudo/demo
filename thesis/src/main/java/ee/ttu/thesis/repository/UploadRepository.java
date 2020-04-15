package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadRepository extends JpaRepository<File, Long> {
}
