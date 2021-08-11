package doancnpm.payload.request;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class PostRequest extends BaseRequest<PostRequest> {

	private String student;
	private String title;
	private String description;
	private Set<String> subject;
	private String grade;
	private String price;
	private String phonenumber;
	private String address;
	private Long studentID;
	private Map<String, Boolean> schedules;

	public Long getStudentID() {
		return studentID;
	}

	public void setStudentID(Long studentID) {
		this.studentID = studentID;
	}

	

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	private Date createdDate;

	private Date modifiedDate;

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

	

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}



}
