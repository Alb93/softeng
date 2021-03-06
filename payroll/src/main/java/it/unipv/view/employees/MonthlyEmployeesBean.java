package it.unipv.view.employees;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.controller.PayrollController;
import it.unipv.dao.PayrollDAO;
import it.unipv.model.employees.DailyEmployee;
import it.unipv.model.employees.MonthlyEmployeeWithSales;
import it.unipv.model.payment.Bank;
import it.unipv.model.payment.Mail;
import it.unipv.model.union.Union;
import it.unipv.view.post.PostServiceChargeBean;
import it.unipv.view.utils.PaymentDropdownView;
import it.unipv.view.utils.UnionDropdownView;

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
	private String selectedUnion = "-";
	private ArrayList<String> unionNames;
	private String selectedMethod = "Mail";
	
	@PostConstruct
	public void init() {
		monthlyEmployees = payrollController.findAllMonthlyEmployees();		
    	List<Union> unions = payrollController.findAllUnions();
    	unionNames = new ArrayList<>();
    	unionNames.add("-");
    	for (Union union : unions) {
			unionNames.add(union.getName());
			System.out.println(union.getName());
		}  	
		
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
			selectedUnion = union.getName();
		}
		if(m.getPaymentMethod().equals("Bank")){
			//query bank
			selectedMethod = "Bank";
			bank = payrollController.getBank(m.getUsername());
			
		}
		if(m.getPaymentMethod().equals("Mail")){
			selectedMethod = "Mail";
			mail = payrollController.getMail(m.getUsername());
		} else {
			selectedMethod = "Pickup";
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("edit_monthly.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public String updateMonthlyEmployee(){
		
		mEmployee.setUnion_name(selectedUnion);
		System.out.println("il comm rate è" + mEmployee.getCommissionRate());
		if(selectedUnion.equals("-")){
			mEmployee.setDueRate(0);
		}
		mEmployee.setPaymentMethod(selectedMethod);
		payrollController.updateMonthlyEmployee(mEmployee, bank, mail);
    	monthlyEmployees = payrollController.findAllMonthlyEmployees();
    	serviceChargeBean.reload();
    	return "success";	
    		
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

}
