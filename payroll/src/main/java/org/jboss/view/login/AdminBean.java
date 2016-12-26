package org.jboss.view.login;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.admin.Admin;
import org.primefaces.context.RequestContext;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class AdminBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	private Admin admin;
    
    @PostConstruct
	public void init() {
		admin = new Admin();
	}
    
    public void checkLogin(){  	
    	if(admin.getUsername() != null){
    		System.out.println("admin " + admin.getUsername() + " " + admin.getPassword());
        	Admin a = payrollController.checkAdminLogin(admin.getUsername(), admin.getPassword());
        	if(a != null){
        		try {
    				FacesContext.getCurrentInstance().getExternalContext().redirect("admin_operations.jsf");
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        	}else{
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Invalid credentials");
        		RequestContext.getCurrentInstance().showMessageInDialog(message);
        	}
    	}
    	
    }
       
    public void setAdmin(Admin admin) {
		this.admin = admin;
	}
    
    public Admin getAdmin() {
		return admin;
	}
}
