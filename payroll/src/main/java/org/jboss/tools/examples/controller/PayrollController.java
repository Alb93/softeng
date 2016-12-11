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

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;
import org.jboss.tools.examples.dao.PayrollDAO;
import org.jboss.tools.examples.model.employees.Employee;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Stateful
public class PayrollController {

	@Inject
	PayrollDAO payrollDAO;
	
	public static String SUCCESS = "success";

	public static String SUCCESS_D = "success_d";
	public static String SUCCESS_M = "success_m";

	public static String FAILURE = "failure";
	
	private Employee emp;
	
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


	public Employee getEmp() {
		return emp;
	}
    

}
