package it.unipv.view.login;

import it.unipv.model.employees.Employee;

public interface ILogin {
	
	public String checkLogin();
	public Employee getEmployee();
	public String getSuccessfulString();
	public void setUsernameI(String username);
	public void setPasswordI(String password);

}
