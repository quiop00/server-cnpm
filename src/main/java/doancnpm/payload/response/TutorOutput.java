package doancnpm.payload.response;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import doancnpm.models.Grade;
import doancnpm.models.Subject;
import doancnpm.models.User;

public class TutorOutput {
	private Long id;
	private Long idUser;
	private String name;
	private String phonenumber;
	private Long age;
	private Long gender;
	private String qualification;
	private String avatar;
	private String rating;
	private String description;
	private String address;
	@JsonIgnoreProperties("tutors")
	private Set<String> subject;
	private Set<String> grade;

	
	public Long getIdUser() {
		return idUser;
	}
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	private Map<String,Boolean> schedules;
	
	public Map<String, Boolean> getSchedules() {
		return schedules;
	}
	public void setSchedules(Map<String, Boolean> schedules) {
		this.schedules = schedules;
	}

	
	
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public Long getGender() {
		return gender;
	}
	public void setGender(Long gender) {
		this.gender = gender;
	}
	public Long getId() {
		return id;
	}
	public String getQualification() {
		return qualification;
	}
	public String getAvatar() {
		return avatar;
	}
	public String getRating() {
		return rating;
	}
	public String getDescription() {
		return description;
	}
	public String getAddress() {
		return address;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	public Set<String> getSubject() {
		return subject;
	}
	public void setSubject(Set<String> subject) {
		this.subject = subject;
	}
	public Set<String> getGrade() {
		return grade;
	}
	public void setGrade(Set<String> grade) {
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
}
