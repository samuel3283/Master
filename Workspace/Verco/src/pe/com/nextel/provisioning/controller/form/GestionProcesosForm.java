package pe.com.nextel.provisioning.controller.form;

import org.apache.struts.action.ActionForm;

import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;

/**
 * <p>Title: Formulario de Login</p>
 * <p>Description: Clase Form de logeo.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : LoginForm</p>
 * <p>Fecha       : 20 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  FRANK PICOY (COMSA)
 * @version 1.0
 */

public class GestionProcesosForm extends ActionForm {
  private static final long serialVersionUID= 1L;
  private String method;
  private CabeceraArchivoVO cabeceraArchivo;
  
  /**
   *  Constructor de clase GestionProcesosForm 
   */
  public GestionProcesosForm() {
	super();
	cabeceraArchivo = new CabeceraArchivoVO();
  }
/**
 * @return the method
 */
public String getMethod() {
	return method;
}
/**
 * @param method the method to set
 */
public void setMethod(String method) {
	this.method = method;
}
/**
 * @return the cabeceraArchivo
 */
public CabeceraArchivoVO getCabeceraArchivo() {
	return cabeceraArchivo;
}
/**
 * @param cabeceraArchivo the cabeceraArchivo to set
 */
public void setCabeceraArchivo(CabeceraArchivoVO cabeceraArchivo) {
	this.cabeceraArchivo = cabeceraArchivo;
}

  
}
