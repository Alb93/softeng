package org.jboss.model.employees;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Employee {
	
	@Id @GeneratedValue
	protected int id;
	
	protected String username;
	protected String password;
	
	protected String name;
	protected String surname;	
	protected String paymentMethod;
	
	protected float dueRate;
	
	protected String union_name;
	
	protected String paymentAmount;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public float getDueRate() {
		return dueRate;
	}

	public void setDueRate(float dueRate) {
		this.dueRate = dueRate;
	}

	public void setUnion_name(String union_name) {
		this.union_name = union_name;
	}
	
	public String getUnion_name() {
		return union_name;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public String getPaymentAmount() {
		return paymentAmount;
	}

}
