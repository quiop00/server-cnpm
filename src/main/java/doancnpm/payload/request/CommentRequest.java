package doancnpm.payload.request;

public class CommentRequest {
	private Long idTutor;
	private String content;
	private Long idClass;
	private Long rate;
	public Long getIdTutor() {
		return idTutor;
	}
	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getIdClass() {
		return idClass;
	}
	public void setIdClass(Long idClass) {
		this.idClass = idClass;
	}
	public Long getRate() {
		return rate;
	}
	public void setRate(Long rate) {
		this.rate = rate;
	}
	
}
