package doancnpm.security;

import java.util.List;

import doancnpm.models.Invitation;



public interface IInvitationService {
	void save(String username, Long idTutor);
	void accept(String username, long idStudent);
	void reject(String username, long idStudent);
	List<Invitation> findByIdTutor(long idTutor);
	List<Invitation> findAll();
}
