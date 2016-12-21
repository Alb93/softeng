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
import org.jboss.tools.examples.model.employees.DailyEmployee;
import org.jboss.tools.examples.model.union.Union;
import org.jboss.tools.examples.utils.UnionDropdownView;

@Named
@SessionScoped
public class RegisterDailyBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
	
	@Inject PayrollDAO payrollDAO;
	
	@Inject EmployeesBean empBean;
    
    private DailyEmployee empl;
	private UnionDropdownView dropdown;
	private Map<String,Long> list;

    
    @PostConstruct
	public void init() {
    	System.out.println("INIZIALIZZANDO dailylist");
		empl = new DailyEmployee();
		FacesContext context = FacesContext.getCurrentInstance();
        dropdown = (UnionDropdownView) context.getApplication().evaluateExpressionGet(context, "#{unionDropdownView}", UnionDropdownView.class);
    	List<Union> unions = payrollController.findAllUnions();
    	list = new HashMap<>();
    	for (Union union : unions) {
			list.put(union.getName(),union.getId());
			System.out.println(union.getName());
		}
    	
        dropdown.setUnions(list);
	}
    
    public void register() {
    	String id = dropdown.getCountry();
    	if(!id.equals("null")){
    		empl.setUnion_id(Long.parseLong(dropdown.getCountry()));
    	}
    	payrollController.registerEmployee(empl);
    	empBean.setDailyEmployees(payrollDAO.findAllDailyEmployees());
    	empl = new DailyEmployee();
        log.info("Registering " + empl.getName());
        
    }
    
    public void setEmpl(DailyEmployee empl) {
		this.empl = empl;
	}
    
    public DailyEmployee getEmpl() {
		return empl;
	}

}
