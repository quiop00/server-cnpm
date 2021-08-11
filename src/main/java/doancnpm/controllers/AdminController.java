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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import doancnpm.models.ERole;
import doancnpm.models.Post;
import doancnpm.models.Role;
import doancnpm.models.Subject;
import doancnpm.models.User;
import doancnpm.payload.request.UserRequest;
import doancnpm.payload.response.PostOut;
import doancnpm.payload.response.UserResponse;
import doancnpm.repository.PostRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.SubjectRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.IUserService;
import doancnpm.security.iPostService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin
@RestController
public class AdminController {

	@Autowired
	PostRepository postRepository;

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	private iPostService postService;
	
	

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
		List<Post> post = postService.findAll();
		List<PostOut> postOuts = new ArrayList<PostOut>();
		for (int i = 0; i < post.size(); i++) {
			String schedules = post.get(i).getSchedule();
			PostOut postOut = new PostOut();
			postOut.setId(post.get(i).getId());
			postOut.setAddress(post.get(i).getAddress());
			postOut.setGrade(post.get(i).getGrade().getGradename());
			Set<Subject> setSubjects = post.get(i).getSubjects();
			Set<String> subjects = new HashSet<String>();
			for (Subject subject : setSubjects) {
				subjects.add(subject.getSubjectname());
			}
			postOut.setSubject(subjects);
			postOut.setDescription(post.get(i).getDescription());
			postOut.setPrice(post.get(i).getPrice());
			postOut.setTitle(post.get(i).getTitle());
			postOut.setIdStudent(post.get(i).getStudent().getId());
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
	
	@GetMapping("/admin/post/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, PostOut> getPostById(@PathVariable("id") long id) {
		Post post = postService.findPostById(id);
		String schedules = post.getSchedule();
		PostOut postOut = new PostOut();
		postOut.setId(post.getId());
		postOut.setIdStudent(post.getStudent().getId());
		postOut.setAddress(post.getAddress());
		postOut.setGrade(post.getGrade().getGradename());
		Set<Subject> setSubjects = post.getSubjects();
		Set<String> subjects = new HashSet<String>();
		for (Subject subject : setSubjects) {
			subjects.add(subject.getSubjectname());
		}
		postOut.setSubject(subjects);
		postOut.setDescription(post.getDescription());
		postOut.setPrice(post.getPrice());
		postOut.setTitle(post.getTitle());

		try {
			Map<String, Boolean> schedule = new ObjectMapper().readValue(schedules, HashMap.class);
			System.out.println(schedule);
			postOut.setSchedules(schedule);
		} catch (IOException e) {

			e.printStackTrace();
		}
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
	
	@GetMapping(value = "/admin/user")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, List<UserResponse>> showUser() {
		List<User> user = userService.all();
		List<UserResponse> userOutputs = new ArrayList<UserResponse>();
		for (int i = 0; i < user.size(); i++) {
			UserResponse userOutput = new UserResponse();
			userOutput.setId(user.get(i).getId());
			userOutput.setUsername(user.get(i).getUsername());
			userOutput.setPassword(user.get(i).getPassword());
			userOutput.setEmail(user.get(i).getEmail());
			userOutput.setPhonenumber(user.get(i).getPhonenumber());
			userOutput.setAge(user.get(i).getAge());
			userOutput.setName(user.get(i).getName());
			userOutput.setGender(user.get(i).getGender());
			Set<Role> setRoles = user.get(i).getRoles();
			Set<ERole> roles = new HashSet<ERole>();
			for (Role role : setRoles) {
				roles.add(role.getName());
			}
			userOutput.setRoles(roles);
			
			userOutputs.add(userOutput);
		}
		Map<String, List<UserResponse>> response = new HashMap<String, List<UserResponse>>();
		response.put("user", userOutputs);
		return response;

	}
	
	
	@GetMapping("/admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Map<String, UserResponse> getUserById(@PathVariable("id") long id) {
		User user = userRepository.findOne(id);
		UserResponse userOutput = new UserResponse();
		userOutput.setId(user.getId());
		userOutput.setUsername(user.getUsername());
		userOutput.setPassword(user.getPassword());
		userOutput.setEmail(user.getEmail());
		userOutput.setPhonenumber(user.getPhonenumber());
		userOutput.setAge(user.getAge());
		userOutput.setGender(user.getGender());
		userOutput.setName(user.getName());
		Set<Role> setRoles = user.getRoles();
		Set<ERole> roles = new HashSet<ERole>();
		for (Role role : setRoles) {
			roles.add(role.getName());
		}
		userOutput.setRoles(roles);

		Map<String, UserResponse> response = new HashMap<String, UserResponse>();
		response.put("user",userOutput);
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
	
}
