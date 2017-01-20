package it.unipv.utils.payrollalgorithm;

import java.util.List;

import it.unipv.model.employees.Employee;
import it.unipv.model.union.ServiceCharge;

public abstract class PaymentCalculator <T> {
	
	private List<ServiceCharge> charges;
	protected int dayOfTheWeek;
	
	public abstract float calculatePayment(Employee emp);
	public abstract void setPostObject(List<T> object);
	
	
	public void setServiceCharges(List<ServiceCharge> charges) {
		
		this.charges = charges;
	}
	
	
	public float deductFromPayment(float duesRate) {
	
		float amountToBeDeducted = 0;
		amountToBeDeducted += duesRate;
		
		for (ServiceCharge serviceCharge : charges) {
			System.out.println("sto togliendo");
			amountToBeDeducted += serviceCharge.getAmount();
		}
		
		return amountToBeDeducted;
	}

	public void setDayOfTheWeek(int dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}
	
	public int getDayOfTheWeek() {
		return dayOfTheWeek;
	}
}
