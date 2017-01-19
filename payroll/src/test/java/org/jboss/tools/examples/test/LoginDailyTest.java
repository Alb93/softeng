package org.jboss.tools.examples.test;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.controller.PayrollController;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.payment.Bank;
import org.jboss.model.payment.Mail;
import org.jboss.view.employees.DailyEmployeesBean;
import org.jboss.view.login.LoggedDailyBean;
import org.jboss.view.login.LoginDailyBean;
import org.jboss.view.login.LoginProxy;
import org.jboss.view.registration.RegisterDailyBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class LoginDailyTest extends ArquillianTest {

	@Inject RegisterDailyBean registerDailyBean;
	@Inject PayrollDAO payrollDAO;
	@Inject LoginProxy loginProxy;
	
	private DailyEmployee empD;

	
	@Before
	public void cleanup() {
		List<DailyEmployee> dailys = payrollDAO.findAllDailyEmployees();
		// Now we check if the person was in fact added
		boolean found = false;
		DailyEmployee tmp = null;
		for(DailyEmployee pb: dailys) {
			if ("A".equals(pb.getName()) && "A".equals(pb.getSurname())) {
				found = true;
				empD=pb;
				tmp = pb;
				break;
			}
		}
		if(!found) {
			DailyEmployee d = new DailyEmployee();
			d.setName("A");
			d.setSurname("A");
			d.setUsername("A");
			d.setPassword("A");
			d.setDueRate(4.0f);
			d.setHourlyRate(3.0f);
			registerDailyBean.setSelectedUnion("-");
			registerDailyBean.setSelectedPaymentMethod("Pickup");
			registerDailyBean.setEmpl(d);
			registerDailyBean.register();
			empD = d;
		}
		loginProxy.setActualLogin(javax.enterprise.inject.spi.CDI.current().select(LoginDailyBean.class).get());
	//	loginDailyBean.getLoggedDailyBean().getLoginProxy().setActualLogin(1);

				
	}
	
	@Test
	public void testLoginDaily() {
		
		// Set an element in the bean
		//loginDailyBean.getLoggedDailyBean().getLoginProxy().setActualLogin(1);

		((LoginDailyBean)loginProxy.getActualLogin()).setEmpl(empD);
		String loggedString = ((LoginDailyBean)loginProxy.getActualLogin()).checkLogin();
		
		// Now we assert that the entry has indeed be found
		System.out.println("CCC "+loggedString.equals(PayrollController.SUCCESS_D));
		Assert.assertEquals(loggedString, PayrollController.SUCCESS_D);
		
	}
	
	
}
