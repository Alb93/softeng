package org.jboss.model.employees;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="monthlyEmployees")
public class MonthlyEmployeeWithSales extends MonthlyEmployee {
	
	private float commissionRate;

	public float getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(float commissionRate) {
		this.commissionRate = commissionRate;
	}

}
