package org.jboss.view.login;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.union.Union;
import org.primefaces.context.RequestContext;

@Named
@SessionScoped
public class LoginUnionBean implements Serializable {



	@Inject
	private PayrollController payrollController;

	private Union u = new Union();

	public void checkLogin() {
		if(u.getUsername() != null){
			Union un = payrollController.checkLoginUnion(u.getUsername(), u.getPassword());
			System.out.println(u.getUsername()+" "+u.getPassword());	
			if(un != null){
	    		try {
	    			u = un;
					FacesContext.getCurrentInstance().getExternalContext().redirect("post_service_charge.jsf");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}else{
	    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Invalid credentials");
	    		RequestContext.getCurrentInstance().showMessageInDialog(message);
	    	}
		}
		
	}

	public void setUnion(Union u) {
		this.u = u;
	}
	
	
	public Union getU() {
		return u;
	}

}
