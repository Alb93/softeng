package org.jboss.tools.examples.model.employees;

import javax.persistence.Entity;
import javax.persistence.Table;

public class MonthlyEmployee extends Employee {
	
	protected float salary;

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}	

}
