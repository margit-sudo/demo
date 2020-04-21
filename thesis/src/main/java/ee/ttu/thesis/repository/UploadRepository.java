package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadRepository extends JpaRepository<File, Long> {
    List<File> findByUserId(Long userId);
}
