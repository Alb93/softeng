package org.jboss.tools.examples.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.view.employees.DailyEmployeesBean;
import org.jboss.view.employees.MonthlyEmployeesBean;
import org.jboss.view.registration.RegisterDailyBean;
import org.jboss.view.registration.RegisterMonthlyBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DeleteEmployeeTest extends ArquillianTest {
	
	@Inject DailyEmployeesBean dailyEmployeesBean;
	@Inject MonthlyEmployeesBean monthlyEmployeesBean;
	@Inject PayrollDAO payrollDAO;
	@Inject RegisterDailyBean registerDailyBean;
	@Inject RegisterMonthlyBean registerMonthlyBean;
	
	private DailyEmployee d = new DailyEmployee();
	private MonthlyEmployeeWithSales m = new MonthlyEmployeeWithSales();
	
	@Before
	public void cleanup() {
		List<DailyEmployee> dailys = payrollDAO.findAllDailyEmployees();
		// Now we check if the person was in fact added
		boolean found = false;
		DailyEmployee tmp = null;
		for(DailyEmployee pb: dailys) {
			if ("Pino".equals(pb.getName()) && "Pino".equals(pb.getSurname())) {
				found = true;
				tmp = pb;
				break;
			}
		}
		if(found)
			payrollDAO.removeDailyEmployee(tmp.getId());
		
		List<MonthlyEmployeeWithSales> monthlys = payrollDAO.findAllMonthlyEmployees();
		found = false;
		MonthlyEmployeeWithSales tmp2 = null;
		for (MonthlyEmployeeWithSales monthlyEmployeeWithSales : monthlys) {
			if ("Pino".equals(monthlyEmployeeWithSales.getName()) && "Pino".equals(monthlyEmployeeWithSales.getSurname())) {
				found = true;
				tmp2 = monthlyEmployeeWithSales;
				break;
			}
		}
		if(found)
			payrollDAO.removeMonthlyEmployee(tmp2.getId());
	}

	@Test
	public void testDeleteDailyEmp() throws IOException{
		d.setName("Pino");
		d.setSurname("Pino");
		d.setUsername("Pino");
		d.setPassword("Pino");
		d.setDueRate(4.0f);
		d.setHourlyRate(3.0f);
		registerDailyBean.setSelectedUnion("-");
		registerDailyBean.setSelectedPaymentMethod("Pickup");
		registerDailyBean.setEmpl(d);
		registerDailyBean.register();
		dailyEmployeesBean.getDailyEmployees().add(d);
		dailyEmployeesBean.removeDailyEmployee(d);
		List<DailyEmployee> dailyEmployees = payrollDAO.findAllDailyEmployees();
		boolean notFound = true;
		for (DailyEmployee dailyEmployee : dailyEmployees) {
			if(dailyEmployee.getName().equals("Pino") && dailyEmployee.getSurname().equals("Pino")){
				notFound = false;
			}
		}
		
		Assert.assertTrue("Pino was deleted", notFound);
		
		
	}
	
	@Test
	public void testDeleteMonthlyEmp() throws IOException{
		m.setName("Pino");
		m.setSurname("Pino");
		m.setUsername("Pino");
		m.setPassword("Pino");
		m.setDueRate(4.0f);
		m.setCommissionRate(4.0f);
		m.setSalary(4.0f);
		registerMonthlyBean.setSelectedUnion("-");
		registerMonthlyBean.setSelectedPaymentMethod("Pickup");
		registerMonthlyBean.setEmpl(m);
		registerMonthlyBean.register();
		monthlyEmployeesBean.getMonthlyEmployees().add(m);
		monthlyEmployeesBean.removeMonthlyEmployee(m);
		List<MonthlyEmployeeWithSales> monthlyEmployees = payrollDAO.findAllMonthlyEmployees();
		boolean notFound = true;
		for (MonthlyEmployeeWithSales monthlyEmployeeWithSales : monthlyEmployees) {
			if(monthlyEmployeeWithSales.getName().equals("Pino") && monthlyEmployeeWithSales.getSurname().equals("Pino")){
				notFound = false;
			}
		}
		
		Assert.assertTrue("Pino was deleted", notFound);
		
		
	}

}
