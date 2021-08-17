package doancnpm.security;

import java.util.Set;

import doancnpm.models.Candidate;

public interface ICandidateService {
	Candidate findByIdTutor(Set<Candidate> candidates,Long idTutor);
	Candidate findByStatus(Set<Candidate> candidates,String status);
}
