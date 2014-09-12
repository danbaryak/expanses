package org.accountable.common.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.accountable.common.services.DbBase;
import org.amdatu.mongo.MongoDBService;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import com.mongodb.DBCollection;

public abstract class CRUDServiceImpl<E extends DbBase> {

	private volatile MongoDBService mongoDBService;
	private volatile DBCollection dbCollection;
	private volatile JacksonDBCollection<E, String> collection;

	protected void initDb(String colName, Class<E> objClass) {
		dbCollection = mongoDBService.getDB().getCollection(colName);
		collection = JacksonDBCollection.wrap(dbCollection, objClass,
				String.class);
	}

	protected E add(E obj) {
		WriteResult<E, String> results = collection.insert(obj);
		String uniqueId = results.getSavedId();
		obj.setUniqueId(uniqueId);
		return obj;
	}

	protected List<E> getAll() {
		List<E> list = new ArrayList<E>();
		DBCursor<E> cursor = collection.find();
		while (cursor.hasNext()) {
			list.add(cursor.next());
		}
		return list;
	}

	protected E update(E obj) {
		if (obj == null) {
			throw new IllegalArgumentException("Cannot update a null object");
		}

		String uniqueId = obj.getUniqueId();

		if (uniqueId == null) {
			throw new NullPointerException(
					"Object's uniqueId is null. Cannot update.");
		}

		collection.updateById(uniqueId, obj);
		return obj;
	}

	protected E getById(String uniqueId) {
		if (uniqueId == null) {
			throw new IllegalArgumentException("Facility uniqueId is null");
		}

		return collection.findOneById(uniqueId);
	}

	protected E delete(String uniqueId) {
		if (uniqueId == null) {
			throw new IllegalArgumentException("Facility uniqueId is null.");
		}

		E obj = this.getById(uniqueId);

		if (obj == null) {
			return null;
		}

		collection.removeById(uniqueId);

		return obj;
	}
}
