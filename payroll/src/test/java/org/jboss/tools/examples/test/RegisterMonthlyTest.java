package org.jboss.tools.examples.test;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.payment.Bank;
import org.jboss.model.payment.Mail;
import org.jboss.view.employees.DailyEmployeesBean;
import org.jboss.view.registration.RegisterDailyBean;
import org.jboss.view.registration.RegisterMonthlyBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RegisterMonthlyTest extends ArquillianTest {

	@Inject RegisterMonthlyBean registerMonthlyBean;
	@Inject PayrollDAO payrollDAO;
	
	@Before
	public void cleanup() {
		List<MonthlyEmployeeWithSales> monthlies = payrollDAO.findAllMonthlyEmployees();
		// Now we check if the person was in fact added
		boolean found = false;
		MonthlyEmployeeWithSales tmp = null;
		for(MonthlyEmployeeWithSales pb: monthlies) {
			if ("Mario".equals(pb.getName()) && "Rossi".equals(pb.getSurname())) {
				found = true;
				tmp = pb;
				break;
			}
		}
		if(found)
			payrollDAO.removeMonthlyEmployee(tmp.getId());
	}
	
	@Test
	public void testRegisterMonthly() {
		
		// Set an element in the bean
		MonthlyEmployeeWithSales d = new MonthlyEmployeeWithSales();
		d.setName("Mario");
		d.setSurname("Rossi");
		d.setUsername("MaR");
		d.setPassword("Ma");
		d.setDueRate(4.0f);
		d.setCommissionRate(0.4f);
		registerMonthlyBean.setSelectedUnion("-");
		registerMonthlyBean.setSelectedPaymentMethod("Pickup");
		registerMonthlyBean.setEmpl(d);
		System.out.println(registerMonthlyBean.register());
		

		List<MonthlyEmployeeWithSales> monthlies = payrollDAO.findAllMonthlyEmployees();
		// Now we check if the person was in fact added
		boolean found = false;
		for(MonthlyEmployeeWithSales pb: monthlies) {
			if ("Mario".equals(pb.getName()) && "Rossi".equals(pb.getSurname())) {
				found = true;
				break;
			}
		}
		System.out.println(found);
		// Now we assert that the entry has indeed be found
		Assert.assertTrue("Mario was found", found);
		
	}
	
	
}
