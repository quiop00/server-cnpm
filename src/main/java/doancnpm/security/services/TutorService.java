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
			switch (subject) {
			case "Toán":
				Subject toan = subjectRepository.findBysubjectname("Toán");
						
				subjects.add(toan);
				break;
			case "Tiếng Anh":
				Subject tienganh = subjectRepository.findBysubjectname("Tiếng Anh");
						
				subjects.add(tienganh);
				break;	
			case "Tiếng Việt":
				Subject tiengviet = subjectRepository.findBysubjectname("Tiếng Việt");
						
				subjects.add(tiengviet);
				break;
			case "Hóa":
				Subject hoa = subjectRepository.findBysubjectname("Hóa");
					

				subjects.add(hoa);
				break;
		
			case "Lý":
			Subject ly = subjectRepository.findBysubjectname("Lý");
					

			subjects.add(ly);
			break;
			case "Ngữ Văn":
				Subject nguvan = subjectRepository.findBysubjectname("Ngữ Văn");
					

				subjects.add(nguvan);
				break;
			case "Lịch Sử":
				Subject lichsu = subjectRepository.findBysubjectname("Lịch Sử");
						

				subjects.add(lichsu);
				break;	
			}
		});

		Set<String> strGrade = addTutorRequest.getGrade();
		Set<Grade> grades = new HashSet<>();

		strGrade.forEach(grade -> {
			switch (grade) {
			case "Lớp 1":
				Grade lop1 = gradeRepository.findBygradename("Lớp 1");
						
				grades.add(lop1);
				break;
			case "Lớp 2":
				Grade lop2 = gradeRepository.findBygradename("Lớp 2");
						
				grades.add(lop2);
				break;
			case "Lớp 3":
				Grade lop3 = gradeRepository.findBygradename("Lớp 3");
						
				grades.add(lop3);
				break;
			case "Lớp 4":
				Grade lop4 = gradeRepository.findBygradename("Lớp 4");
						
				grades.add(lop4);
				break;
			case "Lớp 5":
				Grade lop5 = gradeRepository.findBygradename("Lớp 5");
						
				grades.add(lop5);
				break;
			case "Lớp 6":
				Grade lop6 = gradeRepository.findBygradename("Lớp 6");
						
				grades.add(lop6);
				break;	
			case "Lớp 8":
				Grade lop8 = gradeRepository.findBygradename("Lớp 8");
						
				grades.add(lop8);
				break;	
			case "Lớp 9":
				Grade lop9 = gradeRepository.findBygradename("Lớp 9");
					
				grades.add(lop9);
				break;	
			case "Lớp 10":
				Grade lop10 = gradeRepository.findBygradename("Lớp 10");
						
				grades.add(lop10);
				break;	
			case "Lớp 11":
				Grade lop11 = gradeRepository.findBygradename("Lớp 11");
					
				grades.add(lop11);
				break;	
			case "Lớp 12":
				Grade lop12 = gradeRepository.findBygradename("Lớp 12");
						
				grades.add(lop12);
				break;		
			}
		});

		Map<String, Boolean> schedule = addTutorRequest.getSchedules();
//		 try {
//	            String jsonResp = mapperObj.writeValueAsString(inputMap);
//	            System.out.println(jsonResp);
//	        } catch (IOException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
		Tutor tutor = new Tutor();
		Tutor oldTutor = tutorRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Tutor Not Found"));
		
		tutor = tutorConverter.toTutor(addTutorRequest, oldTutor);
		
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
		user.setPhonenumber(addTutorRequest.getPhonenumber());
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

}
