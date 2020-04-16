package ee.ttu.thesis.repository;

import ee.ttu.thesis.domain.ERole;
import ee.ttu.thesis.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
