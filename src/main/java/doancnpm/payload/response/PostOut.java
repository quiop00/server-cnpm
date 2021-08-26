package doancnpm.payload.response;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import doancnpm.models.Candidate;

public class PostOut {
	private Long id;
	private Long idStudent;
	private String studentName;
	private String title;
	@JsonIgnoreProperties("tutors")
	private String grade;
	@JsonIgnoreProperties("posts")
	private Set<String> subject;
	private String price;
	private String phonenumber;
	private String address;
	private String description;
	private Map<String, Boolean> schedules;
	private Set<CandidateResponse> candidates;
	private String finishDate;
	private Boolean isExpire;
	private String status;
	private Boolean verify;
	
	public Boolean getVerify() {
		return verify;
	}

	public void setVerify(Boolean verify) {
		this.verify = verify;
	}

	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Set<String> getSubject() {
		return subject;
	}

	public void setSubject(Set<String> subject) {
		this.subject = subject;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Map<String, Boolean> getSchedules() {
		return schedules;
	}

	public void setSchedules(Map<String, Boolean> schedules) {
		this.schedules = schedules;
	}

	public Set<CandidateResponse> getCandidates() {
		return candidates;
	}

	public void setCandidates(Set<CandidateResponse> candidates) {
		this.candidates = candidates;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public Boolean getIsExpire() {
		return isExpire;
	}

	public void setIsExpire(Boolean isExpire) {
		this.isExpire = isExpire;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
}
