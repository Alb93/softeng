package org.jboss.view.post;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.controller.PayrollController;
import org.jboss.model.employees.Employee;
import org.jboss.model.union.ServiceCharge;
import org.jboss.model.union.Union;
import org.jboss.view.login.LoginUnionBean;
import org.jboss.view.utils.DropdownView;

@Named
@SessionScoped
public class PostServiceChargeBean implements Serializable {
	
	
	@Inject
	private PayrollController payrollController;
    
    private ServiceCharge r;
	private DropdownView dropdown;
	private Union u;

    
    @PostConstruct
	public void init() {
    	System.out.println("init del post ser");
    	FacesContext context = FacesContext.getCurrentInstance();
        dropdown = (DropdownView) context.getApplication().evaluateExpressionGet(context, "#{dropdownView}", DropdownView.class);
        LoginUnionBean loginUnionBean = (LoginUnionBean) context.getApplication().evaluateExpressionGet(context, "#{loginUnionBean}", LoginUnionBean.class);
        u = loginUnionBean.getU();
        System.out.println("il nome union è" + u.getName());
    	List<Employee> employees = payrollController.findAllUnionsEmployee(u.getName());
    	Map<String,Integer> list = new HashMap<>();
    	for (Employee employee : employees) {
			list.put(employee.getName()+" "+employee.getSurname(),employee.getId());
			System.out.println("la lista" + employee.getName()+" "+employee.getSurname());
		}
    	
        dropdown.setEmployees(list);
		r = new ServiceCharge();
	}
    
    public void post() {
    
    	r.setEmp_id(Integer.parseInt((dropdown.getCountry())));
    	payrollController.postServiceCharge(r);
        //log.info("posting " + r.getId_emp()+" sales receipt");
    }
    
    public void reload(){
    	FacesContext context = FacesContext.getCurrentInstance();
        dropdown = (DropdownView) context.getApplication().evaluateExpressionGet(context, "#{dropdownView}", DropdownView.class);
        LoginUnionBean loginUnionBean = (LoginUnionBean) context.getApplication().evaluateExpressionGet(context, "#{loginUnionBean}", LoginUnionBean.class);
        u = loginUnionBean.getU();
    	List<Employee> employees = payrollController.findAllUnionsEmployee(u.getName());
    	Map<String,Integer> list = new HashMap<>();
    	for (Employee employee : employees) {
			list.put(employee.getName()+" "+employee.getSurname(),employee.getId());
			System.out.println(employee.getName()+" "+employee.getSurname());
		}
    	
        dropdown.setEmployees(list);
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

}
