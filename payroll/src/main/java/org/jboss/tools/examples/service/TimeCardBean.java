package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.timecard.TimeCard;

@Named
@SessionScoped
public class TimeCardBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
	
	private TimeCard card;
    
    @PostConstruct
	public void init() {
		card = new TimeCard();
	}
    
    
    private Date date;
    
    
    
 
    public void setDate(Date date) {
		this.date = date;
	}
    
    public Date getDate() {
		return date;
	}
    /*public String checkLogin(){
    	String result = payrollController.checkLogin(empl.getUsername(), empl.getPassword());
    	return result;
    	
    }
    
    
    public void setEmpl(DailyEmployee empl) {
		this.empl = empl;
	}
    
    public DailyEmployee getEmpl() {
		return empl;
	}*/
	
	

}
