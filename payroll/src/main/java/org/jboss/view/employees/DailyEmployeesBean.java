package org.jboss.view.employees;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.payment.Bank;
import org.jboss.model.payment.Mail;
import org.jboss.model.union.Union;
import org.jboss.view.post.PostServiceChargeBean;
import org.jboss.view.utils.PaymentDropdownView;
import org.jboss.view.utils.UnionDropdownView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class DailyEmployeesBean implements Serializable {
	
	@Inject
	private PayrollController payrollController;
	
	@Inject PostServiceChargeBean serviceChargeBean;
	private Bank bank = new Bank();
    private Mail mail = new Mail();
	private List<DailyEmployee> dailyEmployees;
	private DailyEmployee dEmployee;
	private String selectedUnion = "-";
	private ArrayList<String> unionNames;
	private String selectedMethod = "Mail";
	private boolean usernameAlreadyUsed = false;
	
	@PostConstruct
	public void init() {
		dailyEmployees = payrollController.findAllDailyEmployees();
        List<Union> unions = payrollController.findAllUnions();
    	unionNames = new ArrayList<>();
    	unionNames.add("-");
    	for (Union union : unions) {
			unionNames.add(union.getName());
			System.out.println(union.getName());
		}  	
		
	}
	
	public void setdEmployee(DailyEmployee dEmployee) {
		System.out.println("Sto settando" + dEmployee.getName());
		this.dEmployee = dEmployee;
	}
	
	public DailyEmployee getdEmployee() {
		return dEmployee;
	}
	
	public void setDailyEmployees(List<DailyEmployee> dailyEmployees) {
		this.dailyEmployees = dailyEmployees;
	}
	
	public List<DailyEmployee> getDailyEmployees() {
		return dailyEmployees;
	}
	
	public void removeDailyEmployee(DailyEmployee emp) throws IOException{
		System.out.println("Cancellando" + emp.getName());	
		payrollController.removeDailyEmployee(emp.getId());	
		dailyEmployees.remove(dailyEmployees.indexOf(emp));
		serviceChargeBean.reload();
	}
	
	public void goToEditDailyPage(DailyEmployee d){
		setdEmployee(d);
		if(!(d.getUnion_name().equals("-"))){
			Union union = payrollController.findCorrespondingUnion(d.getUnion_name());
			selectedUnion = union.getName();
		}
		if(d.getPaymentMethod().equals("Bank")){
			//query bank
			selectedMethod = "Bank";
			bank = payrollController.getBank(d.getUsername());
			
		}
		else if(d.getPaymentMethod().equals("Mail")){
			selectedMethod = "Mail";
			mail = payrollController.getMail(d.getUsername());
		} else {
			selectedMethod = "Pickup";
		}
		System.out.println("la mail è " + mail.getMail_address());
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("edit_daily.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public String updateDailyEmployee(){
		
		dEmployee.setUnion_name(selectedUnion);
		dEmployee.setPaymentMethod(selectedMethod);
		if(payrollController.checkUsername(dEmployee.getUsername())){
    		usernameAlreadyUsed = true;
    		return "failure";
    	} else {
    		payrollController.updateDailyEmployee(dEmployee, bank, mail);
    		dailyEmployees = payrollController.findAllDailyEmployees();
    		serviceChargeBean.reload();
    		return "success";	
    	}
		
		
	}
	
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	
	public Bank getBank() {
		return bank;
	}
	
	public void setMail(Mail mail) {
		this.mail = mail;
	}
	
	public Mail getMail() {
		return mail;
	}
	
	public void setSelectedMethod(String selectedMethod) {
		this.selectedMethod = selectedMethod;
	}
	
	public void setSelectedUnion(String selectedUnion) {
		this.selectedUnion = selectedUnion;
	}
	
	public void setUnionNames(ArrayList<String> unionNames) {
		this.unionNames = unionNames;
	}
	
	public String getSelectedMethod() {
		return selectedMethod;
	}
	
	public String getSelectedUnion() {
		return selectedUnion;
	}
	
	public ArrayList<String> getUnionNames() {
		return unionNames;
	}
	
	public boolean isUsernameAlreadyUsed() {
		return usernameAlreadyUsed;
	}
	

}
