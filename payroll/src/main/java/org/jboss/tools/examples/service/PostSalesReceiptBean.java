package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.sql.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.salesreceipt.SalesReceipt;
import org.jboss.tools.examples.utils.CalendarView;

@Named
@SessionScoped
public class PostSalesReceiptBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
    
    private SalesReceipt r;
    
    
    @PostConstruct
	public void init() {
		r = new SalesReceipt();
	}
    
    public void post() {
    	setDate();
    	setMonthlyId();
    	payrollController.postSalesReceipt(r);
        log.info("posting " + r.getId()+" sales receipt");
    }

	private void setDate() {
		FacesContext context = FacesContext.getCurrentInstance();
        CalendarView calendarView = (CalendarView) context.getApplication().evaluateExpressionGet(context, "#{calendarView}", CalendarView.class);
        Date sqldate = new Date(calendarView.getDate2().getTime());
        r.setDate(sqldate);
	}

	private void setMonthlyId() {
		FacesContext context = FacesContext.getCurrentInstance();
        LoggedMonthlyBean loggedMonthlyBean = (LoggedMonthlyBean) context.getApplication().evaluateExpressionGet(context, "#{loggedMonthlyBean}", LoggedMonthlyBean.class);
        r.setId_monthly(loggedMonthlyBean.getEmpl().getId());
	}
	
	public SalesReceipt getR() {
		return r;
	}

	public void setR(SalesReceipt r) {
		this.r = r;
	}

}
