package doancnpm.payload.request;

public class CommentRequest {
	private Long idTutor;
	private String content;

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
	
}
