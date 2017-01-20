package it.unipv.view.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class SessionBean implements Serializable{
	
	public String performLogout(ILogout bean){
		System.out.println("Il logout è " + bean.logout());
		return bean.logout();
	}

}
