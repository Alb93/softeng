package org.jboss.view.payroll;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.salesreceipt.SalesReceipt;
import org.jboss.model.timecard.TimeCard;
import org.jboss.model.union.ServiceCharge;
import org.jboss.utils.payrollalgorithm.DailyPaymentCalculator;
import org.jboss.utils.payrollalgorithm.MonthlyPaymentCalculator;
import org.jboss.utils.payrollalgorithm.PaymentCalculator;
import org.jboss.utils.payrollalgorithm.filter.RecordFilter;
import org.jboss.utils.payrollalgorithm.filter.SalesReceiptFilter;
import org.jboss.utils.payrollalgorithm.filter.ServiceChargesForDailyFilter;
import org.jboss.utils.payrollalgorithm.filter.ServiceChargesForMonthlyFilter;
import org.jboss.utils.payrollalgorithm.filter.TimeCardFilter;
import org.jboss.view.utils.CalendarView;


@SuppressWarnings("serial")
@Named
@SessionScoped
public class PayrollPaymentBean implements Serializable {
	
	@Inject
	private PayrollController payrollController;
	
	@Inject
	private CalendarView calendarView;
	
	private List<DailyEmployee> dailyEmployees;
	private PaymentCalculator<TimeCard> dailyPaymentCalculator;
	private RecordFilter<TimeCard> timeCardFilter = new TimeCardFilter();
	private RecordFilter<ServiceCharge> serviceChargeDailyFilter = new ServiceChargesForDailyFilter();
	private RecordFilter<ServiceCharge> serviceChargeMonthlyFilter = new ServiceChargesForMonthlyFilter();
	private List<MonthlyEmployeeWithSales> monthlyEmployees;
	private PaymentCalculator<SalesReceipt> monthlyPaymentCalculator;
	private RecordFilter<SalesReceipt> salesReceiptFilter = new SalesReceiptFilter();
	
	private Date date;
	
	
	
	public float calculateDailyPayment(DailyEmployee dailyEmployee){
		List<TimeCard> cards = payrollController.findTimeCardsOfThisEmp(dailyEmployee.getId());
		List<TimeCard> filteredCards = timeCardFilter.filter(date, cards);
		List<ServiceCharge> charges = payrollController.findServiceChargesOfThisEmp(dailyEmployee.getId());
		List<ServiceCharge> filteredCharges = serviceChargeDailyFilter.filter(date, charges);
		dailyPaymentCalculator = new DailyPaymentCalculator(date);
		//6 is the value for friday
		dailyPaymentCalculator.setDayOfTheWeek(6);
		dailyPaymentCalculator.setPostObject(filteredCards);
		dailyPaymentCalculator.setServiceCharges(filteredCharges);
		return dailyPaymentCalculator.calculatePayment(dailyEmployee);		
	}
	
	public float calculateMonthlyPayment(MonthlyEmployeeWithSales monthlyEmployee){
		List<SalesReceipt> receipts = payrollController.findSalesReceiptOfThisEmp(monthlyEmployee.getId());
		List<SalesReceipt> filteredReceipts = salesReceiptFilter.filter(date, receipts);
		List<ServiceCharge> charges = payrollController.findServiceChargesOfThisEmp(monthlyEmployee.getId());
		List<ServiceCharge> filteredCharges = serviceChargeMonthlyFilter.filter(date, charges);
		monthlyPaymentCalculator = new MonthlyPaymentCalculator(date);
		monthlyPaymentCalculator.setDayOfTheWeek(6);
		monthlyPaymentCalculator.setPostObject(filteredReceipts);
		monthlyPaymentCalculator.setServiceCharges(filteredCharges);
		return monthlyPaymentCalculator.calculatePayment(monthlyEmployee);		
		
		
	}
	
	public void runPayroll(){
		date = new Date(calendarView.getDate().getTime());
		dailyEmployees = payrollController.findAllDailyEmployees();
		for (DailyEmployee dailyEmployee : dailyEmployees) {
			float payment = calculateDailyPayment(dailyEmployee);
			dailyEmployee.setPaymentAmount(String.valueOf(payment) + " €");
		}
		
		monthlyEmployees = payrollController.findAllMonthlyEmployees();
		for (MonthlyEmployeeWithSales monthlyEmployee : monthlyEmployees) {
			float payment = calculateMonthlyPayment(monthlyEmployee);
			monthlyEmployee.setPaymentAmount(String.valueOf(payment) + " €");
		}
	}
	
	public void setDailyEmployees(List<DailyEmployee> dailyEmployees) {
		this.dailyEmployees = dailyEmployees;
	}
	
	public List<DailyEmployee> getDailyEmployees() {
		return dailyEmployees;
	}
	
	public void setMonthlyEmployees(List<MonthlyEmployeeWithSales> monthlyEmployees) {
		this.monthlyEmployees = monthlyEmployees;
	}
	
	public List<MonthlyEmployeeWithSales> getMonthlyEmployees() {
		return monthlyEmployees;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}

}
