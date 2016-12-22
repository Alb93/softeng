package org.jboss.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.union.Union;

@Named
@SessionScoped
public class RegisterUnionBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
    
    private Union u;
    
    
    @PostConstruct
	public void init() {
		u = new Union();
	}
    
    public void register() {
    	payrollController.registerUnion(u);
    }
    
    public void setEmpl(Union u) {
		this.u = u;
	}
    
    public Union getUnion() {
		return u;
	}

}
