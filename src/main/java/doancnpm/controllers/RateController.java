package doancnpm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.Invitation;
import doancnpm.models.Student;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.response.InvitationResponse;
import doancnpm.payload.response.MessageResponse;
import doancnpm.payload.response.RateResponse;
import doancnpm.repository.InvitationRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IRateService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class RateController {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private IRateService rateService;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@PostMapping(value = "/api/rating")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR') or hasRole('STUDENT')")
	public ResponseEntity<?> createRating(HttpServletRequest request, @RequestParam long idTutor,@RequestParam int star) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		rateService.save(username, idTutor, star);
		return ResponseEntity.ok(new MessageResponse("rate successful!"));
	}
	@GetMapping(value = "/rating")
	public RateResponse getStar(@RequestParam long idTutor){	
		float star = rateService.getStar(idTutor);
		RateResponse rate = new RateResponse();
		rate.setStar(star);
		return rate;
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
}
