package org.jboss.utils.payrollalgorithm;

import java.util.List;

import org.jboss.model.employees.Employee;
import org.jboss.model.union.ServiceCharge;

public abstract class PaymentCalculator <T> {
	
	private List<ServiceCharge> charges;
	
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

}
