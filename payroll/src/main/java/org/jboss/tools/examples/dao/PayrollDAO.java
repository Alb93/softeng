package org.jboss.tools.examples.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.jboss.tools.examples.model.employees.DailyEmployee;
import org.jboss.tools.examples.model.employees.Employee;
import org.jboss.tools.examples.model.employees.MonthlyEmployeeWithSales;
import org.jboss.tools.examples.model.salesreceipt.SalesReceipt;
import org.jboss.tools.examples.model.union.Union;

public class PayrollDAO {

	@PersistenceContext
	EntityManager em;

	public Employee doLogin(String username, String password) {

		try {
			System.out.println("EM = " + em);
			DailyEmployee emp = (DailyEmployee) em
					.createQuery(
							"SELECT e FROM DailyEmployee e where e.username = :usnValue and e.password = :pwdValue")
					.setParameter("usnValue", username).setParameter("pwdValue", password).getSingleResult();
			return emp;
		} catch (NoResultException e) {
			MonthlyEmployeeWithSales emp = (MonthlyEmployeeWithSales) em
					.createQuery("SELECT e FROM " + MonthlyEmployeeWithSales.class.getName()
							+ " e where e.username = :usnValue and e.password = :pwdValue")
					.setParameter("usnValue", username).setParameter("pwdValue", password).getSingleResult();
			return emp;
		}

	}

	public void registerEmployee(Employee emp) {

		em.persist(emp);

	}

	public void postSalesReceipt(SalesReceipt r) {
		em.persist(r);
	}

	public void registerUnion(Union u) {
		em.persist(u);
	}

	public Union doLoginUnion(String username, String password) {

		try {
			Union u = (Union) em
					.createQuery(
							"SELECT u FROM Union u where u.username = :usnValue and u.password = :pwdValue")
					.setParameter("usnValue", username).setParameter("pwdValue", password).getSingleResult();
			return u;
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return null;

	}

}
