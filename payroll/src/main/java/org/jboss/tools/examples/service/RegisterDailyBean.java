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
import org.jboss.tools.examples.model.employees.Employee;

@Named
@SessionScoped
public class RegisterDailyBean implements Serializable, IRegistrator {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
    
    private DailyEmployee empl;
    
    
    @PostConstruct
	public void init() {
		empl = new DailyEmployee();
	}
    
	@Override
    public void register() {
    	payrollController.registerEmployee(empl);
        log.info("Registering " + empl.getName());
    }
    
    public void setEmpl(DailyEmployee empl) {
		this.empl = empl;
	}
    
    public DailyEmployee getEmpl() {
		return empl;
	}

}
