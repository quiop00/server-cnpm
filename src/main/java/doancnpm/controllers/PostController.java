package doancnpm.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.converter.PostConverter;
import doancnpm.enums.CandidateStatus;
import doancnpm.enums.NotifyType;
import doancnpm.enums.PostStatus;
import doancnpm.models.Admin;
import doancnpm.models.Candidate;
import doancnpm.models.Grade;
import doancnpm.models.Notification;
import doancnpm.models.Post;
import doancnpm.models.Student;
import doancnpm.models.Subject;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.PostRequest;
import doancnpm.payload.response.PostOut;
import doancnpm.repository.AdminRepository;
import doancnpm.repository.CandidateRepository;
import doancnpm.repository.GradeRepository;
import doancnpm.repository.NotificationRepository;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ITutorService;
import doancnpm.security.iPostService;
import doancnpm.security.jwt.JwtUtils;
import doancnpm.security.services.CandidateService;

@CrossOrigin
@RestController
public class PostController {
	@Autowired
	PostRepository postRepository;

	@Autowired
	SubjectRepository subjectRepository;
	@Autowired
	AdminRepository adminRepository;
	@Autowired
	CandidateService candidateService;
	@Autowired
	CandidateRepository candidateRepository;
	@Autowired
	NotificationRepository notificationRepo;
	
	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	private iPostService postService;
	@Autowired
	private ITutorService tutorService;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping(value = "/api/post")
	@PreAuthorize("hasRole('STUDENT')")
	public String createPost(HttpServletRequest request, @RequestBody PostRequest model) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		postService.saveCreate(username, model);
		String message = "Create Post is success !\n";
		return message;
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}

	@GetMapping(value = "/api/post")
	@PreAuthorize("hasRole('STUDENT')")
	public Map<String, List<PostOut>> getPostByIdStudent(HttpServletRequest request) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));
		List<Post> posts = postService.findByIdStudent(student.getId());

		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < posts.size(); i++) {
			PostOut postOut = new PostOut();
			postOut = PostConverter.modelToResponse(posts.get(i));
			postOuts.add(postOut);
		}

		Map<String, List<PostOut>> response = new HashMap<String, List<PostOut>>();
		response.put("posts", postOuts);
		return response;
	}

	@PutMapping(value = "/api/post/{id}")
	@PreAuthorize("hasRole('STUDENT')")
	public String updatePost(HttpServletRequest request, @RequestBody PostRequest model, @PathVariable("id") long id) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		postService.saveUpdate(username, model, id);
		String message = "Update Post is success !\n";
		return message;
	}

	@DeleteMapping(value = "/api/post/{id}")
	@PreAuthorize("hasRole('STUDENT')")
	public void deletePost(HttpServletRequest request, @PathVariable("id") long id) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		postService.delete(username, id);
		System.out.println("Delete is successed");
	}

	@GetMapping("/post/{id}")
	public Map<String, PostOut> getPostById(@PathVariable("id") long id) {
		Post post = postService.findPostById(id);
		PostOut postOut = new PostOut();
		postOut = PostConverter.modelToResponse(post);
		Map<String, PostOut> response = new HashMap<String, PostOut>();
		response.put("post", postOut);
		return response;
	}

	@GetMapping(value = "/post")
	public Map<String, List<PostOut>> showPost() {
		List<Post> posts = postRepository.findByVerify(true);
		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < posts.size(); i++) {
			PostOut postOut = new PostOut();
			postOut = PostConverter.modelToResponse(posts.get(i));
			postOuts.add(postOut);
		}
		Map<String, List<PostOut>> response = new HashMap<String, List<PostOut>>();
		response.put("posts", postOuts);
		return response;
	}

	/*
	 * ------------- Apply post + cancelApply ----------------
	 */
	@PutMapping(value = "/api/post/apply")
	@PreAuthorize("hasRole('TUTOR')")
	public Map<String, String> apply(HttpServletRequest request, @RequestParam Long idPost) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		Tutor tutor = tutorService.findTutor(username);
		Post post = postService.findPostById(idPost);
		Map<String, String> response = new HashMap<String, String>();
		if (post != null) {
			Set<Candidate> candidates = post.getCandidates();
			if (candidates == null) {
				candidates = new HashSet<Candidate>();
			}
			Candidate candidate = new Candidate();
			candidate.setPost(post);
			candidate.setStatus(CandidateStatus.APPLIED);
			candidate.setTutor(tutor);
			candidates.add(candidate);
//			candidateRepository.save(candidate);
			post.setCandidates(candidates);
			postRepository.save(post);
			response.put("message", "Apply thành công!");
		} else
			response.put("message", "Có lỗi xảy ra !!!");

		return response;
	}

	@PutMapping(value = "/api/post/cancelApply")
	@PreAuthorize("hasRole('TUTOR')")
	public Map<String, String> cancel(HttpServletRequest request, @RequestParam Long idPost) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		Tutor tutor = tutorService.findTutor(username);
		Post post = postService.findPostById(idPost);
		Map<String, String> response = new HashMap<String, String>();
		if (post != null) {
			Set<Candidate> candidates = post.getCandidates();
			if (candidates != null) {
				candidates.removeIf(e -> e.getTutor().getId() == tutor.getId());
			} else {
				candidates = new HashSet<Candidate>();
			}
			post.setCandidates(candidates);
			postRepository.save(post);
			response.put("message", "Hủy apply thành công!");
		} else
			response.put("message", "Có lỗi xảy ra !!!");

		return response;
	}

	// ----------------------------------------------

	/*
	 * ---------------ACCEPT CANDIDATE---------------
	 */
	
	@PutMapping(value = "/api/post/accept")
	@PreAuthorize("hasRole('STUDENT')")
	public Map<String,String> acceptCandidate(HttpServletRequest request,@RequestParam Long postId,@RequestParam Long tutorId){
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);

		Student student = studentRepository.findOne(user.getId());
		
		Post post = postRepository.findOne(postId);
		
		Candidate candidate = candidateService.findByIdTutor(post.getCandidates(), tutorId);
		
		Map<String,String> response = new HashMap<String,String>();
		if(candidate !=null) {
			candidate.setStatus(CandidateStatus.WAITING);
			post.setStatus(PostStatus.CHOOSING);
			candidateRepository.save(candidate);
			Notification toTutor = new Notification(candidate.getTutor().getUser(), post,NotifyType.STUDENT_ACCEPT,post.getId());
			notificationRepo.save(toTutor);
			List<Admin> admins = adminRepository.findAll();
			System.out.println("herer "+admins.size());
			if(admins != null)
				for(Admin admin: admins) {
					Notification toAdmin = new Notification(admin.getUser(), post,NotifyType.STUDENT_ACCEPT,post.getId());
					notificationRepo.save(toAdmin);
				}
			
			postRepository.save(post);
			response.put("message", "Đã đồng ý gia sư "+ candidate.getTutor().getUser().getName() +". Vui lòng chờ thông tin!" );
		}
		else
			response.put("message","Có lỗi xảy ra");	
		return response;
	}
	/*
	 * ----------------------------------------------
	 */
	
	@GetMapping(value = "/post/recommendation")
	@PreAuthorize("hasRole('TUTOR')")
	public Map<String, List<PostOut>> recommendPost(HttpServletRequest request) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		Tutor tutor = tutorService.findTutor(username);

		List<Post> post = postRepository.findByVerify(true);
		List<Post> post1 = new ArrayList<Post>();
		List<Post> post2 = new ArrayList<Post>();
		List<Post> post3 = new ArrayList<Post>();

		for (int i = 0; i < post.size(); i++)
			if (tutor.getSubjects().containsAll(post.get(i).getSubjects()) == true
					&& tutor.getGrades().contains(post.get(i).getGrade()) == true)
				post1.add(post.get(i));
			else if ((tutor.getSubjects().containsAll(post.get(i).getSubjects()) == true
					&& tutor.getGrades().contains(post.get(i).getGrade()) == false)
					|| (tutor.getSubjects().containsAll(post.get(i).getSubjects()) == false
							&& tutor.getGrades().contains(post.get(i).getGrade()) == true))
				post2.add(post.get(i));
			else
				post3.add(post.get(i));

		List<Post> recommendPost = new ArrayList<Post>();
		for (int i = 0; i < post1.size(); i++)
			recommendPost.add(post1.get(i));
		for (int j = 0; j < post2.size(); j++)
			recommendPost.add(post2.get(j));
		for (int k = 0; k < post3.size(); k++)
			recommendPost.add(post3.get(k));

		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < recommendPost.size(); i++) {
			String schedules = recommendPost.get(i).getSchedule();
			PostOut postOut = new PostOut();
			postOut.setId(recommendPost.get(i).getId());
			postOut.setAddress(recommendPost.get(i).getAddress());
			if (recommendPost.get(i).getGrade() != null)
				postOut.setGrade(recommendPost.get(i).getGrade().getGradename());
			else
				postOut.setGrade("");
			Set<Subject> setSubjects = recommendPost.get(i).getSubjects();
			Set<String> subjects = new HashSet<String>();
			for (Subject subject : setSubjects) {
				subjects.add(subject.getSubjectname());
			}
			postOut.setSubject(subjects);
			postOut.setDescription(recommendPost.get(i).getDescription());
			postOut.setPrice(recommendPost.get(i).getPrice());
			postOut.setTitle(recommendPost.get(i).getTitle());
			postOut.setIdStudent(recommendPost.get(i).getStudent().getId());
			try {
				Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
				System.out.println(schedule);
				postOut.setSchedules(schedule);
			} catch (IOException e) {

				e.printStackTrace();
			}
			postOuts.add(postOut);
		}
		Map<String, List<PostOut>> response = new HashMap<String, List<PostOut>>();
		response.put("post", postOuts);
		return response;
	}

	@GetMapping("/post/search")
	public Map<String, List<PostOut>> searchPost(@RequestParam(required = false) String grade, String address,
			String subject) {
		List<Post> posts = new ArrayList<Post>();
		// Pageable paging = new PageRequest(page, size);
		Grade lop = gradeRepository.findBygradename(grade);
		// .orElseThrow(() -> new RuntimeException("Error: Grade is not found."));
		int grade_id = 0;
		if (lop != null) {
			grade_id = lop.getId();
		}
		List<Subject> subjects = new ArrayList<Subject>();

		Subject mon = subjectRepository.findBysubjectname(subject);
//				.orElseThrow(() -> new RuntimeException("Error: Subject is not found."));

		subjects.add(mon);

		// List<Post> pageTuts = null;
		// List<Post> post = new ArrayList<Post>();

//		System.out.println("ok");
//		 post =  postRepository.findBySubjects(subjects);
//		 for(int i=0;i<post.size();i++)
//			 System.out.println(post.get(i).getTitle());

		if (grade == null && address == null && subject == null)
			posts = postRepository.findAll();

		else if (subject != null && grade == null && address == null) {
			posts = postRepository.findBySubjects(subjects);

		} else if (grade != null && address == null && subject == null)
			posts = postRepository.findByGrade_id(grade_id);

		else if (subject == null && grade == null && address != null)
			posts = postRepository.findByAddress(address);

		else if (subject == null && grade != null && address != null)
			posts = postRepository.findByGrade_idInAndAddressIn(grade_id, address);

		else if (subject != null && grade != null && address == null)
			posts = postRepository.findBySubjectsInAndGrade_idIn(subjects, grade_id);

		else if (subject != null && grade == null && address != null)
			posts = postRepository.findBySubjectsInAndAddressIn(subjects, address);

		else
			posts = postRepository.findBySubjectsInAndGrade_idInAndAddressIn(subjects, grade_id, address);

		// posts = pageTuts.getContent();
		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < posts.size(); i++) {
			String schedules = posts.get(i).getSchedule();
			PostOut postOut = new PostOut();
			postOut.setId(posts.get(i).getId());
			postOut.setAddress(posts.get(i).getAddress());
			if (posts.get(i).getGrade() != null)
				postOut.setGrade(posts.get(i).getGrade().getGradename());
			else
				postOut.setGrade("");
			Set<Subject> setSubjects = posts.get(i).getSubjects();
			Set<String> subjects1 = new HashSet<String>();
			for (Subject subject1 : setSubjects) {
				subjects1.add(subject1.getSubjectname());
			}
			postOut.setSubject(subjects1);
			postOut.setDescription(posts.get(i).getDescription());
			postOut.setPrice(posts.get(i).getPrice());
			postOut.setTitle(posts.get(i).getTitle());
			postOut.setIdStudent(posts.get(i).getStudent().getId());
			try {
				Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
				System.out.println(schedule);
				postOut.setSchedules(schedule);
			} catch (IOException e) {

				e.printStackTrace();
			}
			postOuts.add(postOut);
		}
		Map<String, List<PostOut>> response = new HashMap<String, List<PostOut>>();
		response.put("post", postOuts);
		return response;

	}

}