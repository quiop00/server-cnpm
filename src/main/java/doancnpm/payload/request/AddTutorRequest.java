package doancnpm.payload.request;


import java.util.Set;


import java.util.Map;
import java.util.Set;

public class AddTutorRequest {
	private Long id;
	private String phonenumber;
	private Long age;
	private String name;
	private Long gender;
	private String qualification;
//	private String avatar;
	private String description;
	private Set<String> subject;
	private Set<String> grade;
	private String address;
//	private Set<String> teachingDate;
	private String username;
	private String rating;
	private Map<String,Boolean> schedules;
	
	
	
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

	
	public Long getId() {
		return id;
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
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
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

}
