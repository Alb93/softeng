package org.jboss.tools.examples.model.employees;

import javax.persistence.Entity;
import javax.persistence.Table;

public class DailyEmployee extends Employee {
	
	private float hourlyRate;

	public float getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

}
