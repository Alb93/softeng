package it.unipv.view.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import it.unipv.controller.PayrollController;
import it.unipv.model.admin.Admin;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class AdminBean implements Serializable, ILogout {
	
	
	@Inject
	private PayrollController payrollController;
	
	private Admin admin;
    
    @PostConstruct
	public void init() {
		admin = new Admin();
	}
    
    public String checkLogin(){  	
    	if(admin.getUsername() != null){
    		System.out.println("admin " + admin.getUsername() + " " + admin.getPassword());
        	Admin a = payrollController.checkAdminLogin(admin.getUsername(), admin.getPassword());
        	if(a != null){
        		return "success";
        	}else{
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Invalid credentials");
        		RequestContext.getCurrentInstance().showMessageInDialog(message);
        		return "";
        	}
    	} return "";
    	
    }
       
    public void setAdmin(Admin admin) {
		this.admin = admin;
	}
    
    public Admin getAdmin() {
		return admin;
	}

	@Override
	public String logout() {
		admin = new Admin();
		return "success";
	}
}
