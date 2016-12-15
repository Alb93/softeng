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
import org.jboss.tools.examples.model.union.ServiceCharge;
import org.jboss.tools.examples.utils.CalendarView;

@Named
@SessionScoped
public class PostServiceChargeBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
    
    private ServiceCharge r;
    
    
    @PostConstruct
	public void init() {
		r = new ServiceCharge();
	}
    
    //menu a tendina con tutti gli employees per quella union
    public void post(int id) {
    	//r.setId(id);
    	r.setId_emp(id);
    	payrollController.postServiceCharge(r);
       // log.info("posting " + r.getId()+" sales receipt");
    }
    
	public ServiceCharge getR() {
		return r;
	}

	public void setR(ServiceCharge r) {
		this.r = r;
	}

}
