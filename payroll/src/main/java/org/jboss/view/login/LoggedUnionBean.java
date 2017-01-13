package org.jboss.view.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.union.Union;
import org.jboss.view.post.PostServiceChargeBean;

@Named
@SessionScoped
public class LoggedUnionBean implements Serializable, ILogout {
	
	
	@Inject
	private PayrollController payrollController;
	
	@Inject
	private LoginUnionBean login;
	
	
	
    @PostConstruct
	public void init() {

        u = login.getU();
    }
    
    private Union u;


	public Union getU() {
		return u;
	}

	public void setU(Union u) {
		this.u = u;
	}

	@Override
	public String logout() {
		login.setUnion(new Union());
		return "success";
	}

}
