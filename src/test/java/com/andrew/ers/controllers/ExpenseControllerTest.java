package com.andrew.ers.controllers;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

import com.andrew.ers.dto.ReimbursementDTO;
import com.andrew.ers.model.Expense;

public class ExpenseControllerTest extends BaseControllerTest {
	
	@Test
	public void testGetReimbursements() throws Exception {
		mvc.perform(get("/reimbursements").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testGetReimbursementsForUser() throws Exception {
		ReimbursementDTO r = new ReimbursementDTO();
		List<Expense> exps = new ArrayList<>();
		exps.add(new Expense(1L, 30.21));
		exps.add(new Expense(1L, 30.21));
		exps.add(new Expense(1L, 30.21));
		r.setExpenses(exps);
		String json = mapper.writeValueAsString(r);
		mvc.perform(post("/users/acrenwelge/reimbursements").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json));
		mvc.perform(get("/users/acrenwelge/reimbursements").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateReimbursementForUser() throws Exception {
		ReimbursementDTO r = new ReimbursementDTO();
		List<Expense> exps = new ArrayList<>();
		exps.add(new Expense(1L, 30.21));
		exps.add(new Expense(1L, 30.21));
		exps.add(new Expense(1L, 30.21));
		r.setExpenses(exps);
		String json = mapper.writeValueAsString(r);
		mvc.perform(post("/users/acrenwelge/reimbursements").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json));
		mvc.perform(get("/users/acrenwelge/reimbursements").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testApproveReimbursement() throws Exception {
		mvc.perform(put("/reimbursements/1?action=approve"));
		MvcResult result = mvc.perform(get("/reimbursements/1"))
		.andExpect(status().isOk())
		.andReturn();
		MockHttpServletResponse r = result.getResponse();
		ReimbursementDTO resp = mapper.readValue(r.getContentAsString(), ReimbursementDTO.class);
		assertTrue(resp.isApproved());
	}
}
