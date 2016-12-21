package org.jboss.tools.examples.utils;
 
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
 
@ManagedBean
@SessionScoped
public class DropdownView implements Serializable {
     
	   private String country; 
	   private Map<String,Long> employees;
	 
	 
	    public String getCountry() {
	        return country;
	    }
	 
	    public void setCountry(String country) {
	        this.country = country;
	    }
	   
	  

		public Map<String, Long> getEmployees() {
			return employees;
		}

		public void setEmployees(Map<String, Long> employees) {
			this.employees = employees;
		}

		public void displayLocation() {
	        FacesMessage msg;
	        if( country != null)
	            msg = new FacesMessage("Selected "+ country);
	        else
	            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected."); 
	             
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	    }
}