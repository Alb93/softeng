package it.unipv.tools.examples.test;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import it.unipv.dao.PayrollDAO;
import it.unipv.model.timecard.TimeCard;
import it.unipv.view.post.PostTimeCardBean;
import it.unipv.view.utils.CalendarView;

@RunWith(Arquillian.class)
public class PostTimeCardTest extends ArquillianTest {
	
	@Inject PostTimeCardBean postTimeCardBean;
	@Inject CalendarView calendarView;
	@Inject PayrollDAO payrollDAO;
	
	int empId = 10;
	float hours = 10;
	
	@Before
	public void cleanup() {
		List<TimeCard> timeCards = payrollDAO.findTimeCardsOfThisEmp(empId);
		boolean found = false;
		TimeCard tmp = null;
		for(TimeCard tc: timeCards) {
			if (tc.getHours() == hours) {
				found = true;
				tmp = tc;
				break;
			}
		}
		if(found)
			payrollDAO.removeTimeCard(tmp.getEmp_id());
	}
	
	@Test
	public void testTimeCardPosting(){
		TimeCard timeCard = new TimeCard();
		Date date = new Date();
		timeCard.setHours(hours);
		calendarView.setDate(date);
		postTimeCardBean.setCard(timeCard);
		postTimeCardBean.post(empId);
		
		List<TimeCard> timeCards = payrollDAO.findTimeCardsOfThisEmp(empId);
		boolean found = false;
		for (TimeCard tc : timeCards) {
			if(tc.getHours() == hours){
				found = true;
				break;
			}
		}
		
		Assert.assertTrue("Time card posted", found);
	}

}
