package doancnpm.converter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.models.Subject;
import doancnpm.models.TakenClass;
import doancnpm.payload.response.TakenClassResponse;

public class TakenClassConverter {
	public static TakenClassResponse modelToResponse(TakenClass takenClass) {
		TakenClassResponse response = new TakenClassResponse();
		response.setIdClass(takenClass.getId());
		response.setAddress(takenClass.getAddress());
		response.setGrade(takenClass.getGrade().getGradename());
		response.setIsRated(takenClass.getIsRated());
		Set<String> subjects = new HashSet<String>();
		for (Subject subject : takenClass.getSubjects()) {
			subjects.add(subject.getSubjectname());
		}
		response.setSubject(subjects);
		response.setStatus(takenClass.getStatus().name());
		try {
			Map<String, Boolean> schedule = new ObjectMapper().readValue(takenClass.getSchedule(), HashMap.class);
			System.out.println(schedule);
			response.setSchedules(schedule);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return response;
	}
}
