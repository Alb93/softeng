package org.jboss.view.payroll;

import java.io.Serializable;
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
import org.jboss.utils.DailyPaymentCalculator;
import org.jboss.utils.IPaymentCalculator;
import org.jboss.utils.MonthlyPaymentCalculator;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class PayrollPaymentBean implements Serializable {
	
	@Inject
	private PayrollController payrollController;
	
	private List<DailyEmployee> dailyEmployees;
	private List<MonthlyEmployeeWithSales> monthlyEmployees;
	private IPaymentCalculator<TimeCard> dailyPaymentCalculator = new DailyPaymentCalculator();
	//private IPaymentCalculator monthlyPaymentCalculator = new MonthlyPaymentCalculator();
	
	@PostConstruct
	public void init(){
		dailyEmployees = payrollController.findAllDailyEmployees();
		for (DailyEmployee dailyEmployee : dailyEmployees) {
			String payment = calculateDailyPayment(dailyEmployee);
		}
		monthlyEmployees = payrollController.findAllMonthlyEmployees();
	}
	
	public String calculateDailyPayment(DailyEmployee dailyEmployee){
		List<TimeCard> cards = payrollController.findTimeCardsOfThisEmp(dailyEmployee.getId());
		List<ServiceCharge> charges = payrollController.findServiceChargesOfThisEmp(dailyEmployee.getId());
		dailyPaymentCalculator.setPostObject(cards);
		dailyPaymentCalculator.calculatePayment(dailyEmployee);
		return null;
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
