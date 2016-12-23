package org.jboss.controller;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.jboss.dao.PayrollDAO;
import org.jboss.logging.Logger;
import org.jboss.model.admin.Admin;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.Employee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.salesreceipt.SalesReceipt;
import org.jboss.model.timecard.TimeCard;
import org.jboss.model.union.ServiceCharge;
import org.jboss.model.union.Union;


@Stateful
public class PayrollController {

	public Union getU() {
		return u;
	}

	public void setU(Union u) {
		this.u = u;
	}


	@Inject
	PayrollDAO payrollDAO;
	
	public static String SUCCESS = "success";
	public static String SUCCESS_U = "success_u";

	public static String SUCCESS_D = "success_d";
	public static String SUCCESS_M = "success_m";

	public static String FAILURE = "failure";
	
	private Employee emp;
	private Union u;
	private Admin adm;
	
	
	Logger logger = Logger.getLogger(PayrollController.class);
	
	public String checkLogin(String username, String password){
		
		try {
    		
			emp = payrollDAO.doLogin(username, password);
			System.out.println("EMP dao = "+emp);
    		logger.info(emp.getName());
    		return SUCCESS; 
    	} catch (NoResultException e) {
    		return FAILURE;  
    	}
	}
	
	public void registerEmployee(Employee emp) {
		payrollDAO.registerEmployee(emp);
	}
	
	public void postSalesReceipt(SalesReceipt r) {
		payrollDAO.postSalesReceipt(r);
	}
	
	
	public String checkLoginUnion(String username, String password){
		
		try {
    		
			u = payrollDAO.doLoginUnion(username, password);
			System.out.println("u dao = "+u);
    		return SUCCESS; 
    	} catch (NoResultException e) {
    		return FAILURE; 
    	}
	}


	

	
	public String checkAdminLogin(String username, String password){
		try {
    		
			adm = payrollDAO.doAdminLogin(username, password);
			
    		return SUCCESS; 
    	} catch (NoResultException e) {
    		return FAILURE;  
    	}
	}

	
	public void postTimeCard(TimeCard card){
		payrollDAO.postTimeCard(card);
	}
	
	public void postServiceCharge(ServiceCharge s){
		payrollDAO.postServiceCharge(s);
	}
	
	public List<DailyEmployee> findAllDailyEmployees() {
		return payrollDAO.findAllDailyEmployees();
	}
	
	public void removeDailyEmployee(int id){
		payrollDAO.removeDailyEmployee(id);
	}
	
	public List<Union> findAllUnions() {
		return payrollDAO.findAllUnions();
	}

	
	
	public Union findCorrespondingUnion(String name) {
		return payrollDAO.findCorrespondingUnion(name);
	}
	
	public List<Employee> findAllUnionsEmployee(String name) {
		return payrollDAO.findAllUnionsEmployee(name);
	}



	public Employee getEmp() {
		return emp;
	}
	
	public void updateDailyEmployee(DailyEmployee d){
		payrollDAO.updateDailyEmployee(d);
	}
	
	public void updateMonthlyEmployee(MonthlyEmployeeWithSales m){
		payrollDAO.updateMonthlyEmployee(m);
	}

	public List<MonthlyEmployeeWithSales> findAllMonthlyEmployees() {
		return payrollDAO.findAllMonthlyEmployees();
	}

	public void removeMonthlyEmployee(int id) {
		payrollDAO.removeMonthlyEmployee(id);
		
	}
    

}
