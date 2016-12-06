package org.jboss.tools.examples.service;

import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.tools.examples.model.employees.DailyEmployee;

@ManagedBean
public class LoggerManager {
	
	private String usn;
	
	@Inject
    private Logger log;

    @Inject
    private EntityManager em;
    
    public String checkLogin(String usn, String pwd){
    	try {
    		DailyEmployee emp = (DailyEmployee) em.createQuery("SELECT e FROM DailyEmployee e where e.username = :usnValue")
        			.setParameter("usnValue", usn).getSingleResult();
    		log.info(emp.getName());
    		this.usn = usn;
    		return "success"; 
    	} catch (NoResultException e) {
    		return "failure";  
    	}
    	
    }
    
    

}
