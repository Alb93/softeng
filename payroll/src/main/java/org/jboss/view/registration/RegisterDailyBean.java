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
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.union.Union;
import org.jboss.view.employees.DailyEmployeesBean;
import org.jboss.view.post.PostServiceChargeBean;
import org.jboss.view.utils.UnionDropdownView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class RegisterDailyBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	@Inject PayrollDAO payrollDAO;
	
	@Inject DailyEmployeesBean empBean;
	
	@Inject PostServiceChargeBean serviceBean;
    
    private DailyEmployee empl;
	private UnionDropdownView dropdown;
	private ArrayList<String> unionNames;

    
    @PostConstruct
	public void init() {
		empl = new DailyEmployee();
		FacesContext context = FacesContext.getCurrentInstance();
        dropdown = (UnionDropdownView) context.getApplication().evaluateExpressionGet(context, "#{unionDropdownView}", UnionDropdownView.class);
    	List<Union> unions = payrollController.findAllUnions();
    	unionNames = new ArrayList<>();
    	unionNames.add("-");
    	for (Union union : unions) {
			unionNames.add(union.getName());
			System.out.println(union.getName());
		}
    	
    	for (String name : unionNames) {
    		System.out.println(name);
		}
    	
        dropdown.setUnions(unionNames);
	}
    
    public void register() {
    	String name = dropdown.getUnion();
    	//System.out.println("nome union " + name);
    	empl.setUnion_name(name);
    	payrollController.registerEmployee(empl);
    	empBean.setDailyEmployees(payrollDAO.findAllDailyEmployees());
    	serviceBean.reload();
    	System.out.println("Registering" + empl.getName());   
    	empl = new DailyEmployee(); 
    	try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("admin_operations.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
    
    public void setEmpl(DailyEmployee empl) {
		this.empl = empl;
	}
    
    public DailyEmployee getEmpl() {
		return empl;
	}

}
