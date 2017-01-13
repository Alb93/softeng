package org.jboss.utils.payrollalgorithm.filter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.model.salesreceipt.SalesReceipt;

public class SalesReceiptFilter implements RecordFilter<SalesReceipt> {

	@Override
	public List<SalesReceipt> filter(Date date, List<SalesReceipt> salesReceipts) {
		List<SalesReceipt> filteredSalesReceipt = new ArrayList<>();
		//reference week (in our case, it's the current week)
		String referenceWeek = new SimpleDateFormat("w").format(date);	
		for (SalesReceipt salesReceipt : salesReceipts) {
			String recordWeek = new SimpleDateFormat("w").format(salesReceipt.getDate());
			//we keep only the sales receipt submitted in the current week
			if(referenceWeek.equals(recordWeek)) {
				filteredSalesReceipt.add(salesReceipt);
				System.out.println(salesReceipt.getAmount() + " " + salesReceipt.getEmp_id());
			}
		}
		return filteredSalesReceipt;
	}

}
