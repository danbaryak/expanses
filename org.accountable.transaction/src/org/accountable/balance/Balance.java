package org.accountable.balance;

import java.util.Date;

import org.accountable.common.services.DbBase;

/**
 * Represents the account balance at a given time.
 */
public class Balance extends DbBase {

	private Date time;
	private double amount;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
