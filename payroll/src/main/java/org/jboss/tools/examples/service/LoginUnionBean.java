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
import org.jboss.tools.examples.model.employees.Employee;
import org.jboss.tools.examples.model.employees.MonthlyEmployeeWithSales;
import org.jboss.tools.examples.model.union.Union;

@Named
@SessionScoped
public class LoginUnionBean implements Serializable {

	@Inject
	private Logger log;

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
