package org.jboss.tools.examples.test;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.controller.PayrollController;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.view.login.LoginProxy;
import org.jboss.view.registration.RegisterMonthlyBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
		loginProxy.setActualLogin(0);
	//	loginDailyBean.getLoggedDailyBean().getLoginProxy().setActualLogin(1);

				
	}
	
	@Test
	public void testLoginDaily() {
		
		// Set an element in the bean
		//loginDailyBean.getLoggedDailyBean().getLoginProxy().setActualLogin(1);

		loginProxy.getLmb().setEmpl(empD);
		String loggedString = loginProxy.getLmb().checkLogin();
		
		// Now we assert that the entry has indeed be found
		System.out.println("CCC "+loggedString.equals(PayrollController.SUCCESS_M));
		Assert.assertEquals(loggedString, PayrollController.SUCCESS_M);
		
	}
	
	
}
