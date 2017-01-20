package it.unipv.tools.examples.test;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.controller.PayrollController;
import it.unipv.dao.PayrollDAO;
import it.unipv.model.employees.DailyEmployee;
import it.unipv.model.employees.MonthlyEmployeeWithSales;
import it.unipv.view.login.LoginDailyBean;
import it.unipv.view.login.LoginMonthlyBean;
import it.unipv.view.login.LoginProxy;
import it.unipv.view.registration.RegisterMonthlyBean;

@RunWith(Arquillian.class)
public class LoginMonthlyTest extends ArquillianTest {

	@Inject RegisterMonthlyBean registerMonthlyBean;
	@Inject PayrollDAO payrollDAO;
	@Inject LoginProxy loginProxy;
	
	private MonthlyEmployeeWithSales empD;

	
	@Before
	public void cleanup() {
		List<MonthlyEmployeeWithSales> monthlies = payrollDAO.findAllMonthlyEmployees();
		// Now we check if the person was in fact added
		boolean found = false;
		MonthlyEmployeeWithSales tmp = null;
		for(MonthlyEmployeeWithSales pb: monthlies) {
			if ("C".equals(pb.getName()) && "C".equals(pb.getSurname())) {
				found = true;
				empD=pb;
				tmp = pb;
				break;
			}
		}
		if(!found) {
			MonthlyEmployeeWithSales d = new MonthlyEmployeeWithSales();
			d.setName("C");
			d.setSurname("C");
			d.setUsername("C");
			d.setPassword("C");
			d.setDueRate(4.0f);
			d.setCommissionRate(0.4f);
			registerMonthlyBean.setSelectedUnion("-");
			registerMonthlyBean.setSelectedPaymentMethod("Pickup");
			registerMonthlyBean.setEmpl(d);
			registerMonthlyBean.register();
			empD = d;
		}
		
		loginProxy.setActualLogin(javax.enterprise.inject.spi.CDI.current().select(LoginMonthlyBean.class).get());
	//	loginDailyBean.getLoggedDailyBean().getLoginProxy().setActualLogin(1);

				
	}
	
	@Test
	public void testLoginMonthly() {
		
		// Set an element in the bean
		//loginDailyBean.getLoggedDailyBean().getLoginProxy().setActualLogin(1);

		((LoginMonthlyBean)loginProxy.getActualLogin()).setEmpl(empD);
		String loggedString = ((LoginMonthlyBean)loginProxy.getActualLogin()).checkLogin();
		
		// Now we assert that the entry has indeed be found
		System.out.println("CCC "+loggedString.equals(PayrollController.SUCCESS_M));
		Assert.assertEquals(loggedString, PayrollController.SUCCESS_M);
		
	}
	
	
}
