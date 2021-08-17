package doancnpm.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doancnpm.enums.NotifyType;
import doancnpm.models.Post;
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
	public void pushNotification(Post post, User user, NotifyType type,Long endpoint) {
		
		
	}

}
