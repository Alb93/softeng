package org.jboss.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.employees.DailyEmployee;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	
	
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
