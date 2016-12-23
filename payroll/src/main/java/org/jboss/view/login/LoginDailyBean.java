package org.jboss.view.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.Employee;

@Named
@SessionScoped
public class LoginDailyBean implements Serializable, ILogin {


	@Inject
	private PayrollController payrollController;

	private DailyEmployee empl = new DailyEmployee();

	public String checkLogin() {

		String result = payrollController.checkLogin(empl.getUsername(), empl.getPassword());
		System.out.println("INST D = "+ (payrollController.getEmp() instanceof DailyEmployee));
		//if (payrollController.getEmp() instanceof DailyEmployee) {
		System.out.println("GETEMP ="+payrollController.getEmp());
			empl = (DailyEmployee) payrollController.getEmp();
			System.out.println("TEST = "+empl.getHourlyRate());
			if (result.equals(PayrollController.SUCCESS))
				return PayrollController.SUCCESS_D;
			else
				return result;
//		} else {
//			return result;
//		}

	}

	public void setEmpl(DailyEmployee empl) {
		this.empl = empl;
	}

	@Override
	public Employee getEmployee() {
		// TODO Auto-generated method stub
		return empl;
	}

	@Override
	public String getSuccessfulString() {
		// TODO Auto-generated method stub
		return PayrollController.SUCCESS_D;
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
