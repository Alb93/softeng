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
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.union.Union;
import org.jboss.view.post.PostServiceChargeBean;
import org.jboss.view.utils.UnionDropdownView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class DailyEmployeesBean implements Serializable {
	
	@Inject
	private PayrollController payrollController;
	
	@Inject PayrollDAO payrollDAO;
	@Inject PostServiceChargeBean serviceChargeBean;
	private List<DailyEmployee> dailyEmployees;
	private DailyEmployee dEmployee;
	private UnionDropdownView dropdown;
	private ArrayList<String> unionNames;
	
	@PostConstruct
	public void init() {
		dailyEmployees = payrollDAO.findAllDailyEmployees();
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
	
	public void removeDailyEmployee(DailyEmployee emp) throws IOException{
		System.out.println("Cancellando" + emp.getName());	
		payrollDAO.removeDailyEmployee(emp.getId());	
		dailyEmployees.remove(dailyEmployees.indexOf(emp));
		serviceChargeBean.reload();
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
	
	public void updateDailyEmployee(){
		
		dEmployee.setUnion_name(dropdown.getUnion());
		payrollController.updateDailyEmployee(dEmployee);
		dailyEmployees = payrollDAO.findAllDailyEmployees();
		serviceChargeBean.reload();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("edit_employee.jsf");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("You can't delete it."));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}

}
