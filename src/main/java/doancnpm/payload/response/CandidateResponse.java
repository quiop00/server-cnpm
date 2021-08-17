package doancnpm.payload.response;

public class CandidateResponse {
	private Long idPost;
	private TutorOutput tutor;
	private String status;
	public Long getIdPost() {
		return idPost;
	}
	public void setIdPost(Long idPost) {
		this.idPost = idPost;
	}
	public TutorOutput getTutor() {
		return tutor;
	}
	public void setTutor(TutorOutput tutor) {
		this.tutor = tutor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
