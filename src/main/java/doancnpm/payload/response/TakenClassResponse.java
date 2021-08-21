package doancnpm.payload.response;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class TakenClassResponse {
	private Long idClass;
	private String name;
	@JsonIgnoreProperties("tutors")
	private String grade;
	@JsonIgnoreProperties("posts")
	private Set<String> subject;
	private String address;
	private String phonenumber;
	private Boolean isRated;
	private Map<String, Boolean> schedules;
	private String status;
	public Long getIdClass() {
		return idClass;
	}
	public void setIdClass(Long idClass) {
		this.idClass = idClass;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Set<String> getSubject() {
		return subject;
	}
	public void setSubject(Set<String> subject) {
		this.subject = subject;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Boolean getIsRated() {
		return isRated;
	}
	public void setIsRated(Boolean isRated) {
		this.isRated = isRated;
	}
	
}
