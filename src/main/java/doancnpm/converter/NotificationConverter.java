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
		out.setContent(notification.getContent());
		out.setTime(notification.getCreatedDate().toString());
		return out;
	}
	
}
