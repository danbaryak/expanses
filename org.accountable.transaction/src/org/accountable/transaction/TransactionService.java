package org.accountable.transaction;

import java.util.List;

public interface TransactionService {
	public Transaction addTransaction(Transaction transaction);
	public Transaction updateTransaction(Transaction transaction);
	public List<Transaction> getTransactions();	
} 
