package it.unipv.model.employees;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="dailyEmployees")
public class DailyEmployee extends Employee {
	
	private float hourlyRate;

	public float getHourlyRate() {
		return hourlyRate;
	}

	public void setHourlyRate(float hourlyRate) {
		this.hourlyRate = hourlyRate;
	}

}
