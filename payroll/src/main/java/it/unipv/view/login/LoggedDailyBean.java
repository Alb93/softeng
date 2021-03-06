package it.unipv.view.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import it.unipv.model.employees.DailyEmployee;

@SuppressWarnings("serial")
@Named
@SessionScoped
public class LoggedDailyBean implements Serializable, ILogout {
	
	
	@Inject
	private LoginProxy loginProxy;
	
    @PostConstruct
	public void init() {
		loginProxy.setActualLogin(javax.enterprise.inject.spi.CDI.current().select(LoginDailyBean.class).get());
        showEmp();
    }
    
    private DailyEmployee empl;
    
    
    public void showEmp() {
    	empl = (DailyEmployee) loginProxy.getActualLogin().getEmployee();
    }
    
    public DailyEmployee getEmpl() {
		return empl;
	}
    
    public void setEmpl(DailyEmployee empl) {
		this.empl = empl;
	}

	@Override
	public String logout() {
		((LoginDailyBean) loginProxy.getActualLogin()).setEmpl(new DailyEmployee());
		return "success";
	}
	
	public LoginProxy getLoginProxy() {
		return loginProxy;
	}
    
    

}
