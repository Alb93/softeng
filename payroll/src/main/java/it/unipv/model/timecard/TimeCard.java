package it.unipv.model.timecard;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="timeCards")
public class TimeCard {
	
	@Id @GeneratedValue
	private long id;
	
	private int emp_id;
	
    private Date date;
    private float hours;
    
    
   
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
	public float getHours() {
		return hours;
	}
	public void setHours(float hours) {
		this.hours = hours;
	}
	
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	
	public int getEmp_id() {
		return emp_id;
	}
	
    
    
    
    

}
