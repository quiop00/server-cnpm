package doancnpm.security;

public interface IRateService {
	void save(String username, Long idTutor, int star);
	float getStar(Long idTutor);
}
