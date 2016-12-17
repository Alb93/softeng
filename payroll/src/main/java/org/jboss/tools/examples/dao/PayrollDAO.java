package org.jboss.tools.examples.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.jboss.tools.examples.model.admin.Admin;
import org.jboss.tools.examples.model.employees.DailyEmployee;
import org.jboss.tools.examples.model.employees.Employee;
import org.jboss.tools.examples.model.employees.MonthlyEmployeeWithSales;
import org.jboss.tools.examples.model.salesreceipt.SalesReceipt;
import org.jboss.tools.examples.model.timecard.TimeCard;
import org.jboss.tools.examples.model.union.ServiceCharge;
import org.jboss.tools.examples.model.union.Union;
@Stateless
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
		System.out.println("Posted "+r.getId());
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

	
	public Admin doAdminLogin(String username, String password){
		
		Admin adm = (Admin) em.createQuery("SELECT a FROM Admin a where a.username = :usnValue and a.password = :pwdValue")
				.setParameter("usnValue", username).setParameter("pwdValue", password).getSingleResult();
		return adm;	
	}
	
	
	public void postTimeCard(TimeCard card) {
		em.persist(card);
	}

	public void postServiceCharge(ServiceCharge s) {
		em.persist(s);
		// TODO Auto-generated method stub
		
	}
	
	public List<DailyEmployee> findAllDailyEmployees(){
		List<DailyEmployee> dailyEmployees =
				em.createQuery("select d from DailyEmployee d", DailyEmployee.class)
				.getResultList();
		for (int i = 0; i < dailyEmployees.size(); i++) {
			System.out.println("ok");
		}
		
		return dailyEmployees;
	}
	
	public List<Union> findAllUnions(){
		List<Union> unions =
				em.createQuery("select u from Union u", Union.class)
				.getResultList();
		
		return unions;
	}
	
	public List<Employee> findAllUnionsEmployee(long unionId){
		List<DailyEmployee> employees =
				em.createQuery("select e from DailyEmployee e where e.union_id = :unionIDValue", DailyEmployee.class)
				.setParameter("unionIDValue", unionId).getResultList();
		List<MonthlyEmployeeWithSales> monthlyEmployeeWithSales =
				em.createQuery("select e from MonthlyEmployeeWithSales e where e.union_id = :unionIDValue", MonthlyEmployeeWithSales.class)
				.setParameter("unionIDValue", unionId).getResultList();
		
		List<Employee> emplist = new LinkedList<>();
		emplist.addAll(employees);
		emplist.addAll(monthlyEmployeeWithSales);
		
		return emplist;
	}
	
	public void removeDailyEmployee(String username){
		em.createQuery("DELETE FROM DailyEmployee d where d.username = :usnValue")
				.setParameter("usnValue", username).executeUpdate();
	}
}
