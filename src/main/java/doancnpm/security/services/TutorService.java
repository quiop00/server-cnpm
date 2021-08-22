package doancnpm.security.services;

import java.util.HashSet;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.converter.TutorConverter;
import doancnpm.models.Grade;

import doancnpm.models.Subject;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.repository.GradeRepository;

import doancnpm.repository.SubjectRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IImageService;
import doancnpm.security.ITutorService;

@Service
public class TutorService implements ITutorService {

	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	IImageService imageService;
	@Autowired
	UserRepository userRepository;

	@Autowired
	private TutorConverter tutorConverter;

	@Override
	public List<Tutor> findAll() {
		return tutorRepository.findAll();
	}

	@Override
	public Tutor findTutorById(Long id) {

		return tutorRepository.findOne(id);
	}

	@Override
	public void save(String username, AddTutorRequest addTutorRequest) {

		User user = userRepository.findOneByusername(username);

		Set<String> strSubject = addTutorRequest.getSubject();
		Set<Subject> subjects = new HashSet<>();
		strSubject.forEach(subject -> {
			Subject sbj = subjectRepository.findBysubjectname(subject);
			subjects.add(sbj);
		});

		Set<String> strGrade = addTutorRequest.getGrade();
		Set<Grade> grades = new HashSet<>();

		strGrade.forEach(grade -> {
			Grade lop = gradeRepository.findBygradename(grade);
			grades.add(lop);
		});

		Map<String, Boolean> schedule = addTutorRequest.getSchedules();

		Tutor tutor = new Tutor();
		Tutor oldTutor = tutorRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Tutor Not Found"));

		tutor = tutorConverter.toTutor(addTutorRequest, oldTutor);

		// save avatar, cmnd
		try {
			String avatarName = imageService.save(addTutorRequest.getAvatar());
			String avatarUrl = imageService.getImageUrl(avatarName);
			String cmndName = imageService.save(addTutorRequest.getCmnd());
			String cmndUrl = imageService.getImageUrl(cmndName);

			user.setAvatar(avatarUrl);
			tutor.setCmnd(cmndUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			user.setAvatar("");
			tutor.setCmnd("");
		}

		tutor.setGrades(grades);
		tutor.setSubjects(subjects);

		String jsonResp = "";
		ObjectMapper mapperObj = new ObjectMapper();
		try {
			jsonResp = mapperObj.writeValueAsString(schedule);
			System.out.println(jsonResp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		tutor.setSchedule(jsonResp);
		tutor.setUser(user);

		user.setName(addTutorRequest.getName());
		user.setAge(addTutorRequest.getAge());

		user.setGender(addTutorRequest.getGender());

		userRepository.save(user);

		tutor = tutorRepository.save(tutor);
	}

	@Override
	public void delete(long[] ids) {
		for (long item : ids) {
			tutorRepository.delete(item);
		}

	}

	@Override
	public List<Tutor> findAllPage(Pageable pageable) {
		return tutorRepository.findAll(pageable).getContent();
	}

	@Override
	public int totalItem() {
		// TODO Auto-generated method stub
		return (int) tutorRepository.count();
	}

	@Override
	public Tutor findTutor(String username) {
		User user = userRepository.findOneByusername(username);
		Tutor tutor = tutorRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Tutor Not Found"));
		return tutor;
	}

	@Override
	public List<Tutor> getTutorsByVerify(Boolean verify) {
		List<Tutor> tutors = tutorRepository.findByVerify(verify);
		return tutors;
	}

	@Override
	public Boolean approvalTutor(Boolean approval, Long id) {
		Tutor tutor = tutorRepository.findOne(id);
		if (tutor == null)
			return false;
		tutor.setVerify(approval);
		tutorRepository.save(tutor);
		return true;
	}

}
