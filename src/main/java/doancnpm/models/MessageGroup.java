//package doancnpm.models;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//
//@Entity
//@Table(name = "MessageGroup")
//public class MessageGroup {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//	
//	@ManyToOne
//	@JoinColumn(name="user_id")
//	private User user; 
//	
//	@ManyToOne
//	@JoinColumn(name="tutor_id")
//	private Tutor tutor; 
//	
//	@OneToMany(mappedBy = "messagegroup")
//	private List<Message> messages = new ArrayList<>();
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
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
//	public List<Message> getMessages() {
//		return messages;
//	}
//
//	public void setMessages(List<Message> messages) {
//		this.messages = messages;
//	}
//	
//	
//}
