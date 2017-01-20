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
import it.unipv.view.employees.DailyEmployeesBean;
import it.unipv.view.employees.MonthlyEmployeesBean;
import it.unipv.view.registration.RegisterDailyBean;
import it.unipv.view.registration.RegisterMonthlyBean;

@RunWith(Arquillian.class)
public class EditEmployeeTest extends ArquillianTest {
	
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
	public void editDailyTest(){
		d.setName("Pino");
		d.setSurname("Pino");
		d.setUsername("Pino");
		d.setPassword("Pino");
		d.setDueRate(4);
		d.setHourlyRate(3);
		registerDailyBean.setSelectedUnion("-");
		registerDailyBean.setSelectedPaymentMethod("Pickup");
		registerDailyBean.setEmpl(d);
		registerDailyBean.register();
		
		float newHourlyRate = 5;
		float newDueRate = 4;
		String newUnion = "union1";
		String newPaymentMethod = "Mail";
		
		d.setHourlyRate(newHourlyRate);
		d.setDueRate(newDueRate);
		dailyEmployeesBean.setSelectedMethod(newPaymentMethod);
		dailyEmployeesBean.setSelectedUnion(newUnion);
		dailyEmployeesBean.setdEmployee(d);
		
		dailyEmployeesBean.updateDailyEmployee();
		
		List<DailyEmployee> dailyEmployees = payrollDAO.findAllDailyEmployees();
		boolean found = true;
		for (DailyEmployee dailyEmployee : dailyEmployees) {
			if("union1".equals(dailyEmployee.getUnion_name()) && "Mail".equals(dailyEmployee.getPaymentMethod()) &&
					dailyEmployee.getDueRate() == 4 && dailyEmployee.getHourlyRate() == 5){
				found = true;
				break;
				
			}
		}
		
		Assert.assertTrue("Pino was modified", found);
	}
	
	@Test
	public void editMonthlyTest(){
		m.setName("Pino");
		m.setSurname("Pino");
		m.setUsername("Pino");
		m.setPassword("Pino");
		m.setDueRate(4);
		m.setSalary(5);
		registerMonthlyBean.setSelectedUnion("-");
		registerMonthlyBean.setSelectedPaymentMethod("Pickup");
		registerMonthlyBean.setEmpl(m);
		registerMonthlyBean.register();
		
		float newSalary = 5;
		float newDueRate = 4;
		String newUnion = "union1";
		String newPaymentMethod = "Mail";
		
		m.setSalary(newSalary);
		m.setDueRate(newDueRate);
		monthlyEmployeesBean.setSelectedMethod(newPaymentMethod);
		monthlyEmployeesBean.setSelectedUnion(newUnion);
		monthlyEmployeesBean.setmEmployee(m);
		
		monthlyEmployeesBean.updateMonthlyEmployee();
		
		List<MonthlyEmployeeWithSales> monthlyEmployees = payrollDAO.findAllMonthlyEmployees();
		boolean found = true;
		for (MonthlyEmployeeWithSales monthlyEmployee : monthlyEmployees) {
			if("union1".equals(monthlyEmployee.getUnion_name()) && "Mail".equals(monthlyEmployee.getPaymentMethod()) &&
					monthlyEmployee.getDueRate() == 4 && monthlyEmployee.getSalary() == 5){
				found = true;
				break;
				
			}
		}
		
		Assert.assertTrue("Pino was modified", found);
	}

}
