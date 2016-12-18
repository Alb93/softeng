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
	private List<DailyEmployee> dailyEmployees;
	private List<MonthlyEmployeeWithSales> monthlyEmployees;
	
	@PostConstruct
	public void init() {
		log.info("Logger");
		dailyEmployees = payrollDAO.findAllDailyEmployees();
		monthlyEmployees = payrollDAO.findAllMonthlyEmployees();
		
	}
	
	public void setDailyEmployees(List<DailyEmployee> dailyEmployees) {
		this.dailyEmployees = dailyEmployees;
	}
	
	public List<DailyEmployee> getDailyEmployees() {
		return dailyEmployees;
	}
	
	public void setMonthlyEmployees(List<MonthlyEmployeeWithSales> monthlyEmployees) {
		this.monthlyEmployees = monthlyEmployees;
	}
	
	public List<MonthlyEmployeeWithSales> getMonthlyEmployees() {
		return monthlyEmployees;
	}
	
	public void removeDailyEmployee(DailyEmployee emp) throws IOException{
		//System.out.println("Cancellando" + emp.getName());
		payrollDAO.removeDailyEmployee(emp.getId());
		dailyEmployees.remove(dailyEmployees.indexOf(emp));
	}
	
	public void removeMonthlyEmployee(MonthlyEmployeeWithSales emp) throws IOException{
		
		payrollDAO.removeMonthlyEmployee(emp.getId());
		monthlyEmployees.remove(monthlyEmployees.indexOf(emp));
	}
	
	
	
	

}
