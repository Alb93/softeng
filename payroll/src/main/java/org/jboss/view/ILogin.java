package org.jboss.view;

import org.jboss.model.employees.Employee;

public interface ILogin {
	
	public String checkLogin();
	public Employee getEmployee();
	public String getSuccessfulString();
	public void setUsernameI(String username);
	public void setPasswordI(String password);

}
