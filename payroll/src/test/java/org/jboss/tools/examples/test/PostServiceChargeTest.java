package org.jboss.tools.examples.test;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.dao.PayrollDAO;
import org.jboss.model.union.ServiceCharge;
import org.jboss.view.post.PostServiceChargeBean;
import org.jboss.view.utils.CalendarView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(Arquillian.class)
public class PostServiceChargeTest extends ArquillianTest {
	
	@Inject PostServiceChargeBean postServiceChargeBean;
	@Inject CalendarView calendarView;
	@Inject PayrollDAO payrollDAO;
	
	float amount = 10;
	int empId = 10;
	
	@Before
	public void cleanup() {
		List<ServiceCharge> serviceCharges = payrollDAO.findServiceChargeOfThisEmp(empId);
		boolean found = false;
		ServiceCharge tmp = null;
		for(ServiceCharge sc : serviceCharges) {
			if (sc.getAmount() == amount) {
				found = true;
				tmp = sc;
				break;
			}
		}
		if(found)
			payrollDAO.removeSalesReceipt(tmp.getEmp_id());
	}
	
	
	@Test
	public void testServiceChargePosting(){
		
		ServiceCharge serviceCharge = new ServiceCharge();
		Date date = new Date(0);
		serviceCharge.setAmount(amount);
		calendarView.setDate(date);
		postServiceChargeBean.setR(serviceCharge);
		Map<String,Integer> employeesList = new HashMap<>();
		employeesList.put("nome", 10);
		postServiceChargeBean.setEmployeesList(employeesList);
		postServiceChargeBean.setSelectedUnion("10");
		postServiceChargeBean.post();
		
		List<ServiceCharge> sc = payrollDAO.findServiceChargeOfThisEmp(empId);
		boolean found = false;
		for (ServiceCharge charge : sc) {
			if(charge.getAmount() == amount) {
				found = true;
				break;
			}
		}
		
		Assert.assertTrue("Service charge posted", found);
		
	}

}
