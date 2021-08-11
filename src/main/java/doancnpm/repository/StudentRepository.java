package doancnpm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	Student findOneById(Long id);

	//Student findOneByusername(String username);
	
	Student findByUser_id(long id);
	
	Optional<Student> findByuser_id(long id);
}
