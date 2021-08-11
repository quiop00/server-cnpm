package doancnpm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByuser_id(long id);
}
