package org.accountable.transaction.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.accountable.transaction.Transaction;
import org.accountable.transaction.TransactionService;
import org.amdatu.mongo.MongoDBService;
import org.mongojack.DBCursor;
import org.mongojack.DBSort;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import com.mongodb.DBCollection;

public class TransactionServiceImpl implements TransactionService {

	private static final String COLLECTION = "transactions";

	private volatile MongoDBService mongoDBService;
	private volatile DBCollection dbCollection;
	private volatile JacksonDBCollection<Transaction, String> transactionsCollection;

	public void start() {
		dbCollection = mongoDBService.getDB().getCollection(COLLECTION);
		transactionsCollection = JacksonDBCollection.wrap(dbCollection,
				Transaction.class, String.class);
	}

	public void stop() {
		dbCollection = null;
	}

	@Override
	public Transaction addTransaction(Transaction transaction) {
		if (transaction.getTime() == null) {
			transaction.setTime(new Date());
		}
		WriteResult<Transaction, String> results = transactionsCollection
				.insert(transaction);
		String uniqueId = results.getSavedId();
		transaction.setUniqueId(uniqueId);
		return transaction;
	}

	@Override
	public List<Transaction> getTransactions() {
		List<Transaction> list = new ArrayList<Transaction>();
		DBCursor<Transaction> cursor = transactionsCollection.find();

		cursor.sort(DBSort.asc("name"));

		while (cursor.hasNext()) {
			list.add(cursor.next());
		}

		return list;
	}

	@Override
	public Transaction updateTransaction(Transaction transaction) {

		if (transaction == null) {
			throw new IllegalArgumentException("transaction is null.");
		}

		String uniqueId = transaction.getUniqueId();

		if (uniqueId == null) {
			throw new NullPointerException("Transaction's uniqueId is null. Cannot update.");
		}

		transactionsCollection.updateById(uniqueId, transaction);

		return transaction;
	}

}
