package org.jboss.view.post;

import java.io.Serializable;
import java.sql.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.salesreceipt.SalesReceipt;
import org.jboss.view.login.LoggedMonthlyBean;
import org.jboss.view.utils.CalendarView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class PostSalesReceiptBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
    
    private SalesReceipt r;
    
    @Inject
	private CalendarView calendarView;
    
    @PostConstruct
	public void init() {
		r = new SalesReceipt();
	}
    
    public String post(int id) {
    	if(r.getAmount() != 0){
    		r.setEmp_id(id);
    		setDate();
        	payrollController.postSalesReceipt(r);
            System.out.println(("posting " + r.getId()+" sales receipt"));
            r = new SalesReceipt();
            return "success";
    	} 
    	return "";
    	
    }

	private void setDate() {
		
        Date sqldate = new Date(calendarView.getDate().getTime());
        r.setDate(sqldate);
	}

	
	
	public SalesReceipt getR() {
		return r;
	}

	public void setR(SalesReceipt r) {
		this.r = r;
	}

}
