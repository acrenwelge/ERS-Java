package com.andrew.ers.services;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;

import com.andrew.ers.controllers.ReimbursementController;
import com.andrew.ers.dto.ReimbursementDTO;
import com.andrew.ers.model.Expense;
import com.andrew.ers.model.Reimbursement;
import com.andrew.ers.repositories.ReimbursementRepo;
import com.andrew.ers.repositories.UserRepo;

@Service
public class ReimbursementService {
	
	@Autowired
	ReimbursementRepo reimbursementRepo;
	
	@Autowired
	UserRepo userRepo;
	
	public static ReimbursementDTO convert(Reimbursement r) {
		ReimbursementDTO  newr = new ReimbursementDTO();
		newr.setId(r.getId());
		newr.setExpenses(r.getExpenses());
		newr.setApproved(r.isApproved());
		double tot = 0;
		for (Expense e : newr.getExpenses()) {
			tot += e.getAmount();
		}
		newr.setTotal(tot);
		return newr;
	}
	
	public static List<ReimbursementDTO> convert(List<Reimbursement> listr) {
		List<ReimbursementDTO> listDTO = new ArrayList<>();
		for (Reimbursement re : listr) {
			listDTO.add(convert(re));
		}
		return listDTO;
	}
	
	public static Reimbursement convert(ReimbursementDTO r) {
		Reimbursement newr = new Reimbursement();
		newr.setId(r.getId());
		newr.setExpenses(r.getExpenses());
		newr.setApproved(r.isApproved());
		newr.setExpenses(r.getExpenses());
		return newr;
	}
	
	public Resources<ReimbursementDTO> getAllReimbursements() {
		return new Resources<>(convert(reimbursementRepo.findAll()), 
				linkTo(methodOn(ReimbursementController.class).getAllReimbursements()).withSelfRel());
	}
	
	public Resources<ReimbursementDTO> getReimbursementsForUser(String username) {
		return new Resources<>(convert(userRepo.findByUsername(username).getReimbursements()), 
				linkTo(methodOn(ReimbursementController.class).getAllReimbursements()).withSelfRel());
	}
}
