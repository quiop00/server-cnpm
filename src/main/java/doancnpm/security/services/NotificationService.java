package doancnpm.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import doancnpm.enums.NotifyType;

import doancnpm.models.ERole;
import doancnpm.models.Notification;
import doancnpm.models.Post;
import doancnpm.models.Role;

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
		Notification notification = new Notification();
		notification.setEndpoint(endpoint);
		notification.setUser(user);
		notification.setNotifyType(type);
		ERole userRole = null;
		for (Role role : user.getRoles()) {
			userRole = role.getName();
			break;
		}
		notification.setContent(setContent(post, type, userRole));
		
		notificationRepo.save(notification);
		
	}
	public static String setContent(Post post,NotifyType type,ERole role) {
		String content ="";
		switch(type) {
			case STUDENT_INVITATION:{
				content = "Từ hệ thống: Tác giả của bài đăng '"+post.getTitle()+ "' đã mời bạn làm gia sư.";
				break;
			}
			case REQUEST_OPEN_CLASS:{
				
				content = "Từ hệ thống: Tác giả của bài đăng #" +post.getId() + " đã chấp nhận gia sư "
						+PostService.getAcceptTutor(post).getUser().getName()+",liên hệ gia sư!";	
				break;
			}
			case REQUEST_TO_PAY:{
				content = "Từ hệ thống: Tác giả của bài đăng '"+post.getTitle()+ "' đã chấp nhận bạn làm gia sư. "
						+ "Vui lòng thanh toán phí để được nhận lớp!";
				break;
			}
			case PAYMENT_SUCCESS:
				content = "Bạn đã thanh toán thành công một hóa đơn nhận lớp!";
				break;
			case ACCEPT_OPEN_CLASS:{
				content = "Lớp học của bạn đã được tạo thành công! Vào lịch học để xem chi tiết!";
				break;
			}
			case REJECT_OPEN_CLASS:{
				if(role == ERole.ROLE_STUDENT) 
					content = "Gia sư "+PostService.getAcceptTutor(post).getUser().getName()
							+" đã từ chối thanh toán cho lớp '"+ post.getTitle()+"'";
				
				else if(role == ERole.ROLE_TUTOR){
					content = "Bạn đã từ chối thanh toán cho lớp '"+ post.getTitle()+"'";
				}	
				break;
			}	
			case FINISH_CLASS:{
				if(role == ERole.ROLE_STUDENT) 
					content = "Lớp học của gia sư  đã kết thúc. Đánh giá ngay!";
				
				else if(role == ERole.ROLE_TUTOR){
					content = "Lớp học của bạn đã kết thúc!";
				}	
				break;
			}
		}
		return content;
	}

}
