package org.expanses.login.mongo;

import java.util.ArrayList;
import java.util.List;

import org.amdatu.mongo.MongoDBService;
import org.expanses.login.api.User;
import org.expanses.login.api.UserService;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import com.mongodb.DBCollection;

public class MongoUserService implements UserService {
	
	private volatile MongoDBService mongo;
	
	
	public List<User> getUsers() {
		DBCollection col = mongo.getDB().getCollection("users");
		JacksonDBCollection<User, Object> users = JacksonDBCollection.wrap(col, User.class);
		List<User> result = new ArrayList<User>();
		DBCursor<User> cursor = users.find();
		while (cursor.hasNext()) {
			result.add(cursor.next());
		}
		return result;
	}
	

	public void addUser(User user) {
		DBCollection col = mongo.getDB().getCollection("users");
		JacksonDBCollection<User, Object> users = JacksonDBCollection.wrap(col, User.class);
		users.save(user);
	}

}
