package org.jboss.view.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.union.Union;

@Named
@SessionScoped
public class LoginUnionBean implements Serializable {



	@Inject
	private PayrollController payrollController;

	private Union u = new Union();

	public String checkLogin() {

		String result = payrollController.checkLoginUnion(u.getUsername(), u.getPassword());
		System.out.println(u.getUsername()+" "+u.getPassword());	
		u = (Union) payrollController.getU();
			if (result.equals(PayrollController.SUCCESS)) {
				System.out.println(PayrollController.SUCCESS_U);
				return PayrollController.SUCCESS_U;
			}
			else {
				System.out.println(result);
				return result;
			}
//		} else {
//			return result;
//		}

	}

	public void setUnion(Union u) {
		this.u = u;
	}
	
	
	public Union getU() {
		return u;
	}

}
