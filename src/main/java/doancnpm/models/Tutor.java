package doancnpm.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tutor")

public class Tutor implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "qualification")
	private String qualification;

	@Column(name = "rating")
	private String rating;

	@Column(name = "description")
	private String description;

	@Column(name = "address")
	private String address;
	
	@Column(name = "verify")
	private Boolean verify = false;
	
	private Boolean isCompleted = false;
	
	@Column(name ="certificate", nullable = true)
	private String certificate;
	
	@Column(name = "CMND",nullable = true)
	private String cmnd;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties("tutor")
	private User user;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tutor_subject", joinColumns = @JoinColumn(name = "tutor_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	@JsonIgnoreProperties("tutors")
	private Set<Subject> subjects = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tutor_grade", joinColumns = @JoinColumn(name = "tutor_id"), inverseJoinColumns = @JoinColumn(name = "grade_id"))
	@JsonIgnoreProperties("tutors")
	private Set<Grade> grades = new HashSet<>();

	@OneToMany(mappedBy = "tutor", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("tutor")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "tutor", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("tutor")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<TakenClass> classes = new ArrayList<>();

	@OneToMany(mappedBy = "tutor",orphanRemoval = true,cascade = CascadeType.ALL)
	@JsonIgnoreProperties("tutor")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Payment> payments = new ArrayList<Payment>();
	
	
	@OneToMany(mappedBy = "tutor", cascade=CascadeType.REMOVE)
	@JsonIgnoreProperties("tutor")
	private List<Suggestion> suggestion = new ArrayList<>();
	
	
	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	@Column(name="schedule")
	@Size(max = 500)
	private String schedule;

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public Set<Grade> getGrades() {
		return grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public List<Suggestion> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(List<Suggestion> suggestion) {
		this.suggestion = suggestion;
	}

	public Boolean getVerify() {
		return verify;
	}

	public void setVerify(Boolean verify) {
		this.verify = verify;
	}

	public Boolean getIsCompleted() {
		if(this.address !=null && this.cmnd !=null && !this.grades.isEmpty() && this.qualification != null 
				&&!this.subjects.isEmpty() && this.schedule != null && this.user.checkComplete())
			isCompleted = true;
		return isCompleted;
	}

	public void setIsCompleted(Boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public List<TakenClass> getClasses() {
		return classes;
	}

	public void setClasses(List<TakenClass> classes) {
		this.classes = classes;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	
}
