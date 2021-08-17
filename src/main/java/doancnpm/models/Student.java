package doancnpm.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "student")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	@JsonIgnoreProperties("student")
    private User user;

	@Column(name = "post_count")
	private Integer postCount = 0;
	
	@OneToMany(mappedBy = "student", orphanRemoval = true, cascade=CascadeType.REMOVE)
	@JsonIgnoreProperties("student")
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Invitation> invitations = new ArrayList<>();
	
	@OneToMany(mappedBy = "student", orphanRemoval = true, cascade=CascadeType.REMOVE)
	@JsonIgnoreProperties("student")
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Rate> rates = new ArrayList<>();
	
	@OneToMany(mappedBy = "student", orphanRemoval = true, cascade=CascadeType.REMOVE)
	@JsonIgnoreProperties("student")
	//@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Comment> comments = new ArrayList<>();
	
	
	
	@OneToMany(mappedBy = "student", cascade=CascadeType.REMOVE)
	@JsonIgnoreProperties("student")
	private List<Post> post = new ArrayList<>();
	
	@OneToMany(mappedBy = "student", cascade=CascadeType.REMOVE)
	@JsonIgnoreProperties("student")
	private List<TakenClass> classes = new ArrayList<>();

	
	@OneToMany(mappedBy = "student", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("student")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Suggestion> suggestion = new ArrayList<>();

	public List<Suggestion> getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(List<Suggestion> suggestion) {
		this.suggestion = suggestion;
	}

	public List<Post> getPost() {
		return post;
	}

	public void setPost(List<Post> post) {
		this.post = post;
	}

	public List<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getPostCount() {
		return postCount;
	}

	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}

	public List<Rate> getRates() {
		return rates;
	}

	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}

	public List<TakenClass> getClasses() {
		return classes;
	}

	public void setClasses(List<TakenClass> classes) {
		this.classes = classes;
	}
	
	
}
