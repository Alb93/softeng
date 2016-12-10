package org.jboss.tools.examples.service;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class RegistratorManager implements Serializable {
	
	private IRegistrator registrator;

	public IRegistrator getRegistrator() {
		return registrator;
	}

	public void setRegistrator(IRegistrator registrator) {
		this.registrator = registrator;
	}
	

}
