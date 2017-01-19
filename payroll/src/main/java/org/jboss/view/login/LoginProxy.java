package org.jboss.view.login;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.model.employees.Employee;
import org.primefaces.context.RequestContext;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class LoginProxy implements ILogin, Serializable {

	private ILogin iLogin;
	private int i;

	private String username;
	private String password;
	
	
	

	
	public String performLogin(){
		if(username != null){
			String result = checkLogin();
			if(result != null){
				return result;
			} else{
	    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Invalid credentials");
	    		RequestContext.getCurrentInstance().showMessageInDialog(message);
	    		return "";
	    	}
		} return "";
		
	}

	@Override
	public String checkLogin() {
		//i = 0;
		setUsernameI(username);
		setPasswordI(password);
		if (iLogin.checkLogin().equals(iLogin.getSuccessfulString())) {
				System.out.println("successo" + iLogin.getSuccessfulString() + iLogin.getEmployee().getUsername());
				return iLogin.getSuccessfulString();
			}
		
		return "";
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
	
		return iLogin;
	}
	
	
	public void setActualLogin(ILogin ilogin) {
		this.iLogin = ilogin;
	}
	@Override
	public void setUsernameI(String username) {
			iLogin.setUsernameI(username);
		
	}

	@Override
	public void setPasswordI(String password) {
			iLogin.setPasswordI(password);
		
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
