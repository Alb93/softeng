package org.jboss.view.registration;

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
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.union.Union;
import org.jboss.view.employees.MonthlyEmployeesBean;
import org.jboss.view.post.PostServiceChargeBean;
import org.jboss.view.utils.UnionDropdownView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class RegisterMonthlyBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	@Inject MonthlyEmployeesBean empBean;
	
	@Inject PayrollDAO payrollDAO;
	
	@Inject PostServiceChargeBean serviceBean;
    
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
    	serviceBean.reload();
    	System.out.println("Registering" + empl.getName());   
    	empl = new MonthlyEmployeeWithSales();
    	try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("admin_operations.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public MonthlyEmployeeWithSales getEmpl() {
		return empl;
	}
    
    public void setEmpl(MonthlyEmployeeWithSales empl) {
		this.empl = empl;
	}
  
}
