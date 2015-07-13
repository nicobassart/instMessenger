package ar.com.instMessenger.bean.persona;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import ar.com.instMessenger.bean.Bean;
import ar.com.instMessenger.bean.usuarios.UsuarioBean;
import ar.com.instMessenger.entity.Agenda;
import ar.com.instMessenger.entity.Persona;
import ar.com.instMessenger.entity.Usuario;
import ar.com.instMessenger.servicios.dao.IUsuariosDao;
import ar.com.instMessenger.servicios.dao.agenda.IAgendaDao;
import ar.com.instMessenger.servicios.dao.persona.IPersonaDao;

@Named
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class PersonaBean extends Bean {
	private static final long serialVersionUID = -8725702349262618823L;

	private static final int ROWS_DATATABLE = 10;

	@Autowired
	IUsuariosDao usuariosDao;
	@Autowired
	UsuarioBean usuarioBean;
	
	@Autowired
	IPersonaDao personaDAO;
	
	@Autowired
	IAgendaDao agendaDAO;
	
	private List<Persona> personas = new ArrayList<Persona>();

	private String selectedAgenda;
	
	private Persona[] selectedPersonas;
	
	private boolean skip;

	@PostConstruct
	public void postConstructor() {
		//personas = personaDAO.getPersonas();
		this.getAgendaDefoult();
	}
	//Si el tipo tiene al menos una agenda la cargamos.
	private void getAgendaDefoult(){
		Usuario usuario = usuariosDao.getUsuario(usuarioBean.getUserName());
		
		List<Agenda> listAgenda = agendaDAO.getAgendas(usuario.getId());
		
		if(listAgenda!=null && !listAgenda.isEmpty()){
			
			Agenda agenda=listAgenda.get(0);

			if(agenda!=null)
				personas = agenda.getPersonas();
		}
	}
	public void agregarPersona(ActionEvent event) {
		Persona persona = new Persona();
		persona.setEstado("ACTIVO");
		personas.add(persona);
		
		final DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:personasList");
			
		
		   int first = 1;
		    if (d.getRowCount() % ROWS_DATATABLE == 0) {
		        first = (d.getRowCount() - ROWS_DATATABLE);
		    }
		    else 
		    {
		        first = (d.getRowCount()/ROWS_DATATABLE)*ROWS_DATATABLE;
		    }
		    d.setFirst(first);
		
		
			int page = d.getPageCount();
			int link = d.getPageLinks();
			int rows = d.getRows();

//			d.setPFirst(d.getPageCount());
//		//personaDAO.add(persona);
	}

	public String eliminarPersona(Persona persona) {
		//personaDAO.remove(persona);
		personas.remove(persona);
		return null;
	}
	
	public String eliminarPersonas() {
		personas.clear();
		return null;
	}
	
	
	public void onEdit(RowEditEvent event) {

		Persona persona = (Persona) event.getObject();
		//personaDAO.update(persona);
		
		//personas.remove(persona);
		//personas.add(persona);

		FacesMessage msg = new FacesMessage("Parametro Editado",
				persona.getApellido() + ", " + persona.getNombre());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		Persona persona = (Persona) event.getObject();
		persona = new Persona();
		FacesMessage msg = new FacesMessage("Parametro Cancelado",
				persona.getApellido() + ", " + persona.getNombre());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String onFlowProcess(FlowEvent event) {
		// logger.info("Current wizard step:" + event.getOldStep());
		// logger.info("Next step:" + event.getNewStep());

		if (skip) {
			skip = false; // reset in case user goes back
			return "confirm";
		} else {
			return event.getNewStep();
		}
	}
	
	public void agregarPersonas(ActionEvent event) {
		//Si hubiera querido que el selectedAgenda sea un Objeto Agenda (y no un String), deber�a haber utilizado un "Converter"
		Agenda agenda = agendaDAO.find(selectedAgenda);
		if(agenda!=null)
			personas.addAll(agenda.getPersonas());
	}
	
	public void abmAgendas(ActionEvent event) {
		try {
			 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			 ec.redirect(ec.getRequestContextPath() + "/pages/agenda/abmcAgenda.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public IPersonaDao getPersonaDAO() {
		return personaDAO;
	}

	public void setPersonaDAO(IPersonaDao personaDAO) {
		this.personaDAO = personaDAO;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

	public String getSelectedAgenda() {
		return selectedAgenda;
	}

	public void setSelectedAgenda(String selectedAgenda) {
		this.selectedAgenda = selectedAgenda;
	}

	public Persona[] getSelectedPersonas() {
		return selectedPersonas;
	}

	public void setSelectedPersonas(Persona[] selectedPersonas) {
		this.selectedPersonas = selectedPersonas;
	}	

}
