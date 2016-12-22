package org.jboss.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.union.Union;
import org.jboss.view.utils.UnionDropdownView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class EmployeesBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	@Inject PayrollDAO payrollDAO;
	private List<DailyEmployee> dailyEmployees;
	private DailyEmployee dEmployee;
	private MonthlyEmployeeWithSales mEmployee;
	private List<MonthlyEmployeeWithSales> monthlyEmployees;
	private UnionDropdownView dropdown;
	private ArrayList<String> unionNames;
	
	@PostConstruct
	public void init() {
		dailyEmployees = payrollDAO.findAllDailyEmployees();
		monthlyEmployees = payrollDAO.findAllMonthlyEmployees();
		FacesContext context = FacesContext.getCurrentInstance();
        dropdown = (UnionDropdownView) context.getApplication().evaluateExpressionGet(context, "#{unionDropdownView}", UnionDropdownView.class);
    	List<Union> unions = payrollController.findAllUnions();
    	unionNames = new ArrayList<>();
    	unionNames.add("-");
    	for (Union union : unions) {
			unionNames.add(union.getName());
			System.out.println(union.getName());
		}  	
        dropdown.setUnions(unionNames);
		
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
	
	public void removeDailyEmployee(DailyEmployee emp) throws IOException{
		System.out.println("Cancellando" + emp.getName());	
		payrollDAO.removeDailyEmployee(emp.getId());	
		dailyEmployees.remove(dailyEmployees.indexOf(emp));
	}
	
	public void removeMonthlyEmployee(MonthlyEmployeeWithSales emp) throws IOException{
		System.out.println("Cancellando" + emp.getName());	
		payrollDAO.removeMonthlyEmployee(emp.getId());
		monthlyEmployees.remove(monthlyEmployees.indexOf(emp));
	}
	
	public void goToEditDailyPage(DailyEmployee d){
		setdEmployee(d);
		if(!(d.getUnion_name().equals("-"))){
			Union union = payrollController.findCorrespondingUnion(d.getUnion_name());
			dropdown.setUnion(union.getName());
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("edit_daily.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void goToEditMonthlyPage(MonthlyEmployeeWithSales m){
		setmEmployee(m);
		if(!(m.getUnion_name().equals("-"))){
			Union union = payrollController.findCorrespondingUnion(m.getUnion_name());
			dropdown.setUnion(union.getName());
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("edit_monthly.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void updateDailyEmployee(){
	
		dEmployee.setUnion_name(dropdown.getUnion());
		payrollController.updateDailyEmployee(dEmployee);
		
		
		
	}
	
	public void updateMonthlyEmployee(){
		
		mEmployee.setUnion_name(dropdown.getUnion());
		payrollController.updateMonthlyEmployee(mEmployee);
		
		
		
	}
	
	
	
	

}
