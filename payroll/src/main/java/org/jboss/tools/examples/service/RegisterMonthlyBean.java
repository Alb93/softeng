package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.dao.PayrollDAO;
import org.jboss.tools.examples.model.employees.MonthlyEmployeeWithSales;
import org.jboss.tools.examples.model.union.Union;
import org.jboss.tools.examples.utils.UnionDropdownView;

@Named
@SessionScoped
public class RegisterMonthlyBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
	
	@Inject EmployeesBean empBean;
	
	@Inject PayrollDAO payrollDAO;
    
	private UnionDropdownView dropdown;
    private MonthlyEmployeeWithSales empl;
    
    @PostConstruct
	public void init() {
		empl = new MonthlyEmployeeWithSales();
		FacesContext context = FacesContext.getCurrentInstance();
        dropdown = (UnionDropdownView) context.getApplication().evaluateExpressionGet(context, "#{unionDropdownView}", UnionDropdownView.class);
    	List<Union> unions = payrollController.findAllUnions();
    	Map<String,Long> list = new HashMap<>();
    	for (Union union : unions) {
			list.put(union.getName(),union.getId());
			System.out.println(union.getName());
		}
    	
        dropdown.setUnions(list);
	}
    
    public void register() {
    	empl.setUnion_id(Long.parseLong(dropdown.getCountry()));
    	payrollController.registerEmployee(empl);
    	empBean.setMonthlyEmployees(payrollDAO.findAllMonthlyEmployees());
  //  	System.out.println(dropdown.getCountry()+" Djj");
        log.info("Registering " + empl.getName());
    }
    
    public MonthlyEmployeeWithSales getEmpl() {
		return empl;
	}
    
    public void setEmpl(MonthlyEmployeeWithSales empl) {
		this.empl = empl;
	}
  
}
