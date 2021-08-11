package doancnpm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import doancnpm.models.Invitation;

public interface InvitationRepository extends JpaRepository<Invitation, Long>{
//	Optional<Invitation> findBytutor_idAndstudent_id(Tutor Tutor, Student student);
//	@Query("SELECT i FROM Invitation i"
//			+ " JOIN i.tutor t "
//			+ " JOIN i.student s"
//			+ " WHERE t.id = :tutor_id AND s.id = :student_id")

	Invitation findByTutor_idAndStudent_id(long idTutor, long idStudent);
	List<Invitation> findBytutor_id(long idTutor);
}
