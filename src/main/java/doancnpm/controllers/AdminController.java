package doancnpm.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.controllers.output.GradeOutput;
import doancnpm.controllers.output.SubjectOutPut;
import doancnpm.converter.AcceptionConverter;
import doancnpm.converter.PostConverter;
import doancnpm.converter.TutorConverter;
import doancnpm.converter.UserConverter;
import doancnpm.enums.CandidateStatus;
import doancnpm.enums.ClassStatus;
import doancnpm.enums.NotifyType;
import doancnpm.enums.PostStatus;
import doancnpm.models.Candidate;
import doancnpm.models.ERole;
import doancnpm.models.Grade;
import doancnpm.models.Notification;
import doancnpm.models.Post;
import doancnpm.models.Role;
import doancnpm.models.Subject;
import doancnpm.models.TakenClass;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.ApprovalRequest;
import doancnpm.payload.request.GradeRequest;
import doancnpm.payload.request.PostRequest;
import doancnpm.payload.request.SubjectRequest;
import doancnpm.payload.request.UserRequest;
import doancnpm.payload.response.AcceptionResponse;
import doancnpm.payload.response.PostOut;
import doancnpm.payload.response.TutorOutput;
import doancnpm.payload.response.UserResponse;
import doancnpm.repository.ClassRepository;
import doancnpm.repository.GradeRepository;
import doancnpm.repository.NotificationRepository;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ICandidateService;
import doancnpm.security.ITutorService;
import doancnpm.security.IUserService;
import doancnpm.security.iPostService;
import doancnpm.security.jwt.JwtUtils;
import doancnpm.security.services.CandidateService;
import doancnpm.security.services.NotificationService;

@CrossOrigin
@RestController
public class AdminController {

	@Autowired
	PostRepository postRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	GradeRepository gradeRepository;

	@Autowired
	NotificationService notificationService;
	@Autowired
	UserRepository userRepository;

	@Autowired
	TutorRepository tutorRepository;

	@Autowired
	private ITutorService tutorService;

	@Autowired
	private IUserService userService;

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	private iPostService postService;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	NotificationRepository notificationRepo;
	@Autowired
	ICandidateService candidateService;

	@Autowired
	private JwtUtils jwtUtils;

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}

	@GetMapping(value = "/admin/post")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<PostOut>> showPost() {
		List<Post> posts = postService.findAll();
		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < posts.size(); i++) {
			PostOut postOut = new PostOut();
			postOut = PostConverter.modelToResponse(posts.get(i));
			postOuts.add(postOut);
		}
		Map<String, List<PostOut>> response = new HashMap<String, List<PostOut>>();

		response.put("posts", postOuts);
		System.out.println(response);
		return response;
	}

	@GetMapping("/admin/post/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, PostOut> getPostById(@PathVariable("id") long id) {
		Post post = postService.findPostById(id);
		String schedules = post.getSchedule();
		PostOut postOut = new PostOut();
		postOut = PostConverter.modelToResponse(post);
		Map<String, PostOut> response = new HashMap<String, PostOut>();
		response.put("post", postOut);
		return response;
	}

	@DeleteMapping(value = "/admin/post/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deletePost(@PathVariable("id") long id) {
		postService.admin_delete(id);
		System.out.println("Delete is successed");
	}

	@GetMapping(value = "/admin/approval/tutor")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<TutorOutput>> getApprovalTutor() {
		List<Tutor> tutors = tutorService.getTutorsByVerify(false);
		List<TutorOutput> tutorOutputs = new ArrayList<TutorOutput>();
		for (int i = 0; i < tutors.size(); i++) {
			TutorOutput tutorOutput = new TutorOutput();
			tutorOutput = TutorConverter.modelToResponse(tutors.get(i));
			/*
			 * ---- only show cmnd phonenumber for admin-------
			 */
			tutorOutput.setCmnd(tutors.get(i).getCmnd());
			tutorOutput.setPhonenumber(tutors.get(i).getUser().getPhonenumber());
			// --------------------------------------------

			tutorOutputs.add(tutorOutput);
		}
		Map<String, List<TutorOutput>> response = new HashMap<String, List<TutorOutput>>();
		response.put("tutors", tutorOutputs);
		return response;
	}

	@PutMapping(value = "/admin/approval/tutor")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, String> approvalTutor(@RequestBody ApprovalRequest request) {
		Boolean result = tutorService.approvalTutor(request.getApproval(), request.getId());

		Map<String, String> response = new HashMap<String, String>();
		String message = "";
		if (result) {
			if (request.getApproval()) {
				message = "Gia s?? ???? ???????c x??c minh !";
			} else
				message = "Gia s?? ch??a ???????c x??c minh!!!";
		} else
			message = "C?? l???i x???y ra !!!";
		response.put("result", message);
		return response;
	}

	@GetMapping(value = "/admin/approval/post")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<PostOut>> getApprovalPost() {
		List<Post> posts = postRepository.findByVerify(false);
		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < posts.size(); i++) {
			PostOut postOut = new PostOut();
			postOut = PostConverter.modelToResponse(posts.get(i));
			postOut.setPhonenumber(posts.get(i).getStudent().getUser().getPhonenumber());
			postOut.setAddress(posts.get(i).getAddress());
			postOuts.add(postOut);
		}
		Map<String, List<PostOut>> response = new HashMap<String, List<PostOut>>();

		response.put("posts", postOuts);
		return response;
	}

	// code here
	@PutMapping(value = "/admin/approval/post")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, String> approvalPost(@RequestBody ApprovalRequest request) {
		Boolean result = postService.approvalPost(request.getApproval(), request.getId());
		Map<String, String> response = new HashMap<String, String>();
		String message = "";
		if (result) {
			if (request.getApproval()) {
				message = "B??i ????ng ???? ???????c duy???t !";
			} else
				message = "B??i ????ng ch??a ???????c duy???t!!!";
		} else
			message = "C?? l???i x???y ra !!!";
		response.put("result", message);
		return response;
	}

	/*
	 * ------------------------- Manage candidate ----------------------------
	 */
	@GetMapping(value = "/admin/post/candidate")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<AcceptionResponse>> showAcception() {
		List<Post> posts = postRepository.findByStatus(PostStatus.CHOOSING);
		List<AcceptionResponse> acceptions = new ArrayList<AcceptionResponse>();
		for (int i = 0; i < posts.size(); i++) {
			AcceptionResponse acception = new AcceptionResponse();
			acception = AcceptionConverter.modelToResponse(posts.get(i));
			acceptions.add(acception);
		}
		Map<String, List<AcceptionResponse>> response = new HashMap<String, List<AcceptionResponse>>();
		response.put("acceptions", acceptions);
		return response;
	}

	/*
	 * -----------------------------------------------------------------------
	 */

	@PutMapping(value = "/admin/candidate")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, String> approvalAcception(@RequestBody ApprovalRequest request) {

		candidateService.openClass(request.getId(), request.getApproval());

		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "OK");
		return response;
	}

	@SuppressWarnings("unlikely-arg-type")
	@GetMapping(value = "/admin/user")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<UserResponse>> showUser() {
		List<User> users = userService.all();
		List<UserResponse> userOutputs = new ArrayList<UserResponse>();
		for (int i = 0; i < users.size(); i++) {
			boolean isAdmin = false;
			for (Role role : users.get(i).getRoles()) {
				if (role.getName() == ERole.ROLE_ADMIN) {
					isAdmin = true;
					break;
				}
			}
			if (isAdmin)
				continue;
			UserResponse userOutput = new UserResponse();
			userOutput = UserConverter.modelToResponse(users.get(i));
			userOutputs.add(userOutput);
		}
		Map<String, List<UserResponse>> response = new HashMap<String, List<UserResponse>>();
		response.put("users", userOutputs);
		return response;

	}

	@GetMapping("/admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, UserResponse> getUserById(@PathVariable("id") long id) {
		User user = userRepository.findOne(id);
		UserResponse userOutput = new UserResponse();
		userOutput = UserConverter.modelToResponse(user);
		Map<String, UserResponse> response = new HashMap<String, UserResponse>();
		response.put("user", userOutput);
		return response;
	}

	@DeleteMapping(value = "/admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@PathVariable("id") long id) {
		userService.admin_delete(id);
		System.out.println("Delete is successed");
	}

	@PutMapping(value = "/admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String updateUser(@RequestBody UserRequest model, @PathVariable("id") long id) {
		userService.admin_update(model, id);
		return "Update user is success";
	}

	@GetMapping(value = "/admin/subject")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<SubjectOutPut>> getSubject() {
		List<Subject> subjects = subjectRepository.findAll();
		List<SubjectOutPut> subjectOuts = new ArrayList<SubjectOutPut>();
		for(int i=0; i<subjects.size();i++) {
			SubjectOutPut subjectOut = new SubjectOutPut();
			subjectOut.setId(subjects.get(i).getId());
			subjectOut.setSubjectName(subjects.get(i).getSubjectname());
			subjectOuts.add(subjectOut);
		}
		Map<String, List<SubjectOutPut>> response = new HashMap<String, List<SubjectOutPut>>();
		response.put("subjects", subjectOuts);
		return response;
	}
	
	@PostMapping(value = "/admin/subject")
	@PreAuthorize("hasRole('ADMIN')")
	public String createSubject(@RequestBody SubjectRequest model) {
		Subject subject = new Subject();
		subject.setSubjectname(model.getSubjectName());

		subjectRepository.save(subject);
		String message = "Create subject is success !\n";
		return message;
	}

	@PutMapping(value = "/admin/subject/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String updateSubject(@RequestBody SubjectRequest model, @PathVariable("id") long id) {
		Subject subject = subjectRepository.findOne(id);
		subject.setSubjectname(model.getSubjectName());
		subjectRepository.save(subject);
		return "Update subject is success";
	}

	@DeleteMapping(value = "/admin/subject/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void delete(@PathVariable("id") long id) {
		subjectRepository.delete(id);
		System.out.println("Delete is successed");
	}


	@GetMapping(value = "/admin/grade")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<GradeOutput>> getGrade() {
		List<Grade> grades = gradeRepository.findAll();
		List<GradeOutput> gradeOuts = new ArrayList<GradeOutput>();
		for(int i=0; i<grades.size();i++) {
			GradeOutput gradeOut = new GradeOutput();
			gradeOut.setId(grades.get(i).getId());
			gradeOut.setGradeName(grades.get(i).getGradename());
			gradeOuts.add(gradeOut);
		}
		Map<String, List<GradeOutput>> response = new HashMap<String, List<GradeOutput>>();
		response.put("subjects", gradeOuts);
		return response;
	}
	

	@PostMapping(value = "/admin/grade")
	@PreAuthorize("hasRole('ADMIN')")
	public String createGrade(@RequestBody GradeRequest model) {
		Grade grade = new Grade();
		grade.setGradename(model.getGradeName());
		gradeRepository.save(grade);
		String message = "Create grade is success !\n";
		return message;
	}

	@PutMapping(value = "/admin/grade/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public String updateGrade(@RequestBody GradeRequest model, @PathVariable("id") long id) {
		Grade grade = gradeRepository.findOne(id);
		grade.setGradename(model.getGradeName());
		gradeRepository.save(grade);
		return "Update grade is success";
	}

	@DeleteMapping(value = "/admin/grade/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteGrade(@PathVariable("id") long id) {
		gradeRepository.delete(id);
		System.out.println("Delete is successed");
	}
}
