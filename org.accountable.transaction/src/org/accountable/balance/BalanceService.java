package org.accountable.balance;

import java.util.List;

public interface BalanceService {
	public Balance addBalance(Balance balance);
	public List<Balance> getBalance();
	public Balance updateBalance(Balance balance);
	public Balance deleteBalance(String balacneId);
}
