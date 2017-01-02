package org.jboss.view.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.Employee;
import org.jboss.model.employees.MonthlyEmployee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;

@Named
@SessionScoped
public class LoginMonthlyBean implements Serializable, ILogin {


	@Inject
	private PayrollController payrollController;

	private MonthlyEmployeeWithSales empl = new MonthlyEmployeeWithSales();

	public String checkLogin() {
		String result = payrollController.checkLogin(empl.getUsername(), empl.getPassword());
		System.out.println("il result è" + result);
		if(payrollController.isEmpSetted()){
			if (payrollController.getEmp() instanceof MonthlyEmployeeWithSales) {
				System.out.println("mi ha superato instanceof");
				empl = (MonthlyEmployeeWithSales) payrollController.getEmp();
				if (result.equals(PayrollController.SUCCESS))
					return PayrollController.SUCCESS_M;
				else
					return result;
			} else {
				return result;
			}
		} else return result;
		
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
	
	public void setEmpl(MonthlyEmployeeWithSales empl) {
		this.empl = empl;
	}

}
