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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.converter.CommentConverter;
import doancnpm.models.Comment;
import doancnpm.models.Invitation;
import doancnpm.models.Student;
import doancnpm.models.TakenClass;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.CommentRequest;
import doancnpm.payload.request.InvitationRequest;
import doancnpm.payload.response.CommentOutput;
import doancnpm.payload.response.CommentResponse;
import doancnpm.payload.response.InvitationResponse;
import doancnpm.repository.ClassRepository;
import doancnpm.repository.CommentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ICommentService;
import doancnpm.security.IStudentService;
import doancnpm.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

public class CommentController {
	
	@Autowired
	ICommentService commentService;

	@Autowired
	UserRepository userRepository;
	@Autowired
	private IStudentService studentService;
	@Autowired
	ClassRepository classRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private JwtUtils jwtUtils;
	
	/*
	 * ---------------------------------------Comment and Rating-------------------------------------------------
	 */
	@PostMapping(value = "/api/student/rate")
	@PreAuthorize("hasRole('STUDENT')")
	public Map<String, String> rate(HttpServletRequest request, @RequestBody Long idClass, @RequestBody Long rate,
			@RequestBody String content) {
		TakenClass finishedClass = classRepository.findOne(idClass);

		Tutor tutor = finishedClass.getTutor();

		List<Comment> comments = tutor.getComments();
		if (comments == null)
			comments = new ArrayList<Comment>();
		Comment comment = new Comment();
		comment.setStar(rate);
		comment.setContent(content);
		comment.setStudent(finishedClass.getStudent());

		tutor.setComments(comments);

		tutorRepository.save(tutor);

		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "Đánh giá thành công!");
		return response;
	}
	
	@PostMapping(value = "/api/comment")
	@PreAuthorize("hasRole('STUDENT')")
	public Map<String,CommentOutput> comment(HttpServletRequest request, @RequestBody CommentRequest cm){
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		Student student = studentService.findStudent(username);
		Tutor tutor = tutorRepository.findOne(cm.getIdTutor());
		
		Comment comment = new Comment();
		comment.setContent(cm.getContent());
		comment.setStudent(student);
		comment.setTutor(tutor);
		
		comment=commentRepository.save(comment);
		
		System.out.println(comment.getId()+" helo "+comment.getCreatedDate());
		
		CommentOutput out = CommentConverter.modelToResponse(comment);
		
		Map<String,CommentOutput> response = new HashMap<String, CommentOutput>();
		response.put("comment",out);
		return response;
	}
	@GetMapping(value = "/api/comment/{idTutor}")
	public Map<String,List<CommentOutput>> getComments(@PathVariable("idTutor") Long idTutor){
		Tutor tutor = tutorRepository.findOne(idTutor);
		List<Comment> comments = tutor.getComments();
		List<CommentOutput> outs = new ArrayList<CommentOutput>();
		if(comments != null) {
			System.out.println(comments.size()+"herer");
			for(int i = 0;i<comments.size();i++) {
				CommentOutput out = CommentConverter.modelToResponse(comments.get(i));
				
				outs.add(out);
			}
		}
		Map<String,List<CommentOutput>> response = new HashMap<String, List<CommentOutput>>();
		
		response.put("comments", outs);
		return response;
	}
	/*
	 * ---------------------------------------------------------------------------------------------------
	 */
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
}
