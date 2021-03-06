package it.unipv.view.post;

import java.io.Serializable;
import java.sql.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.controller.PayrollController;
import it.unipv.model.timecard.TimeCard;
import it.unipv.view.utils.CalendarView;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class PostTimeCardBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	@Inject
	private CalendarView calendarView;
	
	private TimeCard card;
    
    @PostConstruct
	public void init() {
		card = new TimeCard();
	}
      
    public String post(int id) {
    	if(card.getHours() != 0){
    		card.setEmp_id(id);;
        	setDate();
        	payrollController.postTimeCard(card);
        	card = new TimeCard();
            System.out.println("posting " + card.getId()+" time card");
            return "success";
    	}
    	return "";
    	
    	
    }

	private void setDate() {
		
        Date sqldate = new Date(calendarView.getDate().getTime());
        card.setDate(sqldate);
	}

	public void setCard(TimeCard card) {
		this.card = card;
	}
	
	public TimeCard getCard() {
		return card;
	}
}
