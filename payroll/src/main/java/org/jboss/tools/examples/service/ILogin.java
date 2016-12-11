package org.jboss.tools.examples.service;

import org.jboss.tools.examples.model.employees.Employee;

public interface ILogin {
	
	public String checkLogin();
	public Employee getEmployee();
	public String getSuccessfulString();
	public void setUsernameI(String username);
	public void setPasswordI(String password);

}
