package doancnpm.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import doancnpm.enums.CandidateStatus;
import doancnpm.enums.PostStatus;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post")
public class Post  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne
	@JoinColumn(name="grade_id")
	@JsonIgnoreProperties("posts")
	private Grade grade;
	@Column(name = "price")
	private String price;

	@Column(name = "address")
	private String address;

	@Column(name = "schedule")
	@Size(max = 500)
	private String schedule;
	
	@Column(name = "verify")
	private Boolean verify = false;
	
	@Transient 
	private Boolean isExpire;
	
	@Enumerated(EnumType.STRING)
	@Column
	private PostStatus status;
	
	@OneToMany(mappedBy = "post",orphanRemoval=true,cascade = {CascadeType.ALL})
	@JsonIgnoreProperties("post")
	private Set<Candidate> candidates = new HashSet<>();
	
	@Column(name = "createdDate")
	@CreatedDate
	private Date createdDate;

	@Column(name = "modifiedDate")
	@LastModifiedDate
	private Date modifiedDate;

	@Column(name = "finishDate")
	private Date finishDate;


	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "post_subject", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	@JsonIgnoreProperties("posts")
	private Set<Subject> subjects = new HashSet<>();
	
	@ManyToOne
	@JoinColumn(name = "studentId")
	@JsonIgnoreProperties("post")
	private Student student;

	@OneToMany(mappedBy = "post", cascade=CascadeType.REMOVE)
	@JsonIgnoreProperties("post")
	private List<Suggestion> suggestion = new ArrayList<>();
	
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Suggestion> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(List<Suggestion> suggestion) {
		this.suggestion = suggestion;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public PostStatus checkStatus() {
		if(getIsExpire())
			return PostStatus.CLOSE;
		if((candidates==null||candidates.isEmpty()))
			return PostStatus.OPEN;
		for(Candidate cd:candidates) {
			if(cd.getStatus() == CandidateStatus.WAITING)
				return PostStatus.CHOOSING;
			if(cd.getStatus() == CandidateStatus.ACCEPT)
				return PostStatus.CLOSE;
		}
		return PostStatus.OPEN;
	}
	public PostStatus getStatus() {
		return status;
	}
	public void setStatus(PostStatus status) {
		this.status = status;
	}


	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Post() {

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Candidate> getCandidates() {

		return candidates;
	}

	public void setCandidates(Set<Candidate> candidates) {
		this.candidates.addAll(candidates);
	}

	public Boolean getVerify() {
		return verify;
	}

	public void setVerify(Boolean verify) {
		this.verify = verify;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Boolean getIsExpire() {
		Date now = new Date();
		if(now.after(getFinishDate())) {
			setIsExpire(true);
		}else
			setIsExpire(false);
		return isExpire;
	}

	public void setIsExpire(Boolean isExpire) {
		this.isExpire = isExpire;
	}
	

}
