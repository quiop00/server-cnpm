package doancnpm.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Subject")
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "subjectname")
	private String subjectname;

	
	@ManyToMany(mappedBy = "subjects")
	@JsonIgnoreProperties("subjects")
	private List<Tutor> tutors = new ArrayList<>();

	
	@ManyToMany(mappedBy = "subjects")
	@JsonIgnoreProperties("subjects")
	private List<Post> posts = new ArrayList<>();
	
	@ManyToMany(mappedBy = "subjects")
	@JsonIgnoreProperties("subjects")
	private List<TakenClass> classes = new ArrayList<>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubjectname() {
		return subjectname;
	}

	public void setSubjectname(String subjectname) {
		this.subjectname = subjectname;
	}

	public List<Tutor> getTutors() {
		return tutors;
	}

	public void setTutors(List<Tutor> tutors) {
		this.tutors = tutors;
	}

	public List<TakenClass> getClasses() {
		return classes;
	}

	public void setClasses(List<TakenClass> classes) {
		this.classes = classes;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
}
