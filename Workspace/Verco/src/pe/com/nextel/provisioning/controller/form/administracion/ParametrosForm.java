package pe.com.nextel.provisioning.controller.form.administracion;

import org.apache.struts.action.ActionForm;

import pe.com.nextel.provisioning.model.vo.ParametrosVO;

public class ParametrosForm extends ActionForm {
	private static final long serialVersionUID= 1L;
	private String seleccionado;
	private String method;
	private String strMensaje;
	private ParametrosVO parametroBean;
	private String tipoAccion;
	private String strNombreCorto;
	private String strNombre;



/**
   * Constructor de la clase 
   */
  public ParametrosForm() {
	super();
	parametroBean = new ParametrosVO();
  }
  
  /**
   * Metodo que inicializa el Bean Parametros 
   */
  public void inicializar()  {
	  parametroBean = new ParametrosVO();
	strMensaje = "";
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
  
	public ParametrosVO getParametroBean() {
		return parametroBean;
	}
	
	public void setParametroBean(ParametrosVO parametroBean) {
		this.parametroBean = parametroBean;
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

	public String getStrNombreCorto() {
		return strNombreCorto;
	}

	public void setStrNombreCorto(String strNombreCorto) {
		this.strNombreCorto = strNombreCorto;
	}

	public String getStrNombre() {
		return strNombre;
	}

	public void setStrNombre(String strNombre) {
		this.strNombre = strNombre;
	}
	
}

