package org.jboss.tools.examples.model.timecard;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="timeCards")
public class TimeCard {
	
	@Id
	private long id;
	
	private long id_daily;
	
    private Date date;
    private float hours;
    
    
    public void setId_daily(long id_daily) {
		this.id_daily = id_daily;
	}
    
    public long getId_daily() {
		return id_daily;
	}
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
    
    
    
    

}
