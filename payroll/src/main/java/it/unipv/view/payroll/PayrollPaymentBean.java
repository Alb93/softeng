package it.unipv.view.payroll;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.controller.PayrollController;
import it.unipv.model.employees.DailyEmployee;
import it.unipv.model.employees.MonthlyEmployeeWithSales;
import it.unipv.model.salesreceipt.SalesReceipt;
import it.unipv.model.timecard.TimeCard;
import it.unipv.model.union.ServiceCharge;
import it.unipv.utils.payrollalgorithm.DailyPaymentCalculator;
import it.unipv.utils.payrollalgorithm.MonthlyPaymentCalculator;
import it.unipv.utils.payrollalgorithm.PaymentCalculator;
import it.unipv.utils.payrollalgorithm.filter.RecordFilter;
import it.unipv.utils.payrollalgorithm.filter.SalesReceiptFilter;
import it.unipv.utils.payrollalgorithm.filter.ServiceChargesForDailyFilter;
import it.unipv.utils.payrollalgorithm.filter.ServiceChargesForMonthlyFilter;
import it.unipv.utils.payrollalgorithm.filter.TimeCardFilter;
import it.unipv.view.utils.CalendarView;


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
			dailyEmployee.setDate(new SimpleDateFormat("dd/MM/yyyy").format(date));
		}
		
		monthlyEmployees = payrollController.findAllMonthlyEmployees();
		for (MonthlyEmployeeWithSales monthlyEmployee : monthlyEmployees) {
			float payment = calculateMonthlyPayment(monthlyEmployee);
			monthlyEmployee.setPaymentAmount(String.valueOf(payment) + " €");
			monthlyEmployee.setDate(new SimpleDateFormat("dd/MM/yyyy").format(date));
			
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
