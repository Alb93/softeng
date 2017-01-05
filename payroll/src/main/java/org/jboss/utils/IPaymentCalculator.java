package org.jboss.utils;

import java.util.List;

import org.jboss.model.employees.Employee;

public interface IPaymentCalculator <T> {
	
	public float calculatePayment(Employee emp);
	public void setPostObject(List<T> object);

}
