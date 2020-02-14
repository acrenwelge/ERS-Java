package com.andrew.ers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Expense {
	
	public Expense(long id, double amount, String description) {
		this.id = id;
		this.amount = amount;
		this.description = description;
	}
	
	public Expense() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	private double amount;
	
	@Column
	private String description;
	
	@ManyToOne
	private Reimbursement reimbursement;
	
	// TODO: implement bi-directional mapping of Expense/Reimbursement/User so that we can grab username from Expense...
	public String getAssociatedUsername() {
		return reimbursement.getUsername();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String s) {
		description = s;
	}

}
