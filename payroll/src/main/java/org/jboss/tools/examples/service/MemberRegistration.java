/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.tools.examples.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.tools.examples.model.employees.DailyEmployee;
import org.jboss.tools.examples.model.employees.Employee;
import org.jboss.tools.examples.model.employees.MonthlyEmployee;
import org.jboss.tools.examples.model.employees.MonthlyEmployeeWithSales;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class MemberRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

   // @Inject
   // private Event<Member> memberEventSrc;

    public void register(Employee employee) throws Exception {
        log.info("Registering " + employee.getName());
        em.persist(employee);
  //      memberEventSrc.fire(employee);
    }
    
    public void setUsernameAndPassword() throws Exception{
    	//per ora vengono definite qua. Successivamente verranno passate a questa funzione
    	long id = 3;
    	DailyEmployee emp = em.find(DailyEmployee.class, id);
    	emp.setUsername("lmic");
    	emp.setPassword("Aranzulla");
    	em.persist(emp);
    	//DailyEmployee empl = em.find(DailyEmployee.class, Long.valueOf(2));
    	log.info(emp.getUsername() + emp.getPassword());
    }
}
