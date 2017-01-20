package it.unipv.model.payment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="banks")
public class Bank {
	
	@Id
	private String emp_username;
	
	private String bank_account;
	
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	
	public String getBank_account() {
		return bank_account;
	}
	
	public void setEmp_username(String emp_username) {
		this.emp_username = emp_username;
	}
	
	public String getEmp_username() {
		return emp_username;
	}

}
