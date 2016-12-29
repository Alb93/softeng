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
    
    @PostConstruct
	public void init() {
		r = new SalesReceipt();
	}
    
    public void post() {
    	if(r.getAmount() != 0){
    		setDate();
        	setMonthlyId();
        	payrollController.postSalesReceipt(r);
            System.out.println(("posting " + r.getId()+" sales receipt"));
            r = new SalesReceipt();
    	}   	
    }

	private void setDate() {
		FacesContext context = FacesContext.getCurrentInstance();
        CalendarView calendarView = (CalendarView) context.getApplication().evaluateExpressionGet(context, "#{calendarView}", CalendarView.class);
        Date sqldate = new Date(calendarView.getDate().getTime());
        r.setDate(sqldate);
	}

	private void setMonthlyId() {
		FacesContext context = FacesContext.getCurrentInstance();
        LoggedMonthlyBean loggedMonthlyBean = (LoggedMonthlyBean) context.getApplication().evaluateExpressionGet(context, "#{loggedMonthlyBean}", LoggedMonthlyBean.class);
        r.setEmp_id((loggedMonthlyBean.getEmpl().getId()));
	}
	
	public SalesReceipt getR() {
		return r;
	}

	public void setR(SalesReceipt r) {
		this.r = r;
	}

}
