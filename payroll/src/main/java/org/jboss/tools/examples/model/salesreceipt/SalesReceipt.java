package org.jboss.tools.examples.model.salesreceipt;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="salesreceipt")
public class SalesReceipt {
	
	@Id
	private long id;
	
	private long id_monthly;

	
    private Date date;
    private float amount;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public long getId_monthly() {
		return id_monthly;
	}
	public void setId_monthly(long id_monthly) {
		this.id_monthly = id_monthly;
	}
	
	
}
    