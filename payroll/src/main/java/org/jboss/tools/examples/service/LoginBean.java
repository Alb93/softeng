package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.employees.DailyEmployee;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
    
    
    private DailyEmployee empl;
    
    @PostConstruct
	public void init() {
		empl = new DailyEmployee();
	}
    
    
    public String checkLogin(){
    	String result = payrollController.checkLogin(empl.getUsername(), empl.getPassword());
    	return result;
    	
    }
    
    
    public void setEmpl(DailyEmployee empl) {
		this.empl = empl;
	}
    
    public DailyEmployee getEmpl() {
		return empl;
	}
    
    
    
    

}