package org.accountable.balance.impl;

import java.util.Date;
import java.util.List;

import org.accountable.balance.Balance;
import org.accountable.balance.BalanceService;
import org.accountable.common.services.impl.CRUDServiceImpl;

public class BalanceServiceImpl extends CRUDServiceImpl<Balance> implements BalanceService {
	
	private static final String COL_NAME = "balance";
	
	public void start() {
		initDb(COL_NAME, Balance.class);
	}
	
	@Override
	public Balance addBalance(Balance balance) {
		if (balance.getTime() == null) {
			balance.setTime(new Date());
		}
		return super.add(balance);
	}

	@Override
	public List<Balance> getBalance() {
		return super.getAll();
	}

	@Override
	public Balance updateBalance(Balance balance) {
		return super.update(balance);
	}

	public Balance getBalanceById(String uniqueId) {
		return super.getById(uniqueId);
	}
	
	@Override
	public Balance deleteBalance(String balanceId) {
		return super.delete(balanceId);
	}

}
