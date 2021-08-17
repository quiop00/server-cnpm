package doancnpm.payload.response;

public class NotifyResponse {
	private Long endpoint;
	private String type;
	private String content;
	private String time;
	public Long getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(Long endpoint) {
		this.endpoint = endpoint;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
