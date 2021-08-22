package doancnpm.converter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.models.Candidate;
import doancnpm.models.Post;
import doancnpm.models.Subject;
import doancnpm.payload.request.PostRequest;
import doancnpm.payload.response.CandidateResponse;
import doancnpm.payload.response.PostOut;

@Component
public class PostConverter {

	public Post toEntity(PostRequest dto) {
		Post entity = new Post();
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setAddress(dto.getAddress());
		Date date = null;
		if(dto.getFinishDate()!=null)
		try {
			SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yyyy");  
			date = formatter.parse(dto.getFinishDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			date = null;
		}
		if(date == null) {
			date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE,7);
			date = calendar.getTime();
		}
		entity.setFinishDate(date);
		return entity;
	}

	public PostRequest toDTO(Post entity) {
		PostRequest dto = new PostRequest();
		if (entity.getId() != null) {
			dto.setId(entity.getId());
		}
		dto.setTitle(entity.getTitle());
		dto.setDescription(entity.getDescription());
		dto.setPrice(entity.getPrice());
		dto.setAddress(entity.getAddress());
		dto.setCreatedDate(entity.getCreatedDate());
		dto.setModifiedDate(entity.getModifiedDate());

		return dto;
	}

	public Post toEntity(PostRequest dto, Post entity) {

		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setAddress(dto.getAddress());
		Date date = null;
		try {
			date = DateFormat.getInstance().parse(dto.getFinishDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			date = null;
		}
		if(date == null) {
			date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE,1);
			date = calendar.getTime();
		}
		entity.setFinishDate(date);
		return entity;
	}
	public static PostOut modelToResponse(Post post){
			String schedules = post.getSchedule();
			PostOut postOut = new PostOut();
			postOut.setId(post.getId());
			postOut.setStudentName(post.getStudent().getUser().getName());
			postOut.setPhonenumber("Kết nối để xem");
			postOut.setAddress("Kết nối để xem");
			postOut.setGrade(post.getGrade().getGradename());
			Set<Subject> setSubjects = post.getSubjects();
			Set<String> subjects = new HashSet<String>();
			for (Subject subject : setSubjects) {
				subjects.add(subject.getSubjectname());
			}
			postOut.setSubject(subjects);
			postOut.setDescription(post.getDescription());
			postOut.setPrice(post.getPrice());
			postOut.setTitle(post.getTitle());
			postOut.setIdStudent(post.getStudent().getId());
			
			Set<CandidateResponse> candidates = new HashSet<CandidateResponse>();
			if(post.getCandidates()!=null)
			for(Candidate element:post.getCandidates()) {
				CandidateResponse candidate = new CandidateResponse();
				candidate = CandidateConverter.modelToResponse(element);
				candidates.add(candidate);
			}
			postOut.setCandidates(candidates);

			String pattern = "MM-dd-yyyy";
			DateFormat df = new SimpleDateFormat(pattern);        
			postOut.setFinishDate(df.format(post.getFinishDate()));
			
			postOut.setIsExpire(post.getIsExpire());
			
			postOut.setStatus(post.getStatus().name());
			
			try {
				Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
				System.out.println(schedule);
				postOut.setSchedules(schedule);
			} catch (IOException e) {

				e.printStackTrace();
			}
		return postOut;
	}
}
