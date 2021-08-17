package doancnpm.converter;

import doancnpm.enums.NotifyType;
import doancnpm.models.ERole;
import doancnpm.models.Notification;
import doancnpm.payload.response.NotifyResponse;
import doancnpm.security.services.PostService;

public class NotificationConverter {
	public static NotifyResponse modelToResponse(Notification notification,ERole role) {
		NotifyResponse out = new NotifyResponse();
		out.setEndpoint(notification.getEndpoint());
		out.setType(notification.getNotifyType().name());
		out.setContent(setContent(notification, role));
		out.setTime(notification.getCreatedDate().toString());
		return out;
	}
	public static String setContent(Notification notification,ERole role) {
		String content ="";
		switch(notification.getNotifyType()) {
			case STUDENT_ACCEPT:{
				if(role == ERole.ROLE_ADMIN)
					content = "Từ hệ thống: Tác giả của bài đăng #" + notification.getPost().getId() + " đã chấp nhận gia sư "
						+PostService.getAcceptTutor(notification.getPost()).getUser().getName()+",liên hệ gia sư!";	
				else {
					content = "Từ hệ thống: Tác giả của bài đăng #" + notification.getPost().getTitle() 
							+ " đã chấp nhận bạn làm gia sư. Vui lòng thanh toán hoặc liên hệ nhân viên để nhận lớp!";
				}
				break;
			}
			case TUTOR_ACCEPT:
				content = "Kết nối thành công lớp học, vào lịch học để xem thông tin chi tiết!";
				break;
			case TUTOR_DENY:{
				if(role == ERole.ROLE_STUDENT) 
					content = "Gia sư "+PostService.getAcceptTutor(notification.getPost()).getUser().getName()
							+" đã từ chối thanh toán cho lớp #"+ notification.getPost().getId();
				
				else if(role == ERole.ROLE_TUTOR){
					content = "Bạn đã từ chối thanh toán cho lớp #"+ notification.getPost().getId();
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
