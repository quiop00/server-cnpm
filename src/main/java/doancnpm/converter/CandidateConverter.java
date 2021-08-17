package doancnpm.converter;

import doancnpm.models.Candidate;
import doancnpm.payload.response.CandidateResponse;

public class CandidateConverter {

	public static CandidateResponse modelToResponse(Candidate candidate) {
		CandidateResponse candidateResponse = new CandidateResponse();
		candidateResponse.setIdPost(candidate.getPost().getId());
		candidateResponse.setStatus(candidate.getStatus().name());
		candidateResponse.setTutor(TutorConverter.modelToResponse(candidate.getTutor()));
		return candidateResponse;
	}
}
