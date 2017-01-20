package it.unipv.view.post;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import it.unipv.controller.PayrollController;
import it.unipv.model.employees.Employee;
import it.unipv.model.union.ServiceCharge;
import it.unipv.model.union.Union;
import it.unipv.view.login.LoggedUnionBean;
import it.unipv.view.login.LoginUnionBean;
import it.unipv.view.utils.CalendarView;

@Named
@SessionScoped
public class PostServiceChargeBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
	
	@Inject
	private LoggedUnionBean loggedUnionBean;
	
	@Inject
	private CalendarView calendarView;
    
    private ServiceCharge r;
	private Union u;
	private String selectedUnion; 
    private Map<String,Integer> employeesList;

    
    @PostConstruct
	public void init() {
    	System.out.println("init del post ser");
        u = loggedUnionBean.getU();
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
    	if(employeesList.isEmpty()){
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "There are no employees in this union");
    		RequestContext.getCurrentInstance().showMessageInDialog(message);
    	} else {
    		r.setEmp_id(Integer.parseInt(selectedUnion));
    		setDate();
        	payrollController.postServiceCharge(r);
        	r = new ServiceCharge();
    	}
    	
    }
    
    public void reload(){
        
        u = loggedUnionBean.getU();
    	List<Employee> employees = payrollController.findAllUnionsEmployee(u.getName());
    	employeesList = new HashMap<>();
    	for (Employee employee : employees) {
    		employeesList.put(employee.getName()+" "+employee.getSurname(),employee.getId());
			System.out.println(employee.getName()+" "+employee.getSurname());
		}
    }
    
    private void setDate() {
		
        Date sqldate = new Date(calendarView.getDate().getTime());
        r.setDate(sqldate);
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
