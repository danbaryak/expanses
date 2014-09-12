package org.expanses.login.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.expanses.login.api.User;
import org.expanses.login.api.UserService;

@Path("users")
public class UserResource {

	private volatile UserService userService;
	
	@GET
	@Path("me")
	@Produces(MediaType.APPLICATION_JSON)
	public User getCurrentUser() {
		return new User("danbar");
	}
	
}
