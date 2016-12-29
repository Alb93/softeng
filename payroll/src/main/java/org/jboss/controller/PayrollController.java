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
import org.jboss.model.payment.Bank;
import org.jboss.model.payment.Mail;
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

	public static String SUCCESS_D = "login_success_daily.jsf";
	public static String SUCCESS_M = "login_success_monthly.jsf";

	public static String FAILURE = "failure";
	
	private Employee emp;
	private Union u;
	private boolean empSetted = false;
	private Admin adm;
	
	
	Logger logger = Logger.getLogger(PayrollController.class);
	
	public String checkLogin(String username, String password){
		
    		
		emp = payrollDAO.doLogin(username, password);
		if(emp != null){
			System.out.println("EMP dao = "+emp);
	    	logger.info(emp.getName());
	    	empSetted = true;
	    	return SUCCESS; 
		} else {
			empSetted = false;
			return FAILURE;
		}
		
    	
	}
	
	public void registerEmployee(Employee emp, Bank bank, Mail mail) {
		payrollDAO.registerEmployee(emp);
//		if(emp.getPaymentMethod().equals("Bank")){
//			bank.setEmp_username(emp.getUsername());
//			payrollDAO.registerBankAccount(bank);
//		} else if(emp.getPaymentMethod().equals("Mail")) {
//			mail.setEmp_username(emp.getUsername());
//			payrollDAO.registerMailAddress(mail);
//		}
	}
	
	public void postSalesReceipt(SalesReceipt r) {
		payrollDAO.postSalesReceipt(r);
	}
	
	
	public Union checkLoginUnion(String username, String password){
		
		
    		
			return payrollDAO.doLoginUnion(username, password);
		
	}


	public boolean isEmpSetted() {
		return empSetted;
	}
	
	public Admin checkAdminLogin(String username, String password){
    		
			return payrollDAO.doAdminLogin(username, password);
			
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
	
	public void updateDailyEmployee(DailyEmployee d, Bank b, Mail m){
		payrollDAO.updateDailyEmployee(d);
		if(d.getPaymentMethod().equals("Bank")){
			b.setEmp_username(d.getUsername());
			payrollDAO.updateBankAccount(b);
		} else if(d.getPaymentMethod().equals("Mail")) {
			m.setEmp_username(d.getUsername());
			payrollDAO.updateMailAddress(m);
		}
	}
	
	public void updateMonthlyEmployee(MonthlyEmployeeWithSales m, Bank b, Mail mail){
		payrollDAO.updateMonthlyEmployee(m);
		if(m.getPaymentMethod().equals("Bank")){
			b.setEmp_username(m.getUsername());
			payrollDAO.updateBankAccount(b);
		} else if(m.getPaymentMethod().equals("Mail")) {
			mail.setEmp_username(m.getUsername());
			payrollDAO.updateMailAddress(mail);
		}
	}

	public List<MonthlyEmployeeWithSales> findAllMonthlyEmployees() {
		return payrollDAO.findAllMonthlyEmployees();
	}

	public void removeMonthlyEmployee(int id) {
		payrollDAO.removeMonthlyEmployee(id);
		
	}
	
	public Bank getBank(String username){
		return payrollDAO.findBank(username);
	}
	
	public Mail getMail(String username){
		return payrollDAO.findMail(username);
	}
    

}
