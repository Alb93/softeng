package org.jboss.view;

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
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.union.Union;
import org.jboss.view.utils.UnionDropdownView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class RegisterMonthlyBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	@Inject EmployeesBean empBean;
	
	@Inject PayrollDAO payrollDAO;
    
	private UnionDropdownView dropdown;
    private MonthlyEmployeeWithSales empl;
    private ArrayList<String> unionNames;
    
    @PostConstruct
	public void init() {
		empl = new MonthlyEmployeeWithSales();
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
    
    public void register() {
    	String name = dropdown.getUnion();
    	empl.setUnion_name(name);
    	payrollController.registerEmployee(empl);
    	empBean.setMonthlyEmployees(payrollDAO.findAllMonthlyEmployees());
    	System.out.println("Registering" + empl.getName());   
    	empl = new MonthlyEmployeeWithSales();
    }
    
    public MonthlyEmployeeWithSales getEmpl() {
		return empl;
	}
    
    public void setEmpl(MonthlyEmployeeWithSales empl) {
		this.empl = empl;
	}
  
}
