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
import org.jboss.model.timecard.TimeCard;
import org.jboss.model.union.ServiceCharge;
import org.jboss.utils.payrollalgorithm.DailyPaymentCalculator;
import org.jboss.utils.payrollalgorithm.IPaymentCalculator;
import org.jboss.utils.payrollalgorithm.RecordFilter;
import org.jboss.utils.payrollalgorithm.ServiceChargesFilter;
import org.jboss.utils.payrollalgorithm.TimeCardFilter;


@SuppressWarnings("serial")
@Named
@SessionScoped
public class PayrollPaymentBean implements Serializable {
	
	@Inject
	private PayrollController payrollController;
	
	private List<DailyEmployee> dailyEmployees;
	private IPaymentCalculator<TimeCard> dailyPaymentCalculator = new DailyPaymentCalculator();
	private RecordFilter<TimeCard> timeCardFilter = new TimeCardFilter();
	private RecordFilter<ServiceCharge> serviceChargeFilter = new ServiceChargesFilter();
	private List<MonthlyEmployeeWithSales> monthlyEmployees;
	
	//private IPaymentCalculator monthlyPaymentCalculator = new MonthlyPaymentCalculator();
	
	@PostConstruct
	public void init(){
		dailyEmployees = payrollController.findAllDailyEmployees();
		for (DailyEmployee dailyEmployee : dailyEmployees) {
			float payment = calculateDailyPayment(dailyEmployee);
			dailyEmployee.setPaymentAmount(String.valueOf(payment) + " €");
		}
		monthlyEmployees = payrollController.findAllMonthlyEmployees();
	}
	
	public float calculateDailyPayment(DailyEmployee dailyEmployee){
		List<TimeCard> cards = payrollController.findTimeCardsOfThisEmp(dailyEmployee.getId());
		List<TimeCard> filteredCards = timeCardFilter.filter(new Date(), cards);
		List<ServiceCharge> charges = payrollController.findServiceChargesOfThisEmp(dailyEmployee.getId());
		List<ServiceCharge> filteredCharges = serviceChargeFilter.filter(new Date(), charges);
		dailyPaymentCalculator.setPostObject(filteredCards);
		dailyPaymentCalculator.setServiceCharges(filteredCharges);
		return dailyPaymentCalculator.calculatePayment(dailyEmployee);		
	}
	
	public void calculateMonthlyPayment(MonthlyEmployeeWithSales monthlyEmployeeWithSales){
		
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
