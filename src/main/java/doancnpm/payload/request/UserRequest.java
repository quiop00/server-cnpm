package doancnpm.payload.request;

public class UserRequest {
	private Long id;
//	private String username;
	private String email;
//	private String password;
	private String phonenumber;
	private Long age;
	private String name;
	private Long gender;
	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPhonenumber() {
		return phonenumber;
	}
	public Long getAge() {
		return age;
	}
	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public void setAge(Long age) {
		this.age = age;
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
	
}
