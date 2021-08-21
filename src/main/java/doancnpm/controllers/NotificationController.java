package doancnpm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.converter.NotificationConverter;
import doancnpm.enums.NotifyType;
import doancnpm.models.ERole;
import doancnpm.models.Notification;
import doancnpm.models.Post;
import doancnpm.models.Role;
import doancnpm.models.User;
import doancnpm.payload.request.NotificationRequest;
import doancnpm.payload.request.PostRequest;
import doancnpm.payload.response.NotifyResponse;
import doancnpm.repository.PostRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.ICommentService;
import doancnpm.security.INotificationService;
import doancnpm.security.jwt.JwtUtils;

@CrossOrigin
@RestController
public class NotificationController {
	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	private INotificationService noticationService;
	
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}
	@PostMapping(value = "/api/notifications")
	@PreAuthorize("hasRole('ADMIN') or hasRole('TUTOR')")
	public String createNotifications(HttpServletRequest request, @RequestBody NotificationRequest model) {

		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		
		User user = userRepository.findOneByusername(username);
		Post post = postRepository.findOne(model.getIdPost());
		NotifyType type = model.getNotifyType();
		Long endpoint = model.getEndpoint();
		noticationService.pushNotification(post, user, type, endpoint);
		String message = "Create Post is success !\n";
		return message;
	}
	
	@GetMapping(value = "/api/notifications")
	private Map<String, List<NotifyResponse>> getNotifications(HttpServletRequest request){
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);
		
		List<Notification> notifications = user.getNotifications();
		List<NotifyResponse> outs = new ArrayList<NotifyResponse>();
		ERole userRole = null;
		for(Role role:user.getRoles()) {
			userRole = role.getName();
			break;
		}
		if(notifications!=null) {
			for(Notification notification:notifications) {
				NotifyResponse out = NotificationConverter.modelToResponse(notification,userRole);
				outs.add(out);
			}
		}
		Map<String,List<NotifyResponse>> response = new HashMap<String, List<NotifyResponse>>();
		response.put("notificatons", outs);
		return response;
	}
}
