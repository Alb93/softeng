package org.jboss.view.post;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.employees.Employee;
import org.jboss.model.union.ServiceCharge;
import org.jboss.model.union.Union;
import org.jboss.view.login.LoginUnionBean;

@Named
@SessionScoped
public class PostServiceChargeBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	@Inject
	private LoginUnionBean loginUnionBean;
    
    private ServiceCharge r;
	private Union u;
	private String selectedUnion; 
    private Map<String,Integer> employeesList;

    
    @PostConstruct
	public void init() {
    	System.out.println("init del post ser");
        u = loginUnionBean.getU();
        System.out.println("il nome union è" + u.getName());
    	List<Employee> employees = payrollController.findAllUnionsEmployee(u.getName());
    	employeesList = new HashMap<>();
    	for (Employee employee : employees) {
			employeesList.put(employee.getName()+" "+employee.getSurname(),employee.getId());
			System.out.println("la lista" + employee.getName()+" "+employee.getSurname());
		}
    	selectedUnion = u.getName();
		r = new ServiceCharge();
	}
    
    public void post() {
    
    	r.setEmp_id(Integer.parseInt(selectedUnion));
    	payrollController.postServiceCharge(r);
       r = new ServiceCharge();
    }
    
    public void reload(){
        
        u = loginUnionBean.getU();
    	List<Employee> employees = payrollController.findAllUnionsEmployee(u.getName());
    	employeesList = new HashMap<>();
    	for (Employee employee : employees) {
    		employeesList.put(employee.getName()+" "+employee.getSurname(),employee.getId());
			System.out.println(employee.getName()+" "+employee.getSurname());
		}
    }

	

	public ServiceCharge getR() {
		return r;
	}

	public void setR(ServiceCharge r) {
		this.r = r;
	}
	
	public Union getU() {
		return u;
	}
	
	public void setEmployeesList(Map<String, Integer> employeesList) {
		this.employeesList = employeesList;
	}
	
	public Map<String, Integer> getEmployeesList() {
		return employeesList;
	}
	
	public void setSelectedUnion(String selectedUnion) {
		this.selectedUnion = selectedUnion;
	}
	
	public String getSelectedUnion() {
		return selectedUnion;
	}

}
