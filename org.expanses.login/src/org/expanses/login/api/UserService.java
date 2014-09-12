package org.expanses.login.api;

import java.util.List;

public interface UserService {
	public List<User> getUsers();
	public void addUser(User user);
}
