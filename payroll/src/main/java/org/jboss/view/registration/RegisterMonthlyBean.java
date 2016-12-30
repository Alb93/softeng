package org.jboss.view.registration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.payment.Bank;
import org.jboss.model.payment.Mail;
import org.jboss.model.union.Union;
import org.jboss.view.employees.MonthlyEmployeesBean;
import org.jboss.view.post.PostServiceChargeBean;
import org.jboss.view.utils.PaymentDropdownView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class RegisterMonthlyBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	@Inject MonthlyEmployeesBean empBean;
	
	@Inject PayrollDAO payrollDAO;
	
	@Inject PostServiceChargeBean serviceBean;
	
	@Inject PaymentDropdownView payment;
    
	private MonthlyEmployeeWithSales empl;
	private Bank bank = new Bank();
    private Mail mail = new Mail();
    private String selectedUnion = "-";
	private ArrayList<String> unionNames;
	private String selectedPaymentMethod = "Mail";
	private boolean usernameAlreadyUsed = false;
	
    
    @PostConstruct
	public void init() {
		empl = new MonthlyEmployeeWithSales();
        List<Union> unions = payrollController.findAllUnions();
    	unionNames = new ArrayList<>();
    	unionNames.add("-");
    	for (Union union : unions) {
			unionNames.add(union.getName());
			System.out.println(union.getName());
		}
	}
    
    public String register() {
    	empl.setUnion_name(selectedUnion);
    	empl.setPaymentMethod(selectedPaymentMethod);
    	if(payrollController.checkUsername(empl.getUsername())){
    		usernameAlreadyUsed = true;
    		return "failure";
    	} else {
    		usernameAlreadyUsed = false;
    		payrollController.registerEmployee(empl, bank, mail);
        	empBean.setMonthlyEmployees(payrollDAO.findAllMonthlyEmployees());
        	serviceBean.reload();
        	System.out.println("Registering" + empl.getName());   
        	empl = new MonthlyEmployeeWithSales();
        	mail = new Mail();
        	bank = new Bank();
        	return "success";
    	}
    	
    }
    
    public MonthlyEmployeeWithSales getEmpl() {
		return empl;
	}
    
    public void setEmpl(MonthlyEmployeeWithSales empl) {
		this.empl = empl;
	}
    
    public void setBank(Bank bank) {
		this.bank = bank;
	}
    
    public void setMail(Mail mail) {
		this.mail = mail;
	}
    
    public Bank getBank() {
		return bank;
	}
    
    public Mail getMail() {
		return mail;
	}
    
    public void setSelectedUnion(String selectedUnion) {
		this.selectedUnion = selectedUnion;
	}
    
    public String getSelectedUnion() {
		return selectedUnion;
	}
    
    public void setUnionNames(ArrayList<String> unionNames) {
		this.unionNames = unionNames;
	}
    
    public ArrayList<String> getUnionNames() {
		return unionNames;
	}
    
    public void setSelectedPaymentMethod(String selectedPaymentMethod) {
		this.selectedPaymentMethod = selectedPaymentMethod;
	}
    
    public String getSelectedPaymentMethod() {
		return selectedPaymentMethod;
	}
    
    public boolean isUsernameAlreadyUsed() {
		return usernameAlreadyUsed;
	}
    
    public void setUsernameAlreadyUsed(boolean usernameAlreadyUsed) {
		this.usernameAlreadyUsed = usernameAlreadyUsed;
	}
    
  
}
