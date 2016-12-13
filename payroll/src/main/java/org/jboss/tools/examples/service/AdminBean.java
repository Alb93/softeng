package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.admin.Admin;

@Named
@SessionScoped
public class AdminBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
	
	private Admin admin;
    
    @PostConstruct
	public void init() {
		admin = new Admin();
	}
    
    public String checkLogin(){
    	
    	System.out.println("admin " + admin.getUsername() + " " + admin.getPassword());
    	return payrollController.checkAdminLogin(admin.getUsername(), admin.getPassword());
    	
    	
    }
    
    
    
    public void setAdmin(Admin admin) {
		this.admin = admin;
	}
    
    public Admin getAdmin() {
		return admin;
	}

}
