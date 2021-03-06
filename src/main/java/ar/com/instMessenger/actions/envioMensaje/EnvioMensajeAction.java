package ar.com.instMessenger.actions.envioMensaje;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;

import ar.com.instMessenger.actions.Action;
import ar.com.instMessenger.bean.persona.PersonaBean;
import ar.com.instMessenger.bean.utils.UtilsBean;
import ar.com.instMessenger.entity.Persona;
import ar.com.instMessenger.servicios.colas.IServicioColas;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class EnvioMensajeAction extends Action implements IEnvioMensajeAction {

    private static final long serialVersionUID = -2261476298684088030L;
    final Logger logger = LoggerFactory.getLogger(EnvioMensajeAction.class);
    
    @Autowired
    private PersonaBean personaBean;

    private String texto;
    
    @Autowired
    private ApplicationContext appContext;
    
    @Autowired
    private IServicioColas servicioColas;
    
    @Autowired
    private UtilsBean utilsBean;

    public String enviarMsj() {

	RabbitTemplate template = appContext.getBean(RabbitTemplate.class);	
	MessageProperties messageProperties = new MessageProperties();
	for (Persona persona : personaBean.getPersonas()) {

//	    messageProperties.setHeader("telefono", persona.getCelular());
//
//	    MessageConverter converter = new SimpleMessageConverter();
//	    String texto1 = this.armarSMS(this.texto, persona);
//	    org.springframework.amqp.core.Message message = converter.toMessage(texto1, messageProperties);
//
//	    template.convertAndSend(message);
		if(persona!=null && persona.getCelular()!=null && persona.getCelular().length()>=6){
			String celular = persona.getCelularArea().substring(1)+ persona.getCelular().substring(3);
			servicioColas.encolarMensaje(utilsBean.reemplazarContenido(this.texto, persona),celular);
			logger.info("Encolando Cel: " +  persona.getCelularArea() + persona.getCelular());
		}else{
			if(persona!=null && persona.getCelular()!=null)
				logger.info(" Mensaje a Cel : " + persona.getCelularArea()+" - " + persona.getCelular()+" no enviado.");
			else
				logger.error("Persona o celuar faltan y no permite hacer el envio.");
		}
			
		
	}

	return "enviadoOK";
    }
    
	private String armarSMS(String texto2, Persona persona) {
		// TODO Auto-generated method stub
		return "goood";
	}

	public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
