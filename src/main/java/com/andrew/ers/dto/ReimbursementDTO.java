package com.andrew.ers.dto;

import java.util.List;

import com.andrew.ers.model.Expense;

public class ReimbursementDTO {
	
	private long id;
	private double total;
	private boolean isApproved;
	private List<Expense> expenses;
	
	public boolean isApproved() {
		return isApproved;
	}
	
	public void setApproved(boolean b) {
		isApproved = b;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
	
}
