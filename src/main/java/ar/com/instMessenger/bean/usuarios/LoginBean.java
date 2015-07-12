package ar.com.instMessenger.bean.usuarios;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.context.WebApplicationContext;

@Named
@Scope(value = WebApplicationContext.SCOPE_REQUEST)

public class LoginBean implements Serializable {

	final Logger logger = LoggerFactory.getLogger(LoginBean.class);

	private static final long serialVersionUID = 2868742783741899100L;
	private Boolean isAdmin = false;
	private String userName;
	private String password;
	
	@Autowired
	private UsuarioBean usuario;
	

	@Resource(name = "authenticationManager")
	private AuthenticationManager am;

	public LoginBean() {

	}

	// ActionEvent actionEvent
	public String login() throws java.io.IOException {
		try {
			Authentication req = new UsernamePasswordAuthenticationToken(this.getUserName(), getPassword());
			Authentication result = am.authenticate(req);
			SecurityContextHolder.getContext().setAuthentication(result);
			// person = (Persona)
			// SecurityContextHolder.getContext().getAuthentication().getDetails();
			User user = (User) result.getPrincipal();
			
			
			SecurityContextHolder.getContext().getAuthentication().getDetails();
			logger.info("Login Success! ..");
			
			usuario.setUserName(user.getUsername());
			usuario.setPassword(password);
			// HttpRequest request = (HttpRequest)
			// FacesContext.getCurrentInstance().getExternalContext().getRequest();

			// System.out.println("is Admin : "+request.isUserInRole("ROLE_ADMIN"));
			isAdmin = true;

			return "loginOK";
		} catch (IllegalArgumentException ie) {
			System.out.println("Login Failed on IllegalArgumentException");
			System.out.println(ie.getMessage());
			FacesContext.getCurrentInstance()
					.addMessage(
							"formLogin",
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"Login Failed",
									"User Name and Password Not Match!"));

			return "/login";
		}
	}

	public void logout() {
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			SecurityContextHolder.getContext().setAuthentication(null);
			 ec.getSessionMap().clear();
			 ec.redirect(ec.getRequestContextPath() + "/logout.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void homePage(ActionEvent event) {
		try {
			 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			 ec.redirect(ec.getRequestContextPath() + "/pages/envioMsj/envioAlerta.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLogoutHidden() {
		// System.out.println("LoginBean.getLogoutHidden()....");
		SecurityContextHolder.getContext().setAuthentication(null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.clear();
		return "logout";
	}

	public void setLogoutHidden(String logoutHidden) {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}