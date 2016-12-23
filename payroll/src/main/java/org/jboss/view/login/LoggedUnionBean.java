package org.jboss.view.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.union.Union;

@Named
@SessionScoped
public class LoggedUnionBean implements Serializable {
	
	
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
