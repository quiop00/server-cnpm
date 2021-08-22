package doancnpm.converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.models.Grade;
import doancnpm.models.Subject;
import doancnpm.models.Tutor;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.response.TutorOutput;

@Component
public class TutorConverter {
	public Tutor toTutor(AddTutorRequest addTutorRequest)
	{
		Tutor tutor = new Tutor();
		tutor.setAddress(addTutorRequest.getAddress());
		tutor.setDescription(addTutorRequest.getDescription());
		tutor.setQualification(addTutorRequest.getQualification());
		tutor.setCertificate(addTutorRequest.getCertificate());
		return tutor;
	}
	public Tutor toTutor(AddTutorRequest addTutorRequest, Tutor tutor)
	{
		tutor.setAddress(addTutorRequest.getAddress());
		tutor.setDescription(addTutorRequest.getDescription());
		tutor.setQualification(addTutorRequest.getQualification());
		tutor.setCertificate(addTutorRequest.getCertificate());
		return tutor;
	}
	public static TutorOutput modelToResponse(Tutor tutor) {
		String schedule = tutor.getSchedule();
		// JSONObject jsonObject= new JSONObject(schedule );

		TutorOutput tutorOutput = new TutorOutput();
		tutorOutput.setId(tutor.getId());
		tutorOutput.setQualification(tutor.getQualification());
		tutorOutput.setAvatar(tutor.getUser().getAvatar());
		tutorOutput.setRating(tutor.getRating());
		tutorOutput.setDescription(tutor.getDescription());
		tutorOutput.setAddress(tutor.getAddress());
		tutorOutput.setName(tutor.getUser().getName());
		tutorOutput.setAge(tutor.getUser().getAge());
		tutorOutput.setVerify(tutor.getVerify());
		tutorOutput.setBlock(tutor.getUser().getBlock());
		tutorOutput.setGender(tutor.getUser().getGender());
		tutorOutput.setIsComplete(tutor.getIsCompleted());
		
		tutorOutput.setCmnd("");
		tutorOutput.setPhonenumber("Không thể xem");
		
		Set<Grade> setGrades = tutor.getGrades();
		Set<String> grades = new HashSet<String>();
		for(Grade grade : setGrades) {
			grades.add(grade.getGradename());
		}
		tutorOutput.setGrade(grades);
		
		Set<Subject> setSubjects = tutor.getSubjects();
		Set<String> subjects = new HashSet<String>();
		for(Subject subject : setSubjects ) {
			subjects.add(subject.getSubjectname());
		}
		tutorOutput.setSubject(subjects);
		
		if (schedule != null) {
			// tutorOutput.setSchedule(jsonObject);
			try {
				Map<String, Boolean> schedules = new ObjectMapper().readValue(schedule, HashMap.class);
				System.out.println(schedules);
				tutorOutput.setSchedules(schedules);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tutorOutput;
	}
}
