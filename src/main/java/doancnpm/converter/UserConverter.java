package doancnpm.converter;

import org.springframework.stereotype.Component;

import doancnpm.models.User;
import doancnpm.payload.request.AddUserRequest;
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
}
