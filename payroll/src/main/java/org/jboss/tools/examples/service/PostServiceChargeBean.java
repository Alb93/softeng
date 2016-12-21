package org.jboss.tools.examples.service;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.tools.examples.controller.PayrollController;
import org.jboss.tools.examples.model.employees.Employee;
import org.jboss.tools.examples.model.salesreceipt.SalesReceipt;
import org.jboss.tools.examples.model.union.ServiceCharge;
import org.jboss.tools.examples.model.union.Union;
import org.jboss.tools.examples.utils.CalendarView;
import org.jboss.tools.examples.utils.DropdownView;

@Named
@SessionScoped
public class PostServiceChargeBean implements Serializable {
	
	@Inject
    private Logger log;
	
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
    	List<Employee> employees = payrollController.findAllUnionsEmployee(u.getId());
    	Map<String,Long> list = new HashMap<>();
    	for (Employee employee : employees) {
			list.put(employee.getName()+" "+employee.getSurname(),employee.getId());
			System.out.println(employee.getName()+" "+employee.getSurname());
		}
    	
        dropdown.setEmployees(list);
		r = new ServiceCharge();
	}
    
    public void post() {
    
    	r.setId_emp(Long.parseLong(dropdown.getCountry()));
    	payrollController.postServiceCharge(r);
        log.info("posting " + r.getId_emp()+" sales receipt");
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
