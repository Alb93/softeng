package org.jboss.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.jboss.model.employees.DailyEmployee;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class LoggedDailyBean implements Serializable {
	
	
	
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
