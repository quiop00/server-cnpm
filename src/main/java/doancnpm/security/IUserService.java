package doancnpm.security;

import java.util.List;
import java.util.Map;

import doancnpm.models.User;
import doancnpm.payload.request.AddUserRequest;
import doancnpm.payload.request.PasswordRequest;
import doancnpm.payload.request.UserRequest;

public interface IUserService {
	 List<User> all();
	 User getUserById(long id);
	 User getUser(String username);
	void save(String username, UserRequest userRequest);
	void delete(long[] ids);
	void admin_delete(long id);
	void admin_update(UserRequest userRequest, long id);
	String getPassword(String username);
	void save_password(String username, PasswordRequest passwordRequest);
}
