package org.accountable.transaction;

import java.util.Date;

import org.accountable.common.services.DbBase;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Transaction extends DbBase {
	private double amount;
	private String name;
	private String description;
	
	private Date time;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double ammount) {
		this.amount = ammount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd,HH:00", timezone="CET")
	public Date getTime() {
		return time;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd,HH:00", timezone="CET")
	public void setTime(Date time) {
		this.time = time;
	}
	
	
}
