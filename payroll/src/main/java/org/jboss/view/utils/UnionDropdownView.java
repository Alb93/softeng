package org.jboss.view.utils;
 
import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class UnionDropdownView implements Serializable {
     
    private String union; 
    private ArrayList<String> unions;
 
 
    public void setUnion(String union) {
		this.union = union;
	}
    
    public String getUnion() {
		return union;
	}
   
    public void setUnions(ArrayList<String> unions) {
		this.unions = unions;
	}
    
    public ArrayList<String> getUnions() {
		return unions;
	}
    

	public void displayLocation() {
        FacesMessage msg;
        if( union != null)
            msg = new FacesMessage("Selected "+ union);
        else
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid", "City is not selected."); 
             
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }
}