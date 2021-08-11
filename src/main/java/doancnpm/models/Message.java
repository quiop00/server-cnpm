//package doancnpm.models;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//@Entity
//@Table(name = "comments")
//public class Message {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//	
//	@Column(name = "content")
//	private String content;
//	
//	@ManyToOne
//	@JoinColumn(name="user_id")
//	@JsonIgnoreProperties("messages")
//	private User user; 
//	
//	@ManyToOne
//	@JoinColumn(name="tutor_id")
//	@JsonIgnoreProperties("messages")
//	private Tutor tutor;
//	
//	
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//
//	public Tutor getTutor() {
//		return tutor;
//	}
//
//	public void setTutor(Tutor tutor) {
//		this.tutor = tutor;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//	
//}
