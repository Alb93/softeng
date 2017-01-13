package org.jboss.tools.examples.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.salesreceipt.SalesReceipt;
import org.jboss.model.timecard.TimeCard;
import org.jboss.view.payroll.PayrollPaymentBean;
import org.jboss.view.post.PostSalesReceiptBean;
import org.jboss.view.post.PostTimeCardBean;
import org.jboss.view.registration.RegisterDailyBean;
import org.jboss.view.registration.RegisterMonthlyBean;
import org.jboss.view.utils.CalendarView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junit.framework.Assert;

@RunWith(Arquillian.class)
public class PayrollRunTest extends ArquillianTest {

	@Inject
	PayrollDAO payrollDAO;
	@Inject
	RegisterDailyBean registerDailyBean;
	@Inject
	RegisterMonthlyBean registerMonthlyBean;
	@Inject
	PayrollPaymentBean payrollPaymentBean;
	@Inject
	CalendarView calendarView;
	@Inject
	PostTimeCardBean postTimeCardBean;
	@Inject
	PostSalesReceiptBean postSalesReceiptBean;

	@Before
	public void cleanup() {
		List<DailyEmployee> dailys = payrollDAO.findAllDailyEmployees();
		// Now we check if the person was in fact added
		boolean found = false;
		DailyEmployee tmp = null;
		for (DailyEmployee pb : dailys) {
			if ("Mario".equals(pb.getName()) && "Rossi".equals(pb.getSurname())) {
				found = true;
				tmp = pb;
				break;
			}
		}
		if (found)
			payrollDAO.removeDailyEmployee(tmp.getId());

		List<MonthlyEmployeeWithSales> monthlys = payrollDAO.findAllMonthlyEmployees();
		found = false;
		MonthlyEmployeeWithSales tmp2 = null;
		for (MonthlyEmployeeWithSales monthlyEmployeeWithSales : monthlys) {
			if ("Mario".equals(monthlyEmployeeWithSales.getName())
					&& "Bianchi".equals(monthlyEmployeeWithSales.getSurname())) {
				found = true;
				tmp2 = monthlyEmployeeWithSales;
				break;
			}
		}
		if (found)
			payrollDAO.removeMonthlyEmployee(tmp2.getId());
		found = false;
		for (MonthlyEmployeeWithSales monthlyEmployeeWithSales : monthlys) {
			if ("Mario".equals(monthlyEmployeeWithSales.getName())
					&& "Verdi".equals(monthlyEmployeeWithSales.getSurname())) {
				found = true;
				tmp2 = monthlyEmployeeWithSales;
				break;
			}
		}
		if (found)
			payrollDAO.removeMonthlyEmployee(tmp2.getId());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testDailyPayment() throws ParseException {

		// it's friday
		calendarView.setDate(new SimpleDateFormat("yyyyMMdd").parse("20170120"));
		payrollPaymentBean.setDate(new SimpleDateFormat("yyyyMMdd").parse("20170120"));

		float hourlyRate = 3;
		float dueRate = 4;

		// this is a daily emp
		DailyEmployee d = new DailyEmployee();
		d.setName("Mario");
		d.setSurname("Rossi");
		d.setUsername("Mario");
		d.setPassword("Rossi");
		d.setDueRate(dueRate);
		d.setHourlyRate(hourlyRate);
		registerDailyBean.setSelectedUnion("union1");
		registerDailyBean.setSelectedPaymentMethod("Pickup");
		registerDailyBean.setEmpl(d);
		registerDailyBean.register();

		List<DailyEmployee> empList = payrollDAO.findAllDailyEmployees();
		int empId = (empList.get(empList.size() - 1)).getId();
		d.setId(empId);

		List<TimeCard> timeCards = payrollDAO.findTimeCardsOfThisEmp(empId);
		boolean found = false;
		TimeCard tmp = null;
		for (TimeCard tc : timeCards) {
			if (tc.getEmp_id() == empId) {
				found = true;
				tmp = tc;
				break;
			}
		}
		if (found)
			payrollDAO.removeTimeCard(tmp.getEmp_id());

		// the emp has posted a time card on 19/01/2017
		TimeCard timeCard = new TimeCard();
		Date date = new SimpleDateFormat("yyyyMMdd").parse("20170119");
		float hours = 8;
		timeCard.setHours(hours);
		calendarView.setDate(date);
		postTimeCardBean.setCard(timeCard);
		postTimeCardBean.post(empId);

		float dailyPayment = (hours * hourlyRate) - (dueRate);

		Assert.assertEquals(dailyPayment, payrollPaymentBean.calculateDailyPayment(d));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testMonthlyPayment() throws ParseException {

		// it's the last day of the month
		calendarView.setDate(new SimpleDateFormat("yyyyMMdd").parse("20170131"));
		payrollPaymentBean.setDate(new SimpleDateFormat("yyyyMMdd").parse("20170131"));

		float salary = 300;
		float dueRate = 40;

		// this is a monthly emp
		MonthlyEmployeeWithSales m = new MonthlyEmployeeWithSales();
		m.setName("Mario");
		m.setSurname("Bianchi");
		m.setUsername("Mario");
		m.setPassword("Bianchi");
		m.setDueRate(dueRate);
		m.setSalary(salary);
		registerMonthlyBean.setSelectedUnion("union1");
		registerMonthlyBean.setSelectedPaymentMethod("Pickup");
		registerMonthlyBean.setEmpl(m);
		registerMonthlyBean.register();

		List<MonthlyEmployeeWithSales> empList = payrollDAO.findAllMonthlyEmployees();
		int empId = (empList.get(empList.size() - 1)).getId();
		m.setId(empId);


		float monthlyPayment = salary - dueRate;

		Assert.assertEquals(monthlyPayment, payrollPaymentBean.calculateMonthlyPayment(m));

	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testMonthlyPaymentWithSales() throws ParseException {

		// it's the last day of the month and it's friday
		calendarView.setDate(new SimpleDateFormat("yyyyMMdd").parse("20170331"));
		payrollPaymentBean.setDate(new SimpleDateFormat("yyyyMMdd").parse("20170331"));

		float salary = 300;
		float dueRate = 40;
		float commissionRate = 40;

		// this is a monthly emp
		MonthlyEmployeeWithSales m = new MonthlyEmployeeWithSales();
		m.setName("Mario");
		m.setSurname("Verdi");
		m.setUsername("Mario");
		m.setPassword("Verdi");
		m.setCommissionRate(commissionRate);
		m.setDueRate(dueRate);
		m.setSalary(salary);
		registerMonthlyBean.setSelectedUnion("union1");
		registerMonthlyBean.setSelectedPaymentMethod("Pickup");
		registerMonthlyBean.setEmpl(m);
		registerMonthlyBean.register();

		List<MonthlyEmployeeWithSales> empList = payrollDAO.findAllMonthlyEmployees();
		int empId = (empList.get(empList.size() - 1)).getId();
		m.setId(empId);
		
		List<SalesReceipt> salesReceipts = payrollDAO.findSalesReceiptOfThisEmp(empId);
		boolean found = false;
		SalesReceipt tmp = null;
		for(SalesReceipt sr : salesReceipts) {
			if (sr.getEmp_id() == empId) {
				found = true;
				tmp = sr;
				break;
			}
		}
		if(found)
			payrollDAO.removeSalesReceipt(tmp.getEmp_id());
		
		float amount = 20;
		
		SalesReceipt salesReceipt = new SalesReceipt();
		Date date = new SimpleDateFormat("yyyyMMdd").parse("20170330");
		salesReceipt.setAmount(amount);
		calendarView.setDate(date);
		postSalesReceiptBean.setR(salesReceipt);
		postSalesReceiptBean.post(empId);


		float monthlyPayment = salary + (amount * commissionRate) - dueRate;

		Assert.assertEquals(monthlyPayment, payrollPaymentBean.calculateMonthlyPayment(m));

	}


}
