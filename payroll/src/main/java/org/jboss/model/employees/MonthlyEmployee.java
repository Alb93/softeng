package org.jboss.model.employees;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MonthlyEmployee extends Employee {
	
	protected float salary;

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}	

}
