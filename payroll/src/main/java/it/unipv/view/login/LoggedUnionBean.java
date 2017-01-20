package it.unipv.view.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.controller.PayrollController;
import it.unipv.model.union.Union;
import it.unipv.view.post.PostServiceChargeBean;

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
