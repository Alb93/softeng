package org.jboss.tools.examples.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.tools.examples.model.employees.DailyEmployee;
import org.jboss.tools.examples.model.employees.Employee;



public class PayrollDAO {
	
	
	@PersistenceContext
	EntityManager em;

	
	
	public DailyEmployee doLogin(String username, String password){
		
		DailyEmployee emp = (DailyEmployee) em.createQuery("SELECT e FROM DailyEmployee e where e.username = :usnValue and e.password = :pwdValue")
    			.setParameter("usnValue", username).setParameter("pwdValue", password).getSingleResult();
		
		return emp;
		
	}
	
	public void registerEmployee(Employee emp){
		
		em.persist(emp);
		
	}

}
