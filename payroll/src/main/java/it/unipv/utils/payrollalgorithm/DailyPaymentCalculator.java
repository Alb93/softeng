package it.unipv.utils.payrollalgorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.unipv.model.employees.DailyEmployee;
import it.unipv.model.employees.Employee;
import it.unipv.model.timecard.TimeCard;

public class DailyPaymentCalculator extends PaymentCalculator<TimeCard> {
	
	private DailyEmployee dailyEmployee;
	private Date date;
	private List<TimeCard> cards;
	
	public DailyPaymentCalculator(Date date) {
		this.date = date;
	}

	@Override
	public float calculatePayment(Employee emp) {
		dailyEmployee = (DailyEmployee) emp;
		float payment = 0;
		Calendar cal = Calendar.getInstance();	
		cal.setTime(date);
		if(cal.get(Calendar.DAY_OF_WEEK) == dayOfTheWeek){
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
