package org.jboss.tools.examples.model.employees;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="employees")
public class MonthlyEmployeeWithSales extends MonthlyEmployee {
	
	private float commissionRate;

	public float getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(float commissionRate) {
		this.commissionRate = commissionRate;
	}

}
