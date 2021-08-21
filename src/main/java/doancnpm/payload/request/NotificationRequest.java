package doancnpm.payload.request;

import doancnpm.enums.NotifyType;

public class NotificationRequest {
	private Long idPost;
	private NotifyType notifyType;
	private Long endpoint;
	public Long getIdPost() {
		return idPost;
	}
	public void setIdPost(Long idPost) {
		this.idPost = idPost;
	}
	public NotifyType getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(NotifyType notifyType) {
		this.notifyType = notifyType;
	}
	public Long getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(Long endpoint) {
		this.endpoint = endpoint;
	}
	
	

}
