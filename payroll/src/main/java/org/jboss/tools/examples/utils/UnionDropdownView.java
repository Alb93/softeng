package org.jboss.tools.examples.utils;
 
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
 
@ManagedBean
@ViewScoped
public class UnionDropdownView implements Serializable {
     
    private String country; 
    private Map<String,Long> unions;
 
 
    public String getCountry() {
        return country;
    }
 
    public void setCountry(String country) {
        this.country = country;
    }
   
  

	public Map<String, Long> getUnions() {
		return unions;
	}

	public void setUnions(Map<String, Long> unions) {
		this.unions = unions;
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