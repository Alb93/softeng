package org.jboss.model.union;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="serviceCharges")
public class ServiceCharge {
	

	@Id @GeneratedValue
	private int id;
	
	private float amount;
	
	private int emp_id;

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	
	public int getEmp_id() {
		return emp_id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	
	
	
	
	
}
