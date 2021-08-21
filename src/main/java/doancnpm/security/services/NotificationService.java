package doancnpm.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import doancnpm.enums.NotifyType;
import doancnpm.models.Comment;
import doancnpm.models.Notification;
import doancnpm.models.Post;
import doancnpm.models.Student;
import doancnpm.models.Tutor;
import doancnpm.models.User;
import doancnpm.repository.NotificationRepository;
import doancnpm.repository.UserRepository;
import doancnpm.security.INotificationService;

@Service
public class NotificationService implements INotificationService{
	@Autowired 
	UserRepository userRepository;
	@Autowired
	NotificationRepository notificationRepo;
	@Override
	public void pushNotification(Post post, User user, NotifyType type, Long endpoint) {
		Notification notification = new Notification();
		notification.setPost(post);
		notification.setUser(user);
		notification.setNotifyType(type);
		notification.setEndpoint(endpoint);
		notificationRepo.save(notification);	
	}
	
	
//	@Override
//	public void pushNotification(Long post_id, User user,NotifyType type,Long endpoint) {
//		Notification notification = new Notification();
//		notification.
//		User user = userRepository.findOneByusername(username);
//		
//		Student student = studentRepository.findByuser_id(user.getId())
//				.orElseThrow(() -> new UsernameNotFoundException("Student not found"));
//				
//		Tutor tutor = tutorRepository.findOne(commentRequest.getIdTutor());
//		Comment comment = new Comment();
//		
//		comment = commentConverter.toComment(commentRequest);
//		
//		comment.setStudent(student);
//		comment.setTutor(tutor);
//		
//		commentRepository.save(comment);
//	}

}
