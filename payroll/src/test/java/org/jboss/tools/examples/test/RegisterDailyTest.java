package org.jboss.tools.examples.test;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.payment.Bank;
import org.jboss.model.payment.Mail;
import org.jboss.view.employees.DailyEmployeesBean;
import org.jboss.view.registration.RegisterDailyBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RegisterDailyTest extends ArquillianTest {

	@Inject RegisterDailyBean registerDailyBean;
	@Inject PayrollDAO payrollDAO;
	
	@Before
	public void cleanup() {
		List<DailyEmployee> dailys = payrollDAO.findAllDailyEmployees();
		// Now we check if the person was in fact added
		boolean found = false;
		DailyEmployee tmp = null;
		for(DailyEmployee pb: dailys) {
			if ("Mario".equals(pb.getName()) && "Rossi".equals(pb.getSurname())) {
				found = true;
				tmp = pb;
				break;
			}
		}
		if(found)
			payrollDAO.removeDailyEmployee(tmp.getId());
	}
	
	@Test
	public void testRegisterDaily() {
		
		// Set an element in the bean
		DailyEmployee d = new DailyEmployee();
		d.setName("Mario");
		d.setSurname("Rossi");
		d.setUsername("Ma");
		d.setPassword("Ma");
		d.setDueRate(4.0f);
		d.setHourlyRate(3.0f);
		registerDailyBean.setSelectedUnion("-");
		registerDailyBean.setSelectedPaymentMethod("Pickup");
		registerDailyBean.setEmpl(d);
		registerDailyBean.register();

		List<DailyEmployee> dailys = payrollDAO.findAllDailyEmployees();
		// Now we check if the person was in fact added
		boolean found = false;
		for(DailyEmployee pb: dailys) {
			if ("Mario".equals(pb.getName()) && "Rossi".equals(pb.getSurname())) {
				found = true;
				break;
			}
		}
		
		// Now we assert that the entry has indeed be found
		Assert.assertTrue("Mario was found", found);
		
	}
	
	
}
