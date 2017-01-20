package it.unipv.controller;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

import it.unipv.dao.PayrollDAO;
import it.unipv.model.admin.Admin;
import it.unipv.model.employees.DailyEmployee;
import it.unipv.model.employees.Employee;
import it.unipv.model.employees.MonthlyEmployeeWithSales;
import it.unipv.model.payment.Bank;
import it.unipv.model.payment.Mail;
import it.unipv.model.salesreceipt.SalesReceipt;
import it.unipv.model.timecard.TimeCard;
import it.unipv.model.union.ServiceCharge;
import it.unipv.model.union.Union;


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
		if(emp.getPaymentMethod().equals("Bank")){
			bank.setEmp_username(emp.getUsername());
			payrollDAO.registerBankAccount(bank);
		} else if(emp.getPaymentMethod().equals("Mail")) {
			mail.setEmp_username(emp.getUsername());
			payrollDAO.registerMailAddress(mail);
		}
	}
	
	public boolean checkUsername(String username){
		return payrollDAO.checkIfUsernameAlreadyExists(username);
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
	
	public List<TimeCard> findTimeCardsOfThisEmp(int id){
		return payrollDAO.findTimeCardsOfThisEmp(id);
	}
	
	public List<ServiceCharge> findServiceChargesOfThisEmp(int id){
		return payrollDAO.findServiceChargeOfThisEmp(id);
	}
	
	public List<SalesReceipt> findSalesReceiptOfThisEmp(int id){
		return payrollDAO.findSalesReceiptOfThisEmp(id);
	}
	
	public void removeDailyEmployee(int id){
		payrollDAO.removeDailyEmployee(id);
		payrollDAO.removeTimeCard(id);
		payrollDAO.removeServiceCharge(id);
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
		payrollDAO.removeOldBankAndMail(d.getUsername());
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
		payrollDAO.removeOldBankAndMail(m.getUsername());
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
		payrollDAO.removeSalesReceipt(id);
		payrollDAO.removeServiceCharge(id);
		
	}
	
	public Bank getBank(String username){
		return payrollDAO.findBank(username);
	}
	
	public Mail getMail(String username){
		return payrollDAO.findMail(username);
	}
    

}
