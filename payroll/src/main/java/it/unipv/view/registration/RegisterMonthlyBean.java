package it.unipv.view.registration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.controller.PayrollController;
import it.unipv.dao.PayrollDAO;
import it.unipv.model.employees.MonthlyEmployeeWithSales;
import it.unipv.model.payment.Bank;
import it.unipv.model.payment.Mail;
import it.unipv.model.union.Union;
import it.unipv.view.employees.MonthlyEmployeesBean;
import it.unipv.view.post.PostServiceChargeBean;
import it.unipv.view.utils.PaymentDropdownView;

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
    	if(selectedUnion.equals("-")){
    		empl.setDueRate(0);
    	}
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
