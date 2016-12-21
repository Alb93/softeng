package org.jboss.tools.examples.model.union;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="serviceCharges")
public class ServiceCharge {
	
	private String service;

	@Id
	private long id;
	
	private float amount;
	
	private long id_emp;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public long getId_emp() {
		return id_emp;
	}

	public void setId_emp(long id_emp) {
		this.id_emp = id_emp;
	}

	
	
	
	
	
}
