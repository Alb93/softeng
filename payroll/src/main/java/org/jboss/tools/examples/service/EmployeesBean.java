package org.jboss.tools.examples.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.dao.PayrollDAO;
import org.jboss.tools.examples.model.employees.DailyEmployee;
import org.jboss.tools.examples.model.employees.MonthlyEmployeeWithSales;

@Named
@SessionScoped
public class EmployeesBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject PayrollDAO payrollDAO;
	private DailyEmployee dEmpl;
	private List<DailyEmployee> dailyEmployees;
	private List<MonthlyEmployeeWithSales> monthlyEmployeeWithSales;
	
	@PostConstruct
	public void init() {
		log.info("Logger");
		dEmpl = new DailyEmployee();
		dailyEmployees = payrollDAO.findAllDailyEmployees();
		
	}
	
	public void setDailyEmployees(List<DailyEmployee> dailyEmployees) {
		this.dailyEmployees = dailyEmployees;
	}
	
	public List<DailyEmployee> getDailyEmployees() {
		return dailyEmployees;
	}
	
	public void removeDailyEmployee(DailyEmployee emp) throws IOException{
		
		payrollDAO.removeDailyEmployee(emp.getName());
		dailyEmployees.remove(dailyEmployees.indexOf(emp));
	}
	
	

}
