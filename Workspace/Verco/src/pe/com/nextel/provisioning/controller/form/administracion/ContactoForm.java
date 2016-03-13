package pe.com.nextel.provisioning.controller.form.administracion;

import org.apache.struts.action.ActionForm;

import pe.com.nextel.provisioning.model.vo.ContactoVO;

public class ContactoForm extends ActionForm {
	private static final long serialVersionUID= 1L;
	private String seleccionado;
	private String method;
	private String strMensaje;
	private String tipoAccion;
	private ContactoVO contacto;
  


/**
   * Constructor de la clase 
   */
  public ContactoForm() {
	super();
	contacto = new ContactoVO();
  }
  
  /**
   * Metodo que inicializa
   */
  public void inicializar()  {
	  contacto = new ContactoVO();
	  strMensaje = "";
	  seleccionado = "";
	  method = "";
  }
	
  /**
   * Metodo getter del atributo method
   * @return String
   */
  public String getMethod() {
	return method;
  }
  
  /**
   * Metodo setter del atributo metodo
   * @param String method
   */
  public void setMethod(String method) {
	this.method = method;
  }
  
  /**
   * Metodo getter del atributo strMensaje
   * @return String
   */
  public String getStrMensaje() {
	return strMensaje;
  }
  
  /**
   * Metodo setter del atributo strMensaje
   * @param String strMensaje
   */
  public void setStrMensaje(String strMensaje) {
	this.strMensaje = strMensaje;
  }
  
	
	public String getTipoAccion() {
		return tipoAccion;
	}
	
	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}  
  
	public String getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(String seleccionado) {
		this.seleccionado = seleccionado;
	}	
	
	  public ContactoVO getContacto() {
			return contacto;
		}

		public void setContacto(ContactoVO contacto) {
			this.contacto = contacto;
		}
		
}
