package org.accountable.common.services;

import org.mongojack.Id;
import org.mongojack.ObjectId;

/**
 * Database implementation specific annotations.
 */
public class DbBase {
	private String uniqueId;

	/**
	 * Returns the database unique id.
	 * 
	 * @return uniqueId database unique id
	 */
	@Id
	@ObjectId
	public String getUniqueId() {
		return uniqueId;
	}

	/**
	 * Sets the database unique id.
	 * 
	 * @param uniqueId
	 *            database unique id
	 */
	@Id
	@ObjectId
	public DbBase setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
		return this;
	}
}