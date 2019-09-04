package com.andrew.ers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.ers.dto.ReimbursementAction;
import com.andrew.ers.dto.ReimbursementDTO;
import com.andrew.ers.services.ReimbursementService;

@RestController
@RequestMapping(produces=MediaType.APPLICATION_JSON_VALUE)
public class ReimbursementController {
	
	@Autowired
	ReimbursementService rservice;
	
	@GetMapping("/reimbursements")
	public ResponseEntity<?> getAllReimbursements() {
		Resources<ReimbursementDTO> list = rservice.getAllReimbursements();
		return new ResponseEntity<Resources<ReimbursementDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/users/{username}/reimbursements")
	public ResponseEntity<?> getReimbursementsForUser(@PathVariable String username) {
		Resources<ReimbursementDTO> list = rservice.getReimbursementsForUser(username);
		return new ResponseEntity<Resources<ReimbursementDTO>>(list, HttpStatus.OK);
	}
	
	@PutMapping(value="/reimbursements/{id}/status",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> performActionOnReimbursement(@PathVariable long id, @RequestBody ReimbursementAction update) {
		System.out.println(update);
		if (update.action.equals(ReimbursementAction.approve)) {
			rservice.approveReimbursement(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else if (update.action.equals(ReimbursementAction.deny)) {
			rservice.denyReimbursement(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value="/users/{username}/reimbursements",consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createNewReimbursementForUser(@PathVariable String username, 
			@RequestBody ReimbursementDTO newReimburseRequest) {
		Resources<ReimbursementDTO> list = rservice.submitNewReimbursement(username, newReimburseRequest);
		return new ResponseEntity<Resources<ReimbursementDTO>>(list, HttpStatus.CREATED);
	}
}
