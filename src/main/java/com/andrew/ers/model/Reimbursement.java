package com.andrew.ers.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Reimbursement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	private boolean isApproved = false;
	
	@ManyToOne
	private AppUser user;
	
	@OneToMany(mappedBy="reimbursement")
	//@JoinColumn(name="reimbursement_id")
	private List<Expense> expenses;
	
	public String getUsername() {
		return user.getUsername();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public boolean isApproved() {
		return isApproved;
	}
	
	public void setApproved(boolean b) {
		isApproved = b;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}
}