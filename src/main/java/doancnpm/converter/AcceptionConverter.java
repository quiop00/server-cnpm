package doancnpm.converter;

import doancnpm.enums.CandidateStatus;
import doancnpm.models.Candidate;
import doancnpm.models.Post;
import doancnpm.payload.response.AcceptionResponse;

public class AcceptionConverter {

	public static AcceptionResponse modelToResponse(Post post) {
		AcceptionResponse acception =new AcceptionResponse();
		
		acception.setPost(PostConverter.modelToResponse(post));
		
		for(Candidate candidate: post.getCandidates()) {
			if(candidate.getStatus()==CandidateStatus.WAITING)
				{
					acception.setTutor(TutorConverter.modelToResponse(candidate.getTutor()));
					break;
				}
		}
		
		return acception;
		
	}
	
}
