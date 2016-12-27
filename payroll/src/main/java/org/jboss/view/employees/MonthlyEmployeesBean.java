package org.jboss.view.employees;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.payment.Bank;
import org.jboss.model.payment.Mail;
import org.jboss.model.union.Union;
import org.jboss.view.post.PostServiceChargeBean;
import org.jboss.view.utils.PaymentDropdownView;
import org.jboss.view.utils.UnionDropdownView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class MonthlyEmployeesBean implements Serializable {
	
	@Inject
	private PayrollController payrollController;
	
	@Inject PostServiceChargeBean serviceChargeBean;
	private Bank bank = new Bank();
    private Mail mail = new Mail();
	private MonthlyEmployeeWithSales mEmployee;
	private List<MonthlyEmployeeWithSales> monthlyEmployees;
	private UnionDropdownView dropdown;
	private PaymentDropdownView paymentDropdownView;
	private ArrayList<String> unionNames;
	
	@PostConstruct
	public void init() {
		monthlyEmployees = payrollController.findAllMonthlyEmployees();
		FacesContext context = FacesContext.getCurrentInstance();
        dropdown = (UnionDropdownView) context.getApplication().evaluateExpressionGet(context, "#{unionDropdownView}", UnionDropdownView.class);
        paymentDropdownView = (PaymentDropdownView) context.getApplication().evaluateExpressionGet(context, "#{paymentDropdownView}", PaymentDropdownView.class);
    	List<Union> unions = payrollController.findAllUnions();
    	unionNames = new ArrayList<>();
    	unionNames.add("-");
    	for (Union union : unions) {
			unionNames.add(union.getName());
			System.out.println(union.getName());
		}  	
        dropdown.setUnions(unionNames);
		
	}
	
	public void setMonthlyEmployees(List<MonthlyEmployeeWithSales> monthlyEmployees) {
		this.monthlyEmployees = monthlyEmployees;
	}
	
	public List<MonthlyEmployeeWithSales> getMonthlyEmployees() {
		return monthlyEmployees;
	}
	
	public void setmEmployee(MonthlyEmployeeWithSales mEmployee) {
		this.mEmployee = mEmployee;
	}
	
	public MonthlyEmployeeWithSales getmEmployee() {
		return mEmployee;
	}
	
	public void removeMonthlyEmployee(MonthlyEmployeeWithSales emp) throws IOException{
		System.out.println("Cancellando" + emp.getName());	
		payrollController.removeMonthlyEmployee(emp.getId());
		monthlyEmployees.remove(monthlyEmployees.indexOf(emp));
		serviceChargeBean.reload();
	}
	
	public void goToEditMonthlyPage(MonthlyEmployeeWithSales m){
		setmEmployee(m);
		if(!(m.getUnion_name().equals("-"))){
			Union union = payrollController.findCorrespondingUnion(m.getUnion_name());
			dropdown.setUnion(union.getName());
		}
		if(m.getPaymentMethod().equals("Bank")){
			//query bank
			paymentDropdownView.setMethod("Bank");
			bank = payrollController.getBank(m.getUsername());
			
		}
		if(m.getPaymentMethod().equals("Mail")){
			paymentDropdownView.setMethod("Mail");
			mail = payrollController.getMail(m.getUsername());
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("edit_monthly.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void updateMonthlyEmployee(){
		
		mEmployee.setUnion_name(dropdown.getUnion());
		mEmployee.setPaymentMethod(paymentDropdownView.getMethod());
		payrollController.updateMonthlyEmployee(mEmployee, bank, mail);
		monthlyEmployees = payrollController.findAllMonthlyEmployees();
		serviceChargeBean.reload();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("edit_employee.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

}
