package org.jboss.view.login;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class LoginProxyClient implements Serializable {

	@Inject
	private LoginProxy loginProxy;

	public String performLogin() {
		loginProxy.setActualLogin(javax.enterprise.inject.spi.CDI.current().select(LoginDailyBean.class).get());
		
		if (!loginProxy.checkLogin().equals(loginProxy.getActualLogin().getSuccessfulString())) {
			loginProxy.setActualLogin(javax.enterprise.inject.spi.CDI.current().select(LoginMonthlyBean.class).get());
			if (loginProxy.checkLogin().equals(loginProxy.getActualLogin().getSuccessfulString())) {
				return loginProxy.checkLogin();
			}

			else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Invalid credentials");
				RequestContext.getCurrentInstance().showMessageInDialog(message);
				return "";
			}
		} else {
			return loginProxy.checkLogin();
		}
	}


}
