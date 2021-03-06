package it.unipv.utils.payrollalgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import it.unipv.model.employees.Employee;
import it.unipv.model.employees.MonthlyEmployeeWithSales;
import it.unipv.model.salesreceipt.SalesReceipt;
import it.unipv.model.union.ServiceCharge;

public class MonthlyPaymentCalculator extends PaymentCalculator<SalesReceipt> {

	private MonthlyEmployeeWithSales monthlyEmployee;
	private Date date;
	private List<SalesReceipt> receipts;

	public MonthlyPaymentCalculator(Date date) {
		this.date = date;
	}

	@Override
	public float calculatePayment(Employee emp) {
		monthlyEmployee = (MonthlyEmployeeWithSales) emp;
		float payment = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//Calendar cal = new GregorianCalendar(2017,3,28);
		
		
		if(monthlyEmployee.getCommissionRate() != 0){
			if (cal.get(Calendar.DAY_OF_WEEK) == dayOfTheWeek) {
				float commissionRate = monthlyEmployee.getCommissionRate();
				for (SalesReceipt salesReceipt : receipts) {
					float salesAmount = salesReceipt.getAmount();
					payment += (salesAmount * commissionRate);
				}
			}
		} 
		
		if(cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) ||
		  (cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) + 1 && 
		  (cal.get(Calendar.DAY_OF_WEEK) == dayOfTheWeek)) || 
		  (cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH) + 2 && 
		  (cal.get(Calendar.DAY_OF_WEEK) == dayOfTheWeek))){
			System.out.println("Funzionaaaa");
				payment += monthlyEmployee.getSalary();
				//we multiply by 4 because the dues rate are weekly and in a month there are 4 weeks
				payment -= 4*(deductFromPayment(monthlyEmployee.getDueRate()));
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
