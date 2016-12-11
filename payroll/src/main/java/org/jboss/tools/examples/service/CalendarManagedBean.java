package org.jboss.tools.examples.service;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CalendarManagedBean {
	private Date date;
	
	@PostConstruct
	public void init() {
		date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
