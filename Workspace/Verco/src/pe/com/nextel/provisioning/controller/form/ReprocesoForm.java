package pe.com.nextel.provisioning.controller.form;

import org.apache.struts.action.ActionForm;

import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;

public class ReprocesoForm extends ActionForm {
	private static final long serialVersionUID= 1L;
	private String seleccionado;
	private String method;
	private String strMensaje;
	private String tipoAccion;
	private CabeceraArchivoVO cabeceraArchivo;
	private String estadoProceso;
	private String idCabecera;
	private String archivo;

/**
   * Constructor de la clase 
   */
  public ReprocesoForm() {
	super();
	cabeceraArchivo = new CabeceraArchivoVO();
  }
  
  /**
   * Metodo que inicializa el Bean Parametros 
   */
  public void inicializar()  {
	  cabeceraArchivo = new CabeceraArchivoVO();
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
  
	public String getSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(String seleccionado) {
		this.seleccionado = seleccionado;
	}	

  public CabeceraArchivoVO getCabeceraArchivo() {
		return cabeceraArchivo;
	}

	public void setCabeceraArchivo(CabeceraArchivoVO cabeceraArchivo) {
		this.cabeceraArchivo = cabeceraArchivo;
	}	

	public String getTipoAccion() {
		return tipoAccion;
	}

	public void setTipoAccion(String tipoAccion) {
		this.tipoAccion = tipoAccion;
	}	
	
	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}	

	public String getIdCabecera() {
		return idCabecera;
	}

	public void setIdCabecera(String idCabecera) {
		this.idCabecera = idCabecera;
	}	

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}	
}
