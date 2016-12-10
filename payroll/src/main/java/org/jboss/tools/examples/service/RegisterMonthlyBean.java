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
import org.jboss.tools.examples.model.employees.MonthlyEmployeeWithSales;

@Named
@SessionScoped
public class RegisterMonthlyBean implements Serializable, IRegistrator {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
    
    private MonthlyEmployeeWithSales empl;
    
    @PostConstruct
	public void init() {
		empl = new MonthlyEmployeeWithSales();
	}
    
	@Override
    public void register() {
    	payrollController.registerEmployee(empl);
        log.info("Registering " + empl.getName());
    }

	@Override
	public Employee getEmpl() {
		// TODO Auto-generated method stub
		return null;
	}
    
  
}
