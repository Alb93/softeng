package it.unipv.view.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.controller.PayrollController;
import it.unipv.model.employees.MonthlyEmployeeWithSales;

@Named
@SessionScoped
public class LoggedMonthlyBean implements Serializable, ILogout {
	
	
	
	@Inject
	private PayrollController payrollController;
	@Inject
	private LoginProxy loginProxy;
	
    @PostConstruct
	public void init() {
		loginProxy.setActualLogin(javax.enterprise.inject.spi.CDI.current().select(LoginMonthlyBean.class).get());
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
		((LoginMonthlyBean)loginProxy.getActualLogin()).setEmpl(new MonthlyEmployeeWithSales());
		return "success";
	}
    
    

}
