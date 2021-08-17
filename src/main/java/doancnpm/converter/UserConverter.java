package doancnpm.converter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import doancnpm.models.ERole;
import doancnpm.models.Role;
import doancnpm.models.User;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.payload.response.UserResponse;
@Component
public class UserConverter {
	public User toUser(AddUserRequest addUser)
	{
		User user = new User();
		user.setUsername(addUser.getUsername());
		user.setPassword(addUser.getPassword());
		user.setEmail(addUser.getEmail());
		user.setPhonenumber(addUser.getPhonenumber());
		return user;
	}
	public User toUser(AddUserRequest addUser, User user)
	{
		user.setUsername(addUser.getUsername());
		user.setPassword(addUser.getPassword());
		user.setEmail(addUser.getEmail());
		user.setPhonenumber(addUser.getPhonenumber());
		return user;
	}
	public static UserResponse modelToResponse(User user) {
		UserResponse userOutput = new UserResponse();
		userOutput.setId(user.getId());
		userOutput.setUsername(user.getUsername());
		userOutput.setEmail(user.getEmail());
		userOutput.setPhonenumber(user.getPhonenumber());
		userOutput.setAge(user.getAge());
		userOutput.setName(user.getName());
		userOutput.setGender(user.getGender());
		Set<Role> setRoles = user.getRoles();
		Set<ERole> roles = new HashSet<ERole>();
		for (Role role : setRoles) {
			roles.add(role.getName());
		}
		userOutput.setRoles(roles);
		return userOutput;
	}
}
