package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.employees.MonthlyEmployeeWithSales;
import org.jboss.tools.examples.model.union.Union;

@Named
@SessionScoped
public class LoggedUnionBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
	
	private LoginUnionBean login;
	
    @PostConstruct
	public void init() {
    	FacesContext context = FacesContext.getCurrentInstance();
    	System.out.println("HERE");
        login = (LoginUnionBean) context.getApplication().evaluateExpressionGet(context, "#{loginUnionBean}", LoginUnionBean.class);
    	System.out.println("THERE");

        u = login.getU();
    }
    
    private Union u;


	public Union getU() {
		return u;
	}

	public void setU(Union u) {
		this.u = u;
	}

}
