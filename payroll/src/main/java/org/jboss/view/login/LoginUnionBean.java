package org.jboss.view.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.union.Union;
import org.jboss.view.post.PostServiceChargeBean;
import org.primefaces.context.RequestContext;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class LoginUnionBean implements Serializable {



	@Inject
	private PayrollController payrollController;
	
	@Inject
	private LoggedUnionBean loggedUnionBean;
	
	@Inject 
	private PostServiceChargeBean postServiceChargeBean;

	private Union u = new Union();

	public String checkLogin() {
		if(u.getUsername() != null){
			Union un = payrollController.checkLoginUnion(u.getUsername(), u.getPassword());
			System.out.println(u.getUsername()+" "+u.getPassword());	
			if(un != null){
				loggedUnionBean.setU(un);
				postServiceChargeBean.reload();
				u = new Union();
	    		return "success";
	    	}else{
	    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Invalid credentials");
	    		RequestContext.getCurrentInstance().showMessageInDialog(message);
	    		return "";
	    	}
		} return "";
		
	}

	public void setUnion(Union u) {
		this.u = u;
	}
	
	
	public Union getU() {
		return u;
	}

}
