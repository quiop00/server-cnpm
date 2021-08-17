package doancnpm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{
	Subject findBysubjectname(String subject);

}
