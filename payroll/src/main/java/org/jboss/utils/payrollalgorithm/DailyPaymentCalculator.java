package org.jboss.utils.payrollalgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.Employee;
import org.jboss.model.timecard.TimeCard;

public class DailyPaymentCalculator extends IPaymentCalculator<TimeCard> {
	
	private DailyEmployee dailyEmployee;
	private Date date;
	private List<TimeCard> cards;
	
	public DailyPaymentCalculator() {
		this.date = new Date();
	}

	@Override
	public float calculatePayment(Employee emp) {
		dailyEmployee = (DailyEmployee) emp;
		float payment = 0;
		Calendar cal = Calendar.getInstance();	
		cal.setTime(date);
		if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
			float hourlyRate = dailyEmployee.getHourlyRate();
			for (TimeCard timeCard : cards) {
				float hoursWorked = timeCard.getHours();
				if(hoursWorked > 8) {
					payment += (8 * hourlyRate);
					float extraHours = hoursWorked - 8 ;
					payment += (extraHours * 1.5* hourlyRate);
				} else {
					payment += (hourlyRate * hoursWorked);
				}
			}
			
			payment -= deductFromPayment(dailyEmployee.getDueRate());
			
			
		}
		return payment;
	}
	
	public void setCards(List<TimeCard> cards) {
		this.cards = cards;
	}
	
	public List<TimeCard> getCards() {
		return cards;
	}

	@Override
	public void setPostObject(List<TimeCard> cards) {
		this.cards = cards;
	}

	
	

}