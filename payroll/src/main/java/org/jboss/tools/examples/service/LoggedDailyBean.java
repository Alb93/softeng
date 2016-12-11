package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.employees.DailyEmployee;

@Named
@SessionScoped
public class LoggedDailyBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
	
	private LoginProxy loginProxy;
	
    @PostConstruct
	public void init() {
    	FacesContext context = FacesContext.getCurrentInstance();
        loginProxy = (LoginProxy) context.getApplication().evaluateExpressionGet(context, "#{loginProxy}", LoginProxy.class);
        showEmp();
    }
    
    private DailyEmployee empl;
    
    
    public void showEmp() {
    	empl = (DailyEmployee) loginProxy.getActualLogin().getEmployee();
    }
    
    public DailyEmployee getEmpl() {
		return empl;
	}
    
    public void setEmpl(DailyEmployee empl) {
		this.empl = empl;
	}
    
    

}
