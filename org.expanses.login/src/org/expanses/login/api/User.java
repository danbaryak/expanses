package org.expanses.login.api;

import org.mongojack.ObjectId;

public class User {
	@ObjectId
	public String _id;
	private String name;
	private String details;
	
	public User() {
		
	}
	
	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
