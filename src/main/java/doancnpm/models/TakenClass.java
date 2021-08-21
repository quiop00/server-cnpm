package doancnpm.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import doancnpm.enums.ClassStatus;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "taken_class")
public class TakenClass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_tutor")
	@JsonIgnoreProperties("classes")
	private Tutor tutor;
	
	@Column(name = "schedule")
	@Size(max = 500)
	private String schedule;
	
	@Column(name ="address")
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "id_student")
	@JsonIgnoreProperties("classes")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name ="id_grade")
	private Grade grade;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "class_subject", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
	@JsonIgnoreProperties("classes")
	private Set<Subject> subjects = new HashSet<>();
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ClassStatus status = ClassStatus.TEACHING;
	
	@Column(name = "is_rated")
	private Boolean isRated = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	public Set<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public ClassStatus getStatus() {
		return status;
	}

	public void setStatus(ClassStatus status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Boolean getIsRated() {
		return isRated;
	}

	public void setIsRated(Boolean isRated) {
		this.isRated = isRated;
	}
	

}
