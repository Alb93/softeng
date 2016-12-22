package org.jboss.view;

import java.io.Serializable;
import java.sql.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.timecard.TimeCard;
import org.jboss.view.utils.CalendarView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class PostTimeCardBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	private TimeCard card;
    
    @PostConstruct
	public void init() {
		card = new TimeCard();
	}
      
    public void post(int id) {
    	card.setEmp_id(id);;
    	setDate();
    	payrollController.postTimeCard(card);
    	card = new TimeCard();
        System.out.println("posting " + card.getId()+" time card");
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
