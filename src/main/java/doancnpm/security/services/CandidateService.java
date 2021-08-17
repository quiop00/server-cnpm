package doancnpm.security.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import doancnpm.models.Candidate;
import doancnpm.security.ICandidateService;

@Service
public class CandidateService implements ICandidateService{

	@Override
	public Candidate findByIdTutor(Set<Candidate> candidates, Long idTutor) {
		for(Candidate candidate: candidates) {
			if(candidate.getTutor().getId()==idTutor)
				return candidate;
		}
		return null;
	}

	@Override
	public Candidate findByStatus(Set<Candidate> candidates, String status) {
		for(Candidate candidate: candidates) {
			if(candidate.getStatus().name().equals(status))
				return candidate;
		}
		return null;
	}

}
