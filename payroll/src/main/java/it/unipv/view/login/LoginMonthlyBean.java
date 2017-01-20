package it.unipv.view.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.controller.PayrollController;
import it.unipv.model.employees.DailyEmployee;
import it.unipv.model.employees.Employee;
import it.unipv.model.employees.MonthlyEmployee;
import it.unipv.model.employees.MonthlyEmployeeWithSales;

@Named
@SessionScoped
public class LoginMonthlyBean implements Serializable, ILogin {


	@Inject
	private PayrollController payrollController;
	
	@Inject
	private LoggedMonthlyBean loggedMonthlyBean;

	private MonthlyEmployeeWithSales empl = new MonthlyEmployeeWithSales();

	public String checkLogin() {
		String result = payrollController.checkLogin(empl.getUsername(), empl.getPassword());
		System.out.println("il result è" + result);
		if(payrollController.isEmpSetted()){
			if (payrollController.getEmp() instanceof MonthlyEmployeeWithSales) {
				System.out.println("mi ha superato instanceof");
				empl = (MonthlyEmployeeWithSales) payrollController.getEmp();
				loggedMonthlyBean.setEmpl(empl);
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
