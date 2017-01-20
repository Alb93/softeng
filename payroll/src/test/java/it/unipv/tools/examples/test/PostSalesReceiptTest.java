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
import it.unipv.model.salesreceipt.SalesReceipt;
import it.unipv.view.post.PostSalesReceiptBean;
import it.unipv.view.utils.CalendarView;

@RunWith(Arquillian.class)
public class PostSalesReceiptTest extends ArquillianTest {
	
	@Inject PostSalesReceiptBean postSalesReceiptBean;
	@Inject CalendarView calendarView;
	@Inject PayrollDAO payrollDAO;
	
	int empId = 10;
	float amount = 10;
	
	@Before
	public void cleanup() {
		List<SalesReceipt> salesReceipts = payrollDAO.findSalesReceiptOfThisEmp(empId);
		boolean found = false;
		SalesReceipt tmp = null;
		for(SalesReceipt sr : salesReceipts) {
			if (sr.getAmount() == amount) {
				found = true;
				tmp = sr;
				break;
			}
		}
		if(found)
			payrollDAO.removeSalesReceipt(tmp.getEmp_id());
	}
	
	@Test
	public void testSalesReceiptPosting(){
		SalesReceipt salesReceipt = new SalesReceipt();
		Date date = new Date();
		salesReceipt.setAmount(amount);
		calendarView.setDate(date);
		postSalesReceiptBean.setR(salesReceipt);
		postSalesReceiptBean.post(empId);
		
		List<SalesReceipt> salesReceipts = payrollDAO.findSalesReceiptOfThisEmp(empId);
		boolean found = false;
		for (SalesReceipt sr : salesReceipts) {
			if(sr.getAmount() == amount){
				found = true;
			}
		}
		
		Assert.assertTrue("Sales receipt posted", found);
	}

}
