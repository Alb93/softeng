package org.jboss.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.employees.Employee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;

@Named
@SessionScoped
public class LoginMonthlyBean implements Serializable, ILogin {


	@Inject
	private PayrollController payrollController;

	private MonthlyEmployeeWithSales empl = new MonthlyEmployeeWithSales();

	public String checkLogin() {
		String result = payrollController.checkLogin(empl.getUsername(), empl.getPassword());
		System.out.println("INST M = "+ (payrollController.getEmp() instanceof MonthlyEmployeeWithSales));
		if (payrollController.getEmp() instanceof MonthlyEmployeeWithSales) {
			empl = (MonthlyEmployeeWithSales) payrollController.getEmp();
			if (result.equals(PayrollController.SUCCESS))
				return PayrollController.SUCCESS_M;
			else
				return result;
		} else {
			return result;
		}
	}

	@Override
	public Employee getEmployee() {
		// TODO Auto-generated method stub
		return empl;
	}

	@Override
	public String getSuccessfulString() {
		// TODO Auto-generated method stub
		return PayrollController.SUCCESS_M;
	}

	@Override
	public void setUsernameI(String username) {
		empl.setUsername(username);

	}

	@Override
	public void setPasswordI(String password) {
		empl.setPassword(password);
	}

}
