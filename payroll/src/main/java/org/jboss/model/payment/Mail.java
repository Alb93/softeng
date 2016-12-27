package org.jboss.model.payment;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mails")
public class Mail {
	
	@Id 
	private String emp_username;
	
	private String mail_address;
	
	public void setMail_address(String mail_address) {
		this.mail_address = mail_address;
	}
	
	public String getMail_address() {
		return mail_address;
	}
	
	public void setEmp_username(String emp_username) {
		this.emp_username = emp_username;
	}
	
	public String getEmp_username() {
		return emp_username;
	}

}
