package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.employees.DailyEmployee;
import org.jboss.tools.examples.model.union.Union;

@Named
@SessionScoped
public class RegisterUnionBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
    
    private Union u;
    
    
    @PostConstruct
	public void init() {
		u = new Union();
	}
    
    public void register() {
    	payrollController.registerUnion(u);
    }
    
    public void setEmpl(Union u) {
		this.u = u;
	}
    
    public Union getUnion() {
		return u;
	}

}
