package doancnpm.security;

import java.util.List;

import doancnpm.models.Suggestion;

public interface iSuggestionService {

	void save(String username, Long idPost, Long idStudent);
	void accept(String username, long idPost, long idTutor);
	void reject(String username, long idPost, long idTutor);
	void delete(String username, long id);
	List<Suggestion> findByIdStudent(long idStudent);
	List<Suggestion> findAll();
}
