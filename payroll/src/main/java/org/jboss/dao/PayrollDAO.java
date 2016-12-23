package org.jboss.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.model.admin.Admin;
import org.jboss.model.employees.DailyEmployee;
import org.jboss.model.employees.Employee;
import org.jboss.model.employees.MonthlyEmployeeWithSales;
import org.jboss.model.salesreceipt.SalesReceipt;
import org.jboss.model.timecard.TimeCard;
import org.jboss.model.union.ServiceCharge;
import org.jboss.model.union.Union;

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
		try{
			Admin adm = (Admin) em.createQuery("SELECT a FROM Admin a where a.username = :usnValue and a.password = :pwdValue")
					.setParameter("usnValue", username).setParameter("pwdValue", password).getSingleResult();
			return adm;
		} catch (NoResultException e){
			return null;
		}
			
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
	
	public List<MonthlyEmployeeWithSales> findAllMonthlyEmployees(){
		List<MonthlyEmployeeWithSales> monthlyEmployees =
				em.createQuery("select m from MonthlyEmployeeWithSales m", MonthlyEmployeeWithSales.class)
				.getResultList();
		return monthlyEmployees;
	}
	
	public List<Union> findAllUnions(){
		List<Union> unions =
				em.createQuery("select u from Union u", Union.class)
				.getResultList();
		
		return unions;
	}
	
	public Union findCorrespondingUnion(String unionName) {
		Union union = em.createQuery("select u from Union u where u.name = :unionName", Union.class)
				.setParameter("unionName", unionName).getSingleResult();
		return union;
	}
	
	
	
	public List<Employee> findAllUnionsEmployee(String name){
		List<DailyEmployee> employees =
				em.createQuery("select e from DailyEmployee e where e.union_name = :unionName", DailyEmployee.class)
				.setParameter("unionName", name).getResultList();
		List<MonthlyEmployeeWithSales> monthlyEmployeeWithSales =
				em.createQuery("select e from MonthlyEmployeeWithSales e where e.union_name = :unionName", MonthlyEmployeeWithSales.class)
				.setParameter("unionName", name).getResultList();
		
		List<Employee> emplist = new LinkedList<>();
		for (int i = 0; i < emplist.size(); i++) {
			System.out.println("Nome" + emplist.get(i).getName());
		}
		emplist.addAll(employees);
		emplist.addAll(monthlyEmployeeWithSales);
		
		return emplist;
	}
	
	public void removeDailyEmployee(int id){
		
		em.createQuery("DELETE FROM DailyEmployee d where d.id = :id")
				.setParameter("id", id).executeUpdate();
	}
	
	public void removeMonthlyEmployee(int id){
		
		em.createQuery("DELETE FROM MonthlyEmployeeWithSales d where d.id = :id")
				.setParameter("id", id).executeUpdate();
	}

	public void updateDailyEmployee(DailyEmployee d){
	
		Query q = em.createQuery("UPDATE DailyEmployee d SET d.name = :name, d.surname = :surname, d.dueRate = :rate, "
				+ "d.password = :password, d.paymentMethod = :payment, d.username = :username, d.union_name = :union,"
				+ "d.hourlyRate = :hourly WHERE d.id = :id");
		q.setParameter("id", d.getId());
		q.setParameter("name", d.getName());
		q.setParameter("surname", d.getSurname());
		q.setParameter("rate", d.getDueRate());
		q.setParameter("password", d.getPassword());
		q.setParameter("payment", d.getPaymentMethod());
		q.setParameter("username", d.getUsername());
		q.setParameter("union", d.getUnion_name());
		q.setParameter("hourly", d.getHourlyRate());
		
		q.executeUpdate();
	
	
	
	}
	
	public void updateMonthlyEmployee(MonthlyEmployeeWithSales m){
		
		Query q = em.createQuery("UPDATE MonthlyEmployeeWithSales m SET m.name = :name, m.surname = :surname, m.dueRate = :rate, "
				+ "m.password = :password, m.paymentMethod = :payment, m.username = :username, m.union_name = :union,"
				+ "m.salary = :salary WHERE m.id = :id");
		q.setParameter("id", m.getId());
		q.setParameter("name", m.getName());
		q.setParameter("surname", m.getSurname());
		q.setParameter("rate", m.getDueRate());
		q.setParameter("password", m.getPassword());
		q.setParameter("payment", m.getPaymentMethod());
		q.setParameter("username", m.getUsername());
		q.setParameter("union", m.getUnion_name());
		q.setParameter("salary", m.getSalary());
		
		q.executeUpdate();
	
	
	
	}
}
