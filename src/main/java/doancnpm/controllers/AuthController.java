package doancnpm.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.ERole;
import doancnpm.models.Role;
import doancnpm.models.Student;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.LoginRequest;
import doancnpm.payload.request.SignupRequest;
import doancnpm.payload.response.JwtResponse;
import doancnpm.payload.response.MessageResponse;
import doancnpm.repository.RoleRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ITutorService;
import doancnpm.security.jwt.JwtUtils;
import doancnpm.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;
	@Autowired
	private TutorRepository tutorRepository;
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	private ITutorService tutorService;
	


	@PostMapping("/signin")
	
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		if(userDetails.getBlock())
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Tài khoản đã bị khóa"));
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
//		User user = new User(signUpRequest.getUsername(), 
//							 signUpRequest.getEmail(),
//							 encoder.encode(signUpRequest.getPassword()));
		User user = new User(signUpRequest.getUsername(),
							  signUpRequest.getEmail(),
							  signUpRequest.getPhonenumber(),
							  encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(studentRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "tutor":
					Role tutorRole = roleRepository.findByName(ERole.ROLE_TUTOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(tutorRole);
					break;
				default:
					Role studentRole = roleRepository.findByName(ERole.ROLE_STUDENT)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(studentRole);
				}
			});
		}
		
		user.setRoles(roles);	
		user.setName(signUpRequest.getUsername());
		user.setAvatar("https://storage.googleapis.com/tutor-a4d9d.appspot.com/c67a91c5-e28f-4084-af61-71f1f68ec184jpg");
		userRepository.save(user);
		if(strRoles.contains("tutor")){
			Tutor tutor = new Tutor();
			tutor.setUser(user);
			tutorRepository.save(tutor);
			//tutorService.save(addTutorRequest);
		}
		else
		if(strRoles.contains("student")) {
			Student student = new Student();
			student.setUser(user);
			studentRepository.save(student);
		}
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
