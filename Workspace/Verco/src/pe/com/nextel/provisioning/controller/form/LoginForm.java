package pe.com.nextel.provisioning.controller.form;

import org.apache.struts.action.ActionForm;

import pe.com.nextel.provisioning.model.vo.UsuarioVO;

/**
 * <p>Title: Formulario de Login</p>
 * <p>Description: Clase Form de logeo.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : LoginForm</p>
 * <p>Fecha       : 20 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  SAMUEL NAVARRO (COMSA)
 * @version 1.0
 */

public class LoginForm extends ActionForm {
  private static final long serialVersionUID= 1L;
  private String method;
  private String strMensaje;
  private UsuarioVO usuarioBean;
  
  /**
   * Constructor de la clase 
   */
  public LoginForm() {
	super();
	usuarioBean = new UsuarioVO();
  }
  
  /**
   * Metodo que inicializa el Bean Usuario 
   */
  public void inicializar()  {
	usuarioBean = new UsuarioVO();
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
  
  /**
   * Metodo getter del atributo usuarioBean
   * @return UsuarioVO
   */
  public UsuarioVO getUsuarioBean() {
	return usuarioBean;
  }
  
  /**
   * Metodo setter del atributo usuarioBean
   * @param UsuarioVO usuarioBean
   */
  public void setUsuarioBean(UsuarioVO usuarioBean) {
	this.usuarioBean = usuarioBean;
  }
}
