package doancnpm.payload.response;

public class CommentResponse {
	private Long id;
	private Long idStudent;
	private String nameStudent;
	private Long idTutor;
	private String content;
	
	
	public String getNameStudent() {
		return nameStudent;
	}
	public void setNameStudent(String nameStudent) {
		this.nameStudent = nameStudent;
	}
	public Long getIdStudent() {
		return idStudent;
	}
	public Long getIdTutor() {
		return idTutor;
	}
	public String getContent() {
		return content;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}
	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
