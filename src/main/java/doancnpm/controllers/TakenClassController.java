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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import doancnpm.converter.TakenClassConverter;
import doancnpm.enums.ClassStatus;
import doancnpm.enums.NotifyType;
import doancnpm.models.Notification;
import doancnpm.models.Student;
import doancnpm.models.TakenClass;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.payload.response.ClassResponse;
import doancnpm.payload.response.TakenClassResponse;
import doancnpm.repository.ClassRepository;
import doancnpm.repository.NotificationRepository;
import doancnpm.repository.StudentRepository;
import doancnpm.repository.TutorRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.INotificationService;
import doancnpm.security.jwt.JwtUtils;
import doancnpm.security.services.NotificationService;

@CrossOrigin
@RestController
public class TakenClassController {
	@Autowired
	ClassRepository classRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TutorRepository tutorRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	NotificationRepository notificationRepo;
	@Autowired
	INotificationService notificationService;
	@Autowired
	private JwtUtils jwtUtils;

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasLength(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.replace("Bearer ", "");
		}
		return null;
	}

	@GetMapping(value = "/api/myClass")
	public Map<String, List<TakenClassResponse>> getClasses(HttpServletRequest request) {
		String jwt = parseJwt(request);
		String username = "";
		if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
			username = jwtUtils.getUserNameFromJwtToken(jwt);
		}
		User user = userRepository.findOneByusername(username);

		Tutor tutor = tutorRepository.findByUser_id(user.getId());
		List<TakenClassResponse> classes = new ArrayList<TakenClassResponse>();
		List<TakenClass> takenClasses;
		if (tutor != null) {
			takenClasses = tutor.getClasses();
			if (takenClasses != null)
				for (int i = 0; i < takenClasses.size(); i++) {
					TakenClassResponse classResponse = TakenClassConverter.modelToResponse(takenClasses.get(i));
					classResponse.setName(takenClasses.get(i).getStudent().getUser().getName());
					classResponse.setPhonenumber(takenClasses.get(i).getStudent().getUser().getPhonenumber());
					classes.add(classResponse);
				}
		} else {
			Student student = studentRepository.findByUser_id(user.getId());
			takenClasses = student.getClasses();
			if (takenClasses != null)
				for (int i = 0; i < takenClasses.size(); i++) {
					TakenClassResponse classResponse = TakenClassConverter.modelToResponse(takenClasses.get(i));
					classResponse.setName(takenClasses.get(i).getTutor().getUser().getName());
					classResponse.setPhonenumber(takenClasses.get(i).getTutor().getUser().getPhonenumber());
					classes.add(classResponse);
				}
		}
		Map<String, List<TakenClassResponse>> response = new HashMap<String, List<TakenClassResponse>>();
		response.put("classes", classes);

		return response;
	}
	/*
	 * CẦN FIX : CHECK CLASS CÓ THUỘC VỀ TUTOR/STUDENT HAY KHÔNG?
	 */
	@PutMapping(value = "/api/classes/{idClass}")
	@PreAuthorize("hasRole('TUTOR') or hasRole('STUDENT')")
	public Map<String, String> finishClass(@PathVariable("idClass") Long idClass) {

		TakenClass takenClass = classRepository.findOne(idClass);
		takenClass.setStatus(ClassStatus.FINISHED);
		//notify to tutor
		notificationService.pushNotification(null,takenClass.getTutor().getUser(),NotifyType.FINISH_CLASS,(long) -1);
		//notify to student
		notificationService.pushNotification(null,takenClass.getStudent().getUser(),NotifyType.FINISH_CLASS,(long) takenClass.getTutor().getId());

		classRepository.save(takenClass);

		Map<String, String> response = new HashMap<String, String>();

		response.put("message", "Class have finished !!!");
		return response;
	}

}
