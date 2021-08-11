package doancnpm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.Student;
import doancnpm.payload.request.StudentRequest;
import doancnpm.payload.response.StudentOutput;
import doancnpm.payload.response.TutorOutput;
import doancnpm.security.IStudentService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

public class StudentController {
	@Autowired
	private IStudentService studentService;

	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping(value = "/student")
	public Map<String, List<StudentOutput>> all() {
		List<Student> students = studentService.all();
		
		List<StudentOutput> studentOutputs = new ArrayList<StudentOutput>();
		for(int i=0;i<students.size();i++) {
			StudentOutput studentOutput = new StudentOutput();
			studentOutput.setId(students.get(i).getId());
			studentOutput.setIdUser(students.get(i).getUser().getId());
			studentOutput.setName(students.get(i).getUser().getName());
			studentOutput.setAge(students.get(i).getUser().getAge());
			studentOutput.setPhonenumber(students.get(i).getUser().getPhonenumber());
			studentOutput.setGender(students.get(i).getUser().getGender());
			
			studentOutputs.add(studentOutput);
		}
		
		Map<String, List<StudentOutput>> response = new HashMap<String, List<StudentOutput>>();
		response.put("students", studentOutputs);
		return response;
	}

	@GetMapping("/api/student/profile")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<StudentOutput> getStudent(HttpServletRequest request){
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}	
		
		Student studentData = studentService.findStudent(username);
		
		StudentOutput studentOutput = new StudentOutput();
		studentOutput.setId(studentData.getId());
		studentOutput.setIdUser(studentData.getUser().getId());
		studentOutput.setName(studentData.getUser().getName());
		studentOutput.setPhonenumber(studentData.getUser().getPhonenumber());
		studentOutput.setAge(studentData.getUser().getAge());
		studentOutput.setGender(studentData.getUser().getGender());
		
		if (studentData != null) {
			return new ResponseEntity<>(studentOutput, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/student/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
	public ResponseEntity<Student> getUserById(@PathVariable("id") long id) {
		Student student = studentService.getStudentById(id);

		if (student != null) {
			return new ResponseEntity<>(student, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/api/student")
	@PreAuthorize("hasRole('STUDENT')")
	public String updateStudent(HttpServletRequest request, @RequestBody StudentRequest model) {

//	    Optional<User> userEdit = userService.findUserById(userId);  
//	    userEdit.ifPresent(user -> model.addAttribute("user", user));
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		studentService.save(username, model);

		return "Update is success";
	}

	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
	
}
