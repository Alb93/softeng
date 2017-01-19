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
import org.jboss.model.payment.Bank;
import org.jboss.model.payment.Mail;
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
			System.out.println("EM = "  + em);
			DailyEmployee emp = (DailyEmployee) em
					.createQuery(
							"SELECT e FROM DailyEmployee e where e.username = :usnValue and e.password = :pwdValue")
					.setParameter("usnValue", username).setParameter("pwdValue", password).getSingleResult();
			System.out.println("Mi ha trovato il daily");
			return emp;
		} catch (NoResultException e1) {
			try{
				System.out.println("Non mi ha trovato l'emp " + username + " nei daily");
				MonthlyEmployeeWithSales emp = (MonthlyEmployeeWithSales) em
						.createQuery("SELECT e FROM " + MonthlyEmployeeWithSales.class.getName()
								+ " e where e.username = :usnValue and e.password = :pwdValue")
						.setParameter("usnValue", username).setParameter("pwdValue", password).getSingleResult();
				return emp;
			} catch(NoResultException e2){
				System.out.println("Non mi ha trovato l'emp " + username + " nei monthly");
				return null;
			}
			
		}

	}
	
	public boolean checkIfUsernameAlreadyExists(String username){
		try {
			em.createQuery(
							"SELECT e FROM DailyEmployee e where e.username = :usnValue")
					.setParameter("usnValue", username).getSingleResult();
			System.out.println("Mi ha trovato il daily");
			return true;
		} catch (NoResultException e1) {
			try{
				em.createQuery("SELECT e FROM " + MonthlyEmployeeWithSales.class.getName()
								+ " e where e.username = :usnValue")
						.setParameter("usnValue", username).getSingleResult();
				return true;
			} catch(NoResultException e2){
				return false;
			}
			
		}
	}

	public void registerEmployee(Employee emp) {
		

		em.persist(emp);
		

	}
	
	public void registerMailAddress(Mail mail){
		em.persist(mail);
	}
	
	public void registerBankAccount(Bank bank){
		em.persist(bank);
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
	
	public Bank findBank(String username) {
		Bank bank = em.createQuery("select b from Bank b where b.emp_username = :username", Bank.class)
				.setParameter("username", username).getSingleResult();
		return bank;
	}
	
	public Mail findMail(String username) {
		Mail mail = em.createQuery("select m from Mail m where m.emp_username = :username", Mail.class)
				.setParameter("username", username).getSingleResult();
		return mail;
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
	
	public void removeTimeCard(int id){
		em.createQuery("DELETE FROM TimeCard t where t.emp_id = :id")
			.setParameter("id", id).executeUpdate();
	}
	
	public void removeServiceCharge(int id){
		em.createQuery("DELETE FROM ServiceCharge s where s.emp_id = :id")
			.setParameter("id", id).executeUpdate();
	}
	
	public void removeMonthlyEmployee(int id){
		
		em.createQuery("DELETE FROM MonthlyEmployeeWithSales d where d.id = :id")
				.setParameter("id", id).executeUpdate();
	}
	
	public void removeSalesReceipt(int id){
		em.createQuery("DELETE FROM SalesReceipt s where s.emp_id = :id")
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
				+ "m.salary = :salary, m.commissionRate = :commissionRate WHERE m.id = :id");
		q.setParameter("id", m.getId());
		q.setParameter("name", m.getName());
		q.setParameter("surname", m.getSurname());
		q.setParameter("rate", m.getDueRate());
		q.setParameter("password", m.getPassword());
		q.setParameter("payment", m.getPaymentMethod());
		q.setParameter("username", m.getUsername());
		q.setParameter("union", m.getUnion_name());
		q.setParameter("salary", m.getSalary());
		q.setParameter("commissionRate", m.getCommissionRate());
		
		q.executeUpdate();
	
	
	
	}

	public void updateBankAccount(Bank b) {
		em.persist(b);
		
	}

	public void updateMailAddress(Mail m) {
		em.persist(m);		
	}
	
	public void removeOldBankAndMail(String username){
		em.createQuery("DELETE FROM Mail m where m.emp_username = :username")
		.setParameter("username", username).executeUpdate();
		em.createQuery("DELETE FROM Bank b where b.emp_username = :username")
		.setParameter("username", username).executeUpdate();
	}
	
	public List<TimeCard> findTimeCardsOfThisEmp(int id){

		List<TimeCard> cards = em
					.createQuery(
							"SELECT t FROM TimeCard t where t.emp_id = :id", TimeCard.class)
					.setParameter("id", id).getResultList();
			return cards;
		
	}
	
	public List<ServiceCharge> findServiceChargeOfThisEmp(int id){
		
		List<ServiceCharge> charges = em
				.createQuery(
						"SELECT s FROM ServiceCharge s where s.emp_id = :id", ServiceCharge.class)
				.setParameter("id", id).getResultList();
		return charges;
	}
	
	public List<SalesReceipt> findSalesReceiptOfThisEmp(int id){
		
		List<SalesReceipt> receipts = em
				.createQuery(
						"SELECT s FROM SalesReceipt s where s.emp_id = :id", SalesReceipt.class)
				.setParameter("id", id).getResultList();
		return receipts;		
	}
}
