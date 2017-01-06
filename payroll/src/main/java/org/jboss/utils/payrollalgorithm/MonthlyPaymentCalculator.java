package org.jboss.utils.payrollalgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.jboss.model.employees.Employee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.salesreceipt.SalesReceipt;
import org.jboss.model.union.ServiceCharge;

public class MonthlyPaymentCalculator extends PaymentCalculator<SalesReceipt> {

	private MonthlyEmployeeWithSales monthlyEmployee;
	private Date date;
	private List<SalesReceipt> receipts;

	public MonthlyPaymentCalculator() {
		this.date = new Date();
	}

	@Override
	public float calculatePayment(Employee emp) {
		monthlyEmployee = (MonthlyEmployeeWithSales) emp;
		float payment = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//Calendar cal = new GregorianCalendar(2017,3,28);
		
		
		if(monthlyEmployee.getCommissionRate() != 0){
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				float commissionRate = monthlyEmployee.getCommissionRate();
				for (SalesReceipt salesReceipt : receipts) {
					float salesAmount = salesReceipt.getAmount();
					payment += (salesAmount * commissionRate);
				}
			}
		} 
		
		if(cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) ||
		  (cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) + 1 && 
		  (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY)) || 
		  (cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) + 2 && 
		  (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY))){
			System.out.println("Funzionaaaa");
				payment += monthlyEmployee.getSalary();
				payment -= deductFromPayment(monthlyEmployee.getDueRate());
		}	

		return payment;
	}

	@Override
	public void setPostObject(List<SalesReceipt> salesReceipts) {
		receipts = salesReceipts;
		for (SalesReceipt salesReceipt : salesReceipts) {
			System.out.println("Ecco il mio post" + salesReceipt.getAmount() + " " + salesReceipt.getEmp_id());
		}
	}
	
	public void setReceipts(List<SalesReceipt> receipts) {
		this.receipts = receipts;
	}
	
	public List<SalesReceipt> getReceipts() {
		return receipts;
	}

}
