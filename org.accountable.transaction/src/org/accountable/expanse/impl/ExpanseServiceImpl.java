package org.accountable.expanse.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.accountable.expanse.Expanse;
import org.accountable.expanse.ExpansesService;
import org.accountable.transaction.Transaction;
import org.amdatu.mongo.MongoDBService;
import org.mongojack.DBCursor;
import org.mongojack.DBSort;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import com.mongodb.DBCollection;

public class ExpanseServiceImpl implements ExpansesService {

	private static final String COLLECTION = "expanses";

	private volatile MongoDBService mongoDBService;
	private volatile DBCollection dbCollection;
	private volatile JacksonDBCollection<Expanse, String> expansesCollection;

	public void start() {
		dbCollection = mongoDBService.getDB().getCollection(COLLECTION);
		expansesCollection = JacksonDBCollection.wrap(dbCollection,
				Expanse.class, String.class);
	}

	
	@Override
	public List<Expanse> getAllExpanses() {
		List<Expanse> list = new ArrayList<Expanse>();
		DBCursor<Expanse> cursor = expansesCollection.find();

		cursor.sort(DBSort.asc("name"));

		while (cursor.hasNext()) {
			list.add(cursor.next());
		}

		return list;
	}
	
	@Override
	public Expanse addExpanse(Expanse expanse) {
		if (expanse.getTime() == null) {
			expanse.setTime(new Date());
		}
		WriteResult<Expanse, String> results = expansesCollection
				.insert(expanse);
		String uniqueId = results.getSavedId();
		expanse.setUniqueId(uniqueId);
		return expanse;
	}

	@Override
	public void deleteExpanse(String uniqueId) {

	}

	@Override
	public Expanse updateExpanse(Expanse expanse) {
		if (expanse == null) {
			throw new IllegalArgumentException("expanse is null.");
		}

		String uniqueId = expanse.getUniqueId();

		if (uniqueId == null) {
			throw new NullPointerException(
					"Expanse's uniqueId is null. Cannot update.");
		}

		expansesCollection.updateById(uniqueId, expanse);
		return expanse;
	}

}
