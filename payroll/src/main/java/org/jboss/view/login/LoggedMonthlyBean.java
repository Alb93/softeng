package org.jboss.view.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.employees.MonthlyEmployeeWithSales;

@Named
@SessionScoped
public class LoggedMonthlyBean implements Serializable, ILogout {
	
	
	
	@Inject
	private PayrollController payrollController;
	@Inject
	private LoginProxy loginProxy;
	
    @PostConstruct
	public void init() {
    	showEmp();
    }
    
    private MonthlyEmployeeWithSales empl;
    
    
    public void showEmp() {
    	empl = (MonthlyEmployeeWithSales) loginProxy.getActualLogin().getEmployee();
    }
    
    public MonthlyEmployeeWithSales getEmpl() {
		return empl;
	}
    
    public void setEmpl(MonthlyEmployeeWithSales empl) {
		this.empl = empl;
	}

	@Override
	public String logout() {
		loginProxy.getLmb().setEmpl(new MonthlyEmployeeWithSales());
		return "success";
	}
    
    

}
