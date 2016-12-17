/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.tools.examples.controller;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;
import org.jboss.tools.examples.dao.PayrollDAO;
import org.jboss.tools.examples.model.admin.Admin;
import org.jboss.tools.examples.model.employees.Employee;
import org.jboss.tools.examples.model.salesreceipt.SalesReceipt;
import org.jboss.tools.examples.model.timecard.TimeCard;
import org.jboss.tools.examples.model.union.ServiceCharge;
import org.jboss.tools.examples.model.union.Union;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6

@Stateful
public class PayrollController {

	public Union getU() {
		return u;
	}

	public void setU(Union u) {
		this.u = u;
	}


	@Inject
	PayrollDAO payrollDAO;
	
	public static String SUCCESS = "success";
	public static String SUCCESS_U = "success_u";

	public static String SUCCESS_D = "success_d";
	public static String SUCCESS_M = "success_m";

	public static String FAILURE = "failure";
	
	private Employee emp;
	private Union u;
	private Admin adm;
	
	
	Logger logger = Logger.getLogger(PayrollController.class);
	
	public String checkLogin(String username, String password){
		
		try {
    		
			emp = payrollDAO.doLogin(username, password);
			System.out.println("EMP dao = "+emp);
    		logger.info(emp.getName());
    		return SUCCESS; 
    	} catch (NoResultException e) {
    		return FAILURE;  
    	}
	}
	
	public void registerEmployee(Employee emp) {
		payrollDAO.registerEmployee(emp);
	}
	
	public void postSalesReceipt(SalesReceipt r) {
		payrollDAO.postSalesReceipt(r);
	}
	
	public void registerUnion(Union u) {
		payrollDAO.registerUnion(u);
	}
	
	public String checkLoginUnion(String username, String password){
		
		try {
    		
			u = payrollDAO.doLoginUnion(username, password);
			System.out.println("u dao = "+u);
    		return SUCCESS; 
    	} catch (NoResultException e) {
    		return FAILURE; 
    	}
	}


	

	
	public String checkAdminLogin(String username, String password){
		try {
    		
			adm = payrollDAO.doAdminLogin(username, password);
			
    		return SUCCESS; 
    	} catch (NoResultException e) {
    		return FAILURE;  
    	}
	}

	
	public void postTimeCard(TimeCard card){
		payrollDAO.postTimeCard(card);
	}
	
	public void postServiceCharge(ServiceCharge s){
		payrollDAO.postServiceCharge(s);
	}

	public List<Union> findAllUnions() {
		return payrollDAO.findAllUnions();
	}
	
	public List<Employee> findAllUnionsEmployee(long unionId) {
		return payrollDAO.findAllUnionsEmployee(unionId);
	}



	public Employee getEmp() {
		return emp;
	}
    

}
