package doancnpm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import doancnpm.models.Grade;
import doancnpm.models.Student;
import doancnpm.models.Subject;
import doancnpm.models.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
	Optional<Tutor> findByuser_id(long id);

	Tutor findOneById(Long id);

	Tutor findByUser_id(long id);

	List<Tutor> findById(Long tutorId);

	List<Tutor> findByAddress(String address);
	
	List<Tutor> findByVerify(Boolean verify);

	@Modifying
	@Query("SELECT t FROM Tutor t INNER JOIN t.subjects s WHERE s IN (:subjects)")
	List<Tutor> findBySubjects(@Param("subjects") List<Subject> subjects);

	@Modifying
	@Query("SELECT t FROM Tutor t INNER JOIN t.grades g WHERE g IN (:grades)")
	List<Tutor> findByGrades(@Param("grades") List<Grade> grades);

	List<Tutor> findBySubjectsInAndAddressIn(List<Subject> subjects, String address);

	List<Tutor> findBySubjectsInAndGradesIn(List<Subject> subjects, List<Grade> grades);

	List<Tutor> findByGradesInAndAddressIn(List<Grade> grades, String address);

	List<Tutor> findBySubjectsInAndGradesInAndAddressIn(List<Subject> subjects, List<Grade> grades, String address);
}
