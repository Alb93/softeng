package org.jboss.utils.payrollalgorithm.filter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.model.timecard.TimeCard;

public class TimeCardFilter implements RecordFilter<TimeCard> {

	@Override
	public List<TimeCard> filter(Date date, List<TimeCard> timecards) {
		List<TimeCard> filteredCards = new ArrayList<>();
		//reference week (in our case, it's the current week)
		String referenceWeek = new SimpleDateFormat("w").format(date);
		System.out.println("Sono prima del for");
		for (TimeCard timeCard : timecards) {
			System.out.println("Sono nel for");
			String recordWeek = new SimpleDateFormat("w").format(timeCard.getDate());
			//we keep only the time cards submitted in the current week
			if(referenceWeek.equals(recordWeek)) {
				filteredCards.add(timeCard);
			}
		}
		
		return filteredCards;
	}

}
