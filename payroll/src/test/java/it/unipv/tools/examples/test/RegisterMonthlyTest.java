package it.unipv.tools.examples.test;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.dao.PayrollDAO;
import it.unipv.model.employees.DailyEmployee;
import it.unipv.model.employees.MonthlyEmployeeWithSales;
import it.unipv.model.payment.Bank;
import it.unipv.model.payment.Mail;
import it.unipv.view.employees.DailyEmployeesBean;
import it.unipv.view.registration.RegisterDailyBean;
import it.unipv.view.registration.RegisterMonthlyBean;

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
