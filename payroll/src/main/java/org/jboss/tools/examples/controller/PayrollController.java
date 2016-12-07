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

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.model.employees.DailyEmployee;
import org.jboss.tools.examples.model.employees.MonthlyEmployeeWithSales;
import org.jboss.tools.examples.service.LoggerManager;
import org.jboss.tools.examples.service.MemberRegistration;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Model
public class PayrollController {
	
	@Produces
	@Named
    private MemberRegistration memberRegistration;
    
    @Inject
    private LoggerManager loggerManager;

    @Inject
    private FacesContext facesContext;
    
    @Produces
    @Named
    private MonthlyEmployeeWithSales m_employee;
    
    @Produces
    @Named
    private DailyEmployee d_employee;
    
    
    
   
    @PostConstruct
    public void initNewMember() {
    	m_employee = new MonthlyEmployeeWithSales();
    	d_employee = new DailyEmployee();
    	memberRegistration = new MemberRegistration();
    	
    }

    public String login() throws Exception {
        try {
        	String usn = d_employee.getUsername();
        	String psw = d_employee.getPassword();
        	
          //  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
         //   facesContext.addMessage(null, m);
        	initNewMember();
        	return loggerManager.checkLogin(usn, psw);
            
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
            System.err.println(errorMessage);
            return "";
        }
    }
    
    public String registerUsernameAndPassword() throws Exception {
        try {
        	long id = d_employee.getId();
        	String usn = d_employee.getUsername();
        	String pwd = d_employee.getPassword();
        	initNewMember();
        	return memberRegistration.setUsernameAndPassword(id, usn, pwd);
          //  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
         //   facesContext.addMessage(null, m);
            
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
            System.err.println(errorMessage);
            return "";
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

}
