package doancnpm.payload.request;


import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

public class AddTutorRequest {

	private Long age;
	private String name;
	private Long gender;
	private String qualification;
	private String certificate;
	private MultipartFile avatar;
	private MultipartFile cmnd;
	private String description;
	private Set<String> subject;
	private Set<String> grade;
	private String address;
	private Map<String,Boolean> schedules;
	
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getGender() {
		return gender;
	}
	public void setGender(Long gender) {
		this.gender = gender;
	}
	
	public Set<String> getSubject() {
		return subject;
	}
	public void setSubject(Set<String> subject) {
		this.subject = subject;
	}
	public Map<String, Boolean> getSchedules() {
		return schedules;
	}
	public void setSchedules(Map<String, Boolean> schedules) {
		this.schedules = schedules;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

	public Set<String> getGrade() {
		return grade;
	}
	public void setGrade(Set<String> grade) {
		this.grade = grade;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public MultipartFile getCmnd() {
		return cmnd;
	}
	public void setCmnd(MultipartFile cmnd) {
		this.cmnd = cmnd;
	}
	public MultipartFile getAvatar() {
		return avatar;
	}
	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}
	
	
}
