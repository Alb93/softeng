package it.unipv.utils.payrollalgorithm.filter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.unipv.model.union.ServiceCharge;

public class ServiceChargesForDailyFilter implements RecordFilter<ServiceCharge> {

	@Override
	public List<ServiceCharge> filter(Date date, List<ServiceCharge> charges) {
		List<ServiceCharge> filteredCharges = new ArrayList<>();
		//reference week (in our case, it's the current week)
		String referenceWeek = new SimpleDateFormat("w").format(date);
		for (ServiceCharge serviceCharge : charges) {
			
			String recordWeek = new SimpleDateFormat("w").format(serviceCharge.getDate());
			System.out.println(recordWeek +  " " +  serviceCharge.getEmp_id());
			//we keep only the sales receipts submitted in the current week
			if(referenceWeek.equals(recordWeek)) {
				filteredCharges.add(serviceCharge);			}
		}
		
		
		return filteredCharges;
	}
	
	

}
