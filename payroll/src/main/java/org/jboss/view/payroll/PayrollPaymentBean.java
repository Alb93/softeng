package org.jboss.view.payroll;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
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
import org.jboss.utils.payrollalgorithm.RecordFilter;
import org.jboss.utils.payrollalgorithm.SalesReceiptFilter;
import org.jboss.utils.payrollalgorithm.ServiceChargesForDailyFilter;
import org.jboss.utils.payrollalgorithm.ServiceChargesForMonthlyFilter;
import org.jboss.utils.payrollalgorithm.TimeCardFilter;


@SuppressWarnings("serial")
@Named
@SessionScoped
public class PayrollPaymentBean implements Serializable {
	
	@Inject
	private PayrollController payrollController;
	
	private List<DailyEmployee> dailyEmployees;
	private PaymentCalculator<TimeCard> dailyPaymentCalculator = new DailyPaymentCalculator();
	private RecordFilter<TimeCard> timeCardFilter = new TimeCardFilter();
	private RecordFilter<ServiceCharge> serviceChargeDailyFilter = new ServiceChargesForDailyFilter();
	private RecordFilter<ServiceCharge> serviceChargeMonthlyFilter = new ServiceChargesForMonthlyFilter();
	private List<MonthlyEmployeeWithSales> monthlyEmployees;
	private PaymentCalculator<SalesReceipt> monthlyPaymentCalculator = new MonthlyPaymentCalculator();
	private RecordFilter<SalesReceipt> salesReceiptFilter = new SalesReceiptFilter();
	
	
	@PostConstruct
	public void init(){
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
	
	public float calculateDailyPayment(DailyEmployee dailyEmployee){
		List<TimeCard> cards = payrollController.findTimeCardsOfThisEmp(dailyEmployee.getId());
		List<TimeCard> filteredCards = timeCardFilter.filter(new Date(), cards);
		List<ServiceCharge> charges = payrollController.findServiceChargesOfThisEmp(dailyEmployee.getId());
		List<ServiceCharge> filteredCharges = serviceChargeDailyFilter.filter(new Date(), charges);
		dailyPaymentCalculator.setPostObject(filteredCards);
		dailyPaymentCalculator.setServiceCharges(filteredCharges);
		return dailyPaymentCalculator.calculatePayment(dailyEmployee);		
	}
	
	public float calculateMonthlyPayment(MonthlyEmployeeWithSales monthlyEmployee){
		List<SalesReceipt> receipts = payrollController.findSalesReceiptOfThisEmp(monthlyEmployee.getId());
		List<SalesReceipt> filteredReceipts = salesReceiptFilter.filter(new Date(), receipts);
		List<ServiceCharge> charges = payrollController.findServiceChargesOfThisEmp(monthlyEmployee.getId());
		List<ServiceCharge> filteredCharges = serviceChargeMonthlyFilter.filter(new Date(), charges);	
		monthlyPaymentCalculator.setPostObject(filteredReceipts);
		monthlyPaymentCalculator.setServiceCharges(filteredCharges);
		return monthlyPaymentCalculator.calculatePayment(monthlyEmployee);		
		
		
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

}
