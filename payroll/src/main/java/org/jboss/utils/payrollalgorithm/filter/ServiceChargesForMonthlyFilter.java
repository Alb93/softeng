package org.jboss.utils.payrollalgorithm.filter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jboss.model.union.ServiceCharge;

public class ServiceChargesForMonthlyFilter implements RecordFilter<ServiceCharge> {
	
	

		@Override
		public List<ServiceCharge> filter(Date date, List<ServiceCharge> charges) {
			List<ServiceCharge> filteredCharges = new ArrayList<>();
			//reference month (in our case, it's the current month)
			Calendar c1 = Calendar.getInstance();
			c1.setTime(date);
			int referenceMonth = c1.get(Calendar.MONTH);
			for (ServiceCharge serviceCharge : charges) {	
				Calendar c2 = Calendar.getInstance();
				c2.setTime(serviceCharge.getDate());
				int recordMonth = c2.get(Calendar.MONTH);
				System.out.println(recordMonth +  " " +  serviceCharge.getEmp_id());
				//we keep only the service charges submitted in the current month
				if(referenceMonth == recordMonth) {
					filteredCharges.add(serviceCharge);			}
			}
			
			
			return filteredCharges;
		}




}
