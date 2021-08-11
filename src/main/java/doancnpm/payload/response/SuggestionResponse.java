package doancnpm.payload.response;

public class SuggestionResponse {

	private Long id;
	private Long idStudent;
	private String nameStudent;
	private String phoneNumberStudent;
	private Long idTutor;
	private String nameTutor;
	private String phoneNumberTutor;
	private String avatar;
	private Long idPost;
	private String titlePost;
	
	
	
	public String getNameStudent() {
		return nameStudent;
	}
	public void setNameStudent(String nameStudent) {
		this.nameStudent = nameStudent;
	}
	public String getPhoneNumberStudent() {
		return phoneNumberStudent;
	}
	public void setPhoneNumberStudent(String phoneNumberStudent) {
		this.phoneNumberStudent = phoneNumberStudent;
	}
	public String getPhoneNumberTutor() {
		return phoneNumberTutor;
	}
	public void setPhoneNumberTutor(String phoneNumberTutor) {
		this.phoneNumberTutor = phoneNumberTutor;
	}
	public String getTitlePost() {
		return titlePost;
	}
	public void setTitlePost(String titlePost) {
		this.titlePost = titlePost;
	}
	public String getNameTutor() {
		return nameTutor;
	}
	public void setNameTutor(String nameTutor) {
		this.nameTutor = nameTutor;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Long getIdPost() {
		return idPost;
	}
	public void setIdPost(Long idPost) {
		this.idPost = idPost;
	}
	private int status;
	
	public Long getId() {
		return id;
	}
	public Long getIdStudent() {
		return idStudent;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}
	public Long getIdTutor() {
		return idTutor;
	}
	public int getStatus() {
		return status;
	}
	public void setIdTutor(Long idTutor) {
		this.idTutor = idTutor;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
