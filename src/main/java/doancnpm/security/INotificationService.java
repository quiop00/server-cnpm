package doancnpm.security;

import doancnpm.enums.NotifyType;
import doancnpm.models.Post;
import doancnpm.models.User;

public interface INotificationService {
	void pushNotification(Post post, User user,NotifyType type,Long endpoint);
}
