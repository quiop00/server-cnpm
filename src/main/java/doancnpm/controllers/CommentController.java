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

import doancnpm.models.Comment;
import doancnpm.models.Invitation;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.CommentRequest;
import doancnpm.payload.request.InvitationRequest;
import doancnpm.payload.response.CommentResponse;
import doancnpm.payload.response.InvitationResponse;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ICommentService;
import doancnpm.security.jwt.JwtUtils;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

public class CommentController {
	
	@Autowired
	ICommentService commentService;

	@Autowired
	UserRepository userRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping(value = "/api/comment")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
	public Map<String, List<CommentResponse>> getCommentByIdTutor(HttpServletRequest request) {
		
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		
		User user = userRepository.findOneByusername(username);
		Tutor tutor = tutorRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Tutor Not Found"));
		
		List<Comment> comments = commentService.findByIdTutor(tutor.getId());
		
		List<CommentResponse> commentResponses = new ArrayList<CommentResponse>(); 
				
		for(int i=0;i < comments.size();i++) {
			CommentResponse commentResponse = new CommentResponse();
			commentResponse.setId(comments.get(i).getId());
			commentResponse.setIdTutor(comments.get(i).getTutor().getId());
			commentResponse.setIdStudent(comments.get(i).getStudent().getId());
			commentResponse.setNameStudent(comments.get(i).getStudent().getUser().getName());
			commentResponse.setContent(comments.get(i).getContent());
			commentResponses.add(commentResponse);
		}
		
		Map<String, List<CommentResponse>> response = new HashMap<String, List<CommentResponse>>();
		response.put("comments", commentResponses);
		return response;
	}
	
	@PostMapping(value = "/api/comment")
	@PreAuthorize("hasRole('STUDENT')")
	public void createComment(HttpServletRequest request, @RequestBody CommentRequest model) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		commentService.save(username, model);
	}
	
	
	@GetMapping(value = "/comment")
	public Map<String, List<CommentResponse>> all(){
		//List<Comment> messages = messageService.all();
		List<Comment> comments = commentService.findAll();
		List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
		for(int i=0;i<comments.size();i++) {
			CommentResponse commentResponse = new CommentResponse();
			commentResponse.setId(comments.get(i).getId());
			commentResponse.setContent(comments.get(i).getContent());
			commentResponse.setIdStudent(comments.get(i).getStudent().getId());
			commentResponse.setNameStudent(comments.get(i).getStudent().getUser().getName());
			commentResponse.setIdTutor(comments.get(i).getTutor().getId());
			commentResponses.add(commentResponse);
		}
		Map<String,List<CommentResponse>> response = new HashMap<String, List<CommentResponse>>();
		response.put("comments", commentResponses);
		return response;
	}
	
//	@GetMapping("/message/{id}")
//	 public ResponseEntity<Comment> getMessageById(@PathVariable("id") long id) {
//	    //User userData = userService.getUserById(id);
//	    Comment message = messageService.getMessageById(id);
//
//	    if (message != null) {
//	      return new ResponseEntity<>(message, HttpStatus.OK);
//	    } else {
//	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	    }
//	  }
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
}
