package doancnpm.converter;

import org.springframework.stereotype.Component;

import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;

@Component
public class TutorConverter {
	public Tutor toTutor(AddTutorRequest addTutorRequest)
	{
		Tutor tutor = new Tutor();
		tutor.setRating(addTutorRequest.getRating());
		tutor.setAddress(addTutorRequest.getAddress());
		tutor.setDescription(addTutorRequest.getDescription());
		tutor.setQualification(addTutorRequest.getQualification());
		return tutor;
	}
	public Tutor toTutor(AddTutorRequest addTutorRequest, Tutor tutor)
	{
		tutor.setRating(addTutorRequest.getRating());
		tutor.setAddress(addTutorRequest.getAddress());
		tutor.setDescription(addTutorRequest.getDescription());
		tutor.setQualification(addTutorRequest.getQualification());
		return tutor;
	}
}
