package doancnpm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import doancnpm.models.Suggestion;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
	
	Suggestion findByTutor_idAndPost_idAndStudent_id(long idTutor, long idPost, long idStudent);
	List<Suggestion> findBystudent_id(long idStudent);

}
