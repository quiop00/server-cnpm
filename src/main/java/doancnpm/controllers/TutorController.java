package doancnpm.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.converter.TutorConverter;
import doancnpm.models.ERole;
import doancnpm.models.Grade;
import doancnpm.models.Subject;
import doancnpm.models.Suggestion;
import doancnpm.models.Tutor;

import doancnpm.payload.request.AddTutorRequest;
import doancnpm.security.ITutorService;
import doancnpm.security.iSuggestionService;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.payload.response.ClassResponse;
import doancnpm.payload.response.TutorOutput;
import doancnpm.payload.response.TutorResponse;
import doancnpm.repository.GradeRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ITutorService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController


public class TutorController {
	@Autowired
	private ITutorService tutorService;
	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	private iSuggestionService suggestionService;
	@Autowired
	private GradeRepository gradeRepository;
	
	public ObjectMapper getObjectMapper() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.addMixIn(Object.class, IgnoreHibernatePropertiesInJackson.class);
	    return mapper;
	}

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private abstract class IgnoreHibernatePropertiesInJackson{ }
	
//	@GetMapping(value = "/tutor")
//	public Map<String,List<Tutor>> all(){
//		System.out.println("ok");
//		List<Tutor> tutors = tutorService.findAll();
////		System.out.println(tutors.get(0).getSubject().getSubjectname()+"Thanh");
//		Map<String,List<Tutor>> response=new HashMap<String, List<Tutor>>();
//		response.put("tutors", tutors);
//	}
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TutorRepository tutorRepository;
	
	@GetMapping("/api/tutor/class")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
	public Map<String, List<ClassResponse>> getClassList(HttpServletRequest request) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}	
		Tutor tutorData = tutorService.findTutor(username);
		List<Suggestion> suggestions = suggestionService.findAll();
		List<ClassResponse> classResponses = new ArrayList<ClassResponse>(); 
		for(int i=0;i<suggestions.size();i++) 
			if(suggestions.get(i).getStatus()==1 && suggestions.get(i).getTutor().equals(tutorData)){
				ClassResponse classResponse = new ClassResponse();
				classResponse.setIdStudent(suggestions.get(i).getStudent().getId());
				classResponse.setNameStudent(suggestions.get(i).getStudent().getUser().getName());
				classResponse.setIdPost(suggestions.get(i).getPost().getId());
				classResponse.setTitlePost(suggestions.get(i).getPost().getTitle());
				classResponses.add(classResponse);
			}
		Map<String, List<ClassResponse>> response = new HashMap<String, List<ClassResponse>>();
		response.put("class", classResponses);
		return response;
	}	
	
	@GetMapping("/api/tutor/profile")
	@PreAuthorize("hasRole('TUTOR')")
	public ResponseEntity<TutorOutput> getTutor(HttpServletRequest request) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}	
		User user = userRepository.findOneByusername(username);
		Tutor tutorData = tutorService.findTutor(username);
		
		TutorOutput tutorOutput = TutorConverter.modelToResponse(tutorData);
		tutorOutput.setCmnd(tutorData.getCmnd());
		tutorOutput.setPhonenumber(tutorData.getUser().getPhonenumber());
	
		if (tutorData != null) {
			return new ResponseEntity<>(tutorOutput, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}	
	
	@GetMapping("/api/tutor/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
	public ResponseEntity<TutorOutput> getTutorById(@PathVariable("id") long id) {
		Tutor tutorData = tutorService.findTutorById(id);
		
		TutorOutput tutorOutput = TutorConverter.modelToResponse(tutorData);
		
		
		if (tutorData != null) {
			return new ResponseEntity<>(tutorOutput, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(value = "/tutor")
	
	public Map<String, List<TutorOutput>> showTutor() {

		List<Tutor> tutors = tutorService.getTutorsByVerify(true);
		List<TutorOutput> tutorOutputs = new ArrayList<TutorOutput>();
		for (int i = 0; i < tutors.size(); i++) {
			TutorOutput tutorOutput = new TutorOutput();
			tutorOutput = TutorConverter.modelToResponse(tutors.get(i));
			tutorOutputs.add(tutorOutput);
		}
		Map<String, List<TutorOutput>> response = new HashMap<String, List<TutorOutput>>();
		response.put("tutors", tutorOutputs);
		return response;

	}

//	@GetMapping(value = "/pagetutor")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
//	public TutorResponse showTutorPage(@RequestParam("page") int page, @RequestParam("limit") int limit){
//		
//		
//		TutorResponse result = new TutorResponse();
//		result.setPage(page);
//		Pageable pageable = new PageRequest(page - 1, limit);
//		result.setListResult(tutorService.findAllPage(pageable));
//		
//		result.setTotalPage((int) Math.ceil((double) (tutorService.totalItem()) / limit));
//		
//		return result;
//	
//	}

//	@PostMapping(value = "/tutor")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR')")
//	public void createNew(@RequestBody AddTutorRequest model) {
//	
//		tutorService.save(model);
//		
//	}

	/*
	 * ---------------------------------UPDATE TUTOR PROFILE------------------------------------------
	 */
	@PutMapping(value = "/api/tutor/profile")
	@PreAuthorize("hasRole('TUTOR')")
	public String updateTutor(HttpServletRequest request, @RequestBody AddTutorRequest model) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}	
		tutorService.save(username, model);
		String message = "Update tutor is success !\n";
		return message;
	}

	/*
	 * -------------------------------------------------------------------------------------------------
	 */
	
	@DeleteMapping(value = "/api/tutor")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@RequestBody long[] ids) {
		tutorService.delete(ids);
	}

	
	@GetMapping(value = "/tutor/search")
	public Map<String, List<TutorOutput>> searchTutor(@RequestParam(required = false) String grade, String address,
			String subject) {
		List<Tutor> tutors = new ArrayList<Tutor>();
		List<Subject> subjects = new ArrayList<Subject>();
		Subject mon = subjectRepository.findBysubjectname(subject);
		subjects.add(mon);
		List<Grade> grades = new ArrayList<Grade>();
		Grade lop = gradeRepository.findBygradename(grade);
		grades.add(lop);
		if (grade == null && address == null && subject == null)
			tutors = tutorRepository.findAll();
		else if (subject != null && grade == null && address == null) {
			tutors = tutorRepository.findBySubjects(subjects);
		} else if (grade != null && address == null && subject == null) {
			tutors = tutorRepository.findByGrades(grades);
		} else if (subject == null && grade == null && address != null) {
			tutors = tutorRepository.findByAddress(address);
		} else if (subject == null && grade != null && address != null) {
			tutors = tutorRepository.findByGradesInAndAddressIn(grades, address);
		} else if (subject != null && grade != null && address == null) {
			tutors = tutorRepository.findBySubjectsInAndGradesIn(subjects, grades);
		} else if (subject != null && grade == null && address != null) {
			tutors = tutorRepository.findBySubjectsInAndAddressIn(subjects, address);
		} else {
			tutors = tutorRepository.findBySubjectsInAndGradesInAndAddressIn(subjects, grades, address);
		}
		List<TutorOutput> tutorOutputs = new ArrayList<TutorOutput>();
		for (int i = 0; i < tutors.size(); i++) {
			String schedules = tutors.get(i).getSchedule();
			TutorOutput tutorOutput = new TutorOutput();
			tutorOutput.setId(tutors.get(i).getId());
			tutorOutput.setName(tutors.get(i).getUser().getName());
			tutorOutput.setPhonenumber(tutors.get(i).getUser().getPhonenumber());
			tutorOutput.setQualification(tutors.get(i).getQualification());
			tutorOutput.setAvatar(tutors.get(i).getUser().getAvatar());
			tutorOutput.setRating(tutors.get(i).getRating());
			tutorOutput.setDescription(tutors.get(i).getDescription());
			tutorOutput.setAddress(tutors.get(i).getAddress());
			Set<Subject> setSubjects = tutors.get(i).getSubjects();
			Set<String> subjects1 = new HashSet<String>();
			for (Subject subject1 : setSubjects) {
				subjects1.add(subject1.getSubjectname());
			}
			tutorOutput.setSubject(subjects1);

			Set<Grade> setGrades = tutors.get(i).getGrades();
			Set<String> grades1 = new HashSet<String>();
			for (Grade grade1 : setGrades) {
				grades1.add(grade1.getGradename());
			}
			tutorOutput.setGrade(grades1);
			try {
				Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
				System.out.println(schedule);
				tutorOutput.setSchedules(schedule);
			} catch (IOException e) {

				e.printStackTrace();
			}
			tutorOutputs.add(tutorOutput);
		}
		Map<String, List<TutorOutput>> response = new HashMap<String, List<TutorOutput>>();
		response.put("tutor", tutorOutputs);
		return response;

	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
}