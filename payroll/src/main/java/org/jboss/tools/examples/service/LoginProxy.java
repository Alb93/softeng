package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.employees.Employee;

@Named
@SessionScoped
public class LoginProxy implements ILogin, Serializable {

	private List<ILogin> logins = new LinkedList<>();
	private int i;

	private String username;
	private String password;
	
	@Inject
	private LoginMonthlyBean lmb;
	
	@Inject
	private LoginDailyBean ldb;

	@PostConstruct
	public void init() {
		if (logins.size() == 0) {
			logins.add(lmb);
			logins.add(ldb);
		}
		// lack of open-closed, TODO
	}

	@Override
	public String checkLogin() {
		init();
		i = 0;
		setUsernameI(username);
		setPasswordI(password);
		for (ILogin iLogin : logins) {
			if (iLogin.checkLogin().equals(iLogin.getSuccessfulString())) {
				System.out.println("SUCC " +iLogin.getSuccessfulString());
				return iLogin.getSuccessfulString();
			}
			i++;
		}
		return PayrollController.FAILURE;
	}

	@Override
	public Employee getEmployee() {
		// not used
		return null;
	}

	@Override
	public String getSuccessfulString() {
		// not used
		return null;
	}

	public ILogin getActualLogin() {
		System.out.println("GET ACT = "+i);
		return logins.get(i);
	}

	@Override
	public void setUsernameI(String username) {
		for (ILogin iLogin : logins) {
			iLogin.setUsernameI(username);
		}
	}

	@Override
	public void setPasswordI(String password) {
		for (ILogin iLogin : logins) {
			iLogin.setPasswordI(password);
		}
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
