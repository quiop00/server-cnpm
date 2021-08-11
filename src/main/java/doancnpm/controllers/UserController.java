package doancnpm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer.PasswordCompareConfigurer;
import org.springframework.security.config.authentication.PasswordEncoderParser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.models.ERole;
import doancnpm.models.Role;
import doancnpm.models.User;
import doancnpm.payload.request.AddTutorRequest;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.payload.request.PasswordRequest;
import doancnpm.payload.request.UserRequest;
import doancnpm.payload.response.JwtResponse;
import doancnpm.payload.response.MessageResponse;
import doancnpm.payload.response.UserResponse;
import doancnpm.security.IUserService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	PasswordEncoder encoder;
	
	//@Autowired
	//PasswordCompareConfigurer encoderParser;
	
	
	@Autowired
	private JwtUtils jwtUtils;

	@GetMapping(value = "/user")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<User>> all() {
		List<User> user = userService.all();
		Map<String, List<User>> response = new HashMap<String, List<User>>();
		response.put("users", user);
		return response;
	}
	
//	@GetMapping(value = "/user/profile")
//	@PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
//	public UserOutPut getUser(HttpServletRequest request){
//		String jwt = parseJwt(request);
//		String username = "";
//		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//			username = jwtUtils.getUserNameFromJwtToken(jwt);
//		}
//		User user = userService.getUser(username);
//		
//		UserOutput userOutPut = new UserOutPut();
//		userOutPut.setId(user.getId());
//		userOutPut.setEmail(user.getEmail());
//		userOutPut.setPassword(user.getPassword());
//		userOutPut.setName(user.getName());
//		userOutPut.setPhonenumber(user.getPhonenumber());
//		userOutPut.setAge(user.getAge());
//		userOutPut.setGender(user.getGender());
//		userOutPut.setUsername(user.getUsername());
//		Set<Role> roles = user.getRoles();
//		List<String> rols = new ArrayList<String>(); 
//		for(Role role : roles)
//			rols.add(role.getName().name());	
//		userOutPut.setRoles(rols);
//		return userOutPut;
//	}
	
	@GetMapping("/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
		User userData = userService.getUserById(id);

		if (userData != null) {
			return new ResponseEntity<>(userData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/user")
	@PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
	public String updateUser(HttpServletRequest request, @RequestBody UserRequest model) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}

		userService.save(username, model);
		;
		return "Update user is success";
	}
	
	@DeleteMapping(value = "/user")
	@PreAuthorize("hasRole('ADMIN')")
	public void deleteUser(@RequestBody long[] ids) {
		userService.delete(ids);
	}
	
	
//	@GetMapping("/user/password")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT') or hasRole('TUTOR')")
//	public String checkPassword(HttpServletRequest request,@RequestBody PasswordRequest model) {
//		String jwt = parseJwt(request);
//		String username = "";
//		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//			username = jwtUtils.getUserNameFromJwtToken(jwt);
//		}
//		
//		String password = userService.getPassword(username);
//		
//		String message="";
//		if(encoder.matches(model.getPassword(), password)) 
//			message = "Password is right";
//		else
//			message = "Password is wrong";
//		
//		
//		return message;
//	}
	
//	@PutMapping("/user/password")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT') or hasRole('TUTOR')")
//	public String changePassword(HttpServletRequest request, @RequestBody PasswordRequest model ) {
//
//		String jwt = parseJwt(request);
//		String username = "";
//		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
//			username = jwtUtils.getUserNameFromJwtToken(jwt);
//		}
//		
//		userService.save_password(username, model);
//		return "Update password is success";
//	}
	@PutMapping("/user/password")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STUDENT') or hasRole('TUTOR')")
	public MessageResponse changePassword(HttpServletRequest request, @RequestBody PasswordRequest model ) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		String password = userService.getPassword(username);
	
		if(encoder.matches(model.getOldPassword(), password)) {
			userService.save_password(username, model);
			return new MessageResponse("Update password is success");
		}	
		else
			return new MessageResponse("Password is wrong");
		
	}
	
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
	
}
