package doancnpm.payload.response;

import java.util.Set;

import doancnpm.models.ERole;

public class UserResponse {
	private Long id;
	private String username;
	private String email;
	private String name;
	private Long age;
	private Long gender;
	private String phonenumber;
	private Boolean block;
	public Boolean getBlock() {
		return block;
	}
	public void setBlock(Boolean block) {
		this.block = block;
	}
	private Set<ERole> roles;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public Long getGender() {
		return gender;
	}
	public void setGender(Long gender) {
		this.gender = gender;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public Set<ERole> getRoles() {
		return roles;
	}
	public void setRoles(Set<ERole> roles) {
		this.roles = roles;
	}
	
}
