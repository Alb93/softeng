package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.timecard.TimeCard;
import org.jboss.tools.examples.utils.CalendarView;

@Named
@SessionScoped
public class PostTimeCardBean implements Serializable {
	
	@Inject
    private Logger log;
	
	@Inject
	private PayrollController payrollController;
	
	private TimeCard card;
    
    @PostConstruct
	public void init() {
		card = new TimeCard();
	}
    
    
    public void post(int id) {
    	card.setId_daily(id);
    	setDate();
    	payrollController.postTimeCard(card);
        log.info("posting " + card.getId()+" sales receipt");
    }

	private void setDate() {
		FacesContext context = FacesContext.getCurrentInstance();
        CalendarView calendarView = (CalendarView) context.getApplication().evaluateExpressionGet(context, "#{calendarView}", CalendarView.class);
        Date sqldate = new Date(calendarView.getDate2().getTime());
        card.setDate(sqldate);
	}

	public void setCard(TimeCard card) {
		this.card = card;
	}
	
	public TimeCard getCard() {
		return card;
	}
	
	

}
