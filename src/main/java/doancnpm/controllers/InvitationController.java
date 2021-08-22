package doancnpm.controllers;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.enums.NotifyType;

import doancnpm.models.Post;
import doancnpm.models.Student;
import doancnpm.models.Tutor;
import doancnpm.models.User;

import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.INotificationService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

public class InvitationController {

	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	INotificationService notificationService;

	@PostMapping(value = "/api/invite")
	@PreAuthorize("hasRole('STUDENT')")
	public Map<String, String> createInvitation(HttpServletRequest request, @RequestParam long idTutor,
			@RequestParam long idPost) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);

		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student not found"));
		Tutor tutor = tutorRepository.findOne(idTutor);

		Post post = postRepository.findOne(idPost);

		notificationService.pushNotification(post, tutor.getUser(), NotifyType.STUDENT_INVITATION, idPost);

		Map<String, String> response = new HashMap<String, String>();
		response.put("message", "Mời thành công");
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
