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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.Comment;
import doancnpm.models.Invitation;
import doancnpm.models.Student;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.InvitationRequest;
import doancnpm.payload.response.CommentResponse;
import doancnpm.payload.response.InvitationResponse;
import doancnpm.payload.response.MessageResponse;
import doancnpm.payload.response.TutorOutput;
import doancnpm.repository.InvitationRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IInvitationService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

public class InvitationController {
	
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	InvitationRepository invitationRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private IInvitationService invitationService;
	
	@GetMapping(value = "/invitation")
	public Map<String, List<InvitationResponse>> all(){	
		List<Invitation> invitations = invitationService.findAll();	
		List<InvitationResponse> invitationResponses = new ArrayList<InvitationResponse>();
		for(int i=0;i<invitations.size();i++) {
			InvitationResponse invitationResponse = new InvitationResponse();
			invitationResponse.setId(invitations.get(i).getId());
			invitationResponse.setStatus(invitations.get(i).getStatus());
			invitationResponse.setIdStudent(invitations.get(i).getStudent().getId());
			invitationResponse.setPhoneNumberStudent(invitations.get(i).getStudent().getUser().getPhonenumber());
			invitationResponse.setNameStudent(invitations.get(i).getStudent().getUser().getName());
			invitationResponse.setIdTutor(invitations.get(i).getTutor().getId());
			invitationResponse.setNameTutor(invitations.get(i).getTutor().getUser().getName());
			invitationResponse.setPhoneNumberTutor(invitations.get(i).getTutor().getUser().getPhonenumber());
			
			invitationResponses.add(invitationResponse);
		}
		
		
		Map<String,List<InvitationResponse>> response = new HashMap<String, List<InvitationResponse>>();
		response.put("invitations", invitationResponses);
		return response;
	}
	
	@GetMapping(value = "/api/invitation")
	@PreAuthorize("hasRole('TUTOR')")
	public Map<String, List<InvitationResponse>> getInvitationByIdTutor(HttpServletRequest request) {
		
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);
		Tutor tutor = tutorRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Tutor Not Found"));
		List<Invitation> invitations = invitationService.findByIdTutor(tutor.getId());
		
		List<InvitationResponse> invitationResponses = new ArrayList<InvitationResponse>();
		for (int i = 0; i < invitations.size(); i++) {
			InvitationResponse invitationResponse = new InvitationResponse();
			invitationResponse.setId(invitations.get(i).getId());
			invitationResponse.setIdStudent(invitations.get(i).getStudent().getId());
			invitationResponse.setNameStudent(invitations.get(i).getStudent().getUser().getName());
			invitationResponse.setPhoneNumberStudent(invitations.get(i).getStudent().getUser().getPhonenumber());
			invitationResponse.setIdTutor(invitations.get(i).getTutor().getId());
			invitationResponse.setStatus(invitations.get(i).getStatus());
			invitationResponse.setNameTutor(invitations.get(i).getTutor().getUser().getName());
			invitationResponse.setPhoneNumberTutor(invitations.get(i).getTutor().getUser().getPhonenumber());
			
			invitationResponses.add(invitationResponse);
		}
		Map<String, List<InvitationResponse>> response = new HashMap<String, List<InvitationResponse>>();
		response.put("invitations", invitationResponses);
		return response;
	}
	
	@PostMapping(value = "/api/invitation")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<?> createInvitation(HttpServletRequest request, @RequestParam long idTutor) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);
		
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student not found"));
		Tutor tutor = tutorRepository.findOne(idTutor);
		List<Invitation> invitations = invitationRepository.findAll();
		int i=0;
		while(i<invitations.size()) {
			if(invitations.get(i).getStudent().equals(student) && invitations.get(i).getTutor().equals(tutor)) {
					return ResponseEntity
						.badRequest()
						.body(new MessageResponse("Error: Student can't invite this tutor again!"));
			}		
			i++;	
		}	
		
		invitationService.save(username, idTutor);
		return ResponseEntity.ok(new MessageResponse("Invite successful!"));
	}
	
	@PutMapping(value = "/api/invitation/acceptance")
	@PreAuthorize("hasRole('TUTOR')")
	public void acceptInvitation(HttpServletRequest request,  @RequestParam long idStudent) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		
		invitationService.accept(username, idStudent);
	}
	
	@PutMapping(value = "/api/invitation/denial")
	@PreAuthorize("hasRole('TUTOR')")
	public void rejectInvitation(HttpServletRequest request, @RequestParam long idStudent) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		
		invitationService.reject(username, idStudent);
	}
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
}
