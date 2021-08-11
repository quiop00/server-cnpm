//package doancnpm.models;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//@Entity
//@Table(name = "Time")
//public class Time {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
//	
//	@Column(name = "session")
//	private String session;
//	
//	
////	@OneToMany(mappedBy = "time")
////	@JsonIgnoreProperties("time")
////	private List<Schedule> schedules = new ArrayList<>();
//	
//	@ManyToMany(mappedBy = "times")
//	@JsonIgnoreProperties("times")
//	private List<Schedule> schedules = new ArrayList<>();
//	
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public String getSession() {
//		return session;
//	}
//
//	public void setSession(String session) {
//		this.session = session;
//	}
//
//	public List<Schedule> getSchedules() {
//		return schedules;
//	}
//
//	public void setSchedules(List<Schedule> schedules) {
//		this.schedules = schedules;
//	}		
//}
