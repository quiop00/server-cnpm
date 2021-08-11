package doancnpm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import doancnpm.models.Invitation;
import doancnpm.models.Student;
import doancnpm.models.Suggestion;
import doancnpm.models.User;
import doancnpm.payload.request.SuggestionRequest;
import doancnpm.payload.response.InvitationResponse;
import doancnpm.payload.response.SuggestionResponse;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.iSuggestionService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin
@RestController
public class SuggestionController {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private iSuggestionService suggestionService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StudentRepository studentRepository;
	
	@GetMapping(value = "/suggestion")
	public Map<String, List<SuggestionResponse>> all(){
		
		
		List<Suggestion> suggestions = suggestionService.findAll();
		List<SuggestionResponse> suggestionResponses = new ArrayList<SuggestionResponse>();
		for(int i=0;i<suggestions.size();i++) {
			SuggestionResponse suggestionResponse = new SuggestionResponse();
			suggestionResponse.setId(suggestions.get(i).getId());
			suggestionResponse.setIdPost(suggestions.get(i).getPost().getId());
			suggestionResponse.setTitlePost(suggestions.get(i).getPost().getTitle());
			suggestionResponse.setStatus(suggestions.get(i).getStatus());
			suggestionResponse.setNameTutor(suggestions.get(i).getTutor().getUser().getName());
			suggestionResponse.setPhoneNumberTutor(suggestions.get(i).getTutor().getUser().getPhonenumber());
			suggestionResponse.setNameStudent(suggestions.get(i).getStudent().getUser().getName());
			suggestionResponse.setPhoneNumberStudent(suggestions.get(i).getStudent().getUser().getPhonenumber());
			suggestionResponse.setAvatar(suggestions.get(i).getTutor().getAvatar());
			suggestionResponse.setIdStudent(suggestions.get(i).getStudent().getId());
			suggestionResponse.setIdTutor(suggestions.get(i).getTutor().getId());
			
			suggestionResponses.add(suggestionResponse);
		}
		
		Map<String,List<SuggestionResponse>> response = new HashMap<String, List<SuggestionResponse>>();
		response.put("suggestions", suggestionResponses);
		return response;
	}
	
	
	@PostMapping(value = "/api/suggestion")
	@PreAuthorize("hasRole('TUTOR')")
	public void createSuggestion(HttpServletRequest request, @RequestParam long idStudent, @RequestParam long idPost) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		suggestionService.save(username, idPost, idStudent);
	}
	
	@PutMapping(value = "/api/suggestion/acceptance")
	@PreAuthorize("hasRole('STUDENT')")
	public void acceptSuggestion(HttpServletRequest request, @RequestParam long idPost, @RequestParam long idTutor) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		
		suggestionService.accept(username, idPost, idTutor);
	}
	
	@PutMapping(value = "/api/suggestion/denial")
	@PreAuthorize("hasRole('STUDENT')")
	public void denySuggestion(HttpServletRequest request, @RequestParam long idPost, @RequestParam long idTutor) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		
		suggestionService.reject(username, idPost, idTutor);
	}
	
	@DeleteMapping(value = "/api/suggestion/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT')")
	public void deleteSuggestion(HttpServletRequest request, @PathVariable("id") long id) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		suggestionService.delete(username, id);
		System.out.println("Delete is successed");
	}

	
	@GetMapping(value = "/api/suggestion")
	@PreAuthorize("hasRole('STUDENT')")
	public Map<String, List<SuggestionResponse>> getSuggestionByIdStudent(HttpServletRequest request) {
		
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);
		Student student = studentRepository.findByuser_id(user.getId())
				.orElseThrow(() -> new UsernameNotFoundException("Student Not Found"));
		List<Suggestion> suggestions = suggestionService.findByIdStudent(student.getId());
		
		List<SuggestionResponse> suggestionResponses = new ArrayList<SuggestionResponse>();
		for (int i = 0; i < suggestions.size(); i++) {
			SuggestionResponse suggestionResponse = new SuggestionResponse();
			suggestionResponse.setId(suggestions.get(i).getId());
			suggestionResponse.setIdStudent(suggestions.get(i).getStudent().getId());
			suggestionResponse.setIdTutor(suggestions.get(i).getTutor().getId());
			suggestionResponse.setNameTutor(suggestions.get(i).getTutor().getUser().getName());
			suggestionResponse.setPhoneNumberTutor(suggestions.get(i).getTutor().getUser().getPhonenumber());
			suggestionResponse.setNameStudent(suggestions.get(i).getStudent().getUser().getName());
			suggestionResponse.setPhoneNumberStudent(suggestions.get(i).getStudent().getUser().getPhonenumber());
			suggestionResponse.setAvatar(suggestions.get(i).getTutor().getAvatar());
			suggestionResponse.setIdPost(suggestions.get(i).getPost().getId());
			suggestionResponse.setTitlePost(suggestions.get(i).getPost().getTitle());
			suggestionResponse.setStatus(suggestions.get(i).getStatus());
			suggestionResponses.add(suggestionResponse);
		}
		Map<String, List<SuggestionResponse>> response = new HashMap<String, List<SuggestionResponse>>();
		response.put("suggestions", suggestionResponses);
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
