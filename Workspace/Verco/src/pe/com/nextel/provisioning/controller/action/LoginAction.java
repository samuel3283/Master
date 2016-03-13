package pe.com.nextel.provisioning.controller.action;

import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import pe.com.nextel.provisioning.controller.form.LoginForm;
import pe.com.nextel.provisioning.model.vo.UsuarioVO;

/**
 * <p>Title: Logeo al Sistema</p>
 * <p>Description: Clase controladora encargada del flujo del aplicativo.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : LoginAction</p>
 * <p>Fecha       : 20 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  SAMUEL NAVARRO (COMSA)
 * @version 1.0
 */

public class LoginAction extends Action {
  private static final Log log = LogFactory.getLog(LoginAction.class);

  /**
   * Metodo execute
   * @param mapping ActionMapping
   * @param form ActionForm
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param session HttpSession
   * @throws Exception
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
  HttpServletResponse response) throws Exception {
    ActionForward actionforward = null;
	HttpSession session = request.getSession(true);
	String method = "";
	try{  
	  if (request.getParameter("method") == null) {
        method = "inicio";
	  } else {
	    method = request.getParameter("method").toString();
	  }
	  if ("inicio".equals(method)) {
		actionforward = inicio(mapping, form, request, response, session);
	  } else if ("login".equals(method)) {
	    actionforward = login(mapping, form, request, response, session);
	  } 
	} catch (Exception e){
	} finally {
    }
    return actionforward;
  }
	
  /**
   * Metodo inicio
   * @param mapping ActionMapping
   * @param form ActionForm
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param session HttpSession
   * @throws Exception
   */
  public ActionForward inicio(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
  HttpServletResponse response,HttpSession session) throws IOException, ServletException {
   	String forwardString = "inicio";
  	LoginForm formulario = (LoginForm)form;
   	formulario.inicializar();
   	if (log.isInfoEnabled())  {
      log.info(" => Call LoginAction.inicio ::: ");    		
  	}
   	return mapping.findForward(forwardString);
  }


  /**
   * Metodo inicio
   * @param mapping ActionMapping
   * @param form ActionForm
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param session HttpSession
   * @throws Exception
   */
  @SuppressWarnings("static-access")
  public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
  HttpServletResponse response,HttpSession session) throws Exception {
   	String forwardString = "inicio";
   	session = request.getSession();
   	LoginForm formulario = (LoginForm)form;
   	ResourceBundle ParmGene = ResourceBundle.getBundle("pe.com.nextel.provisioning.view.MessageResources");
  	if (log.isInfoEnabled())  {
      log.info(" => Call LoginAction.login ::: Usuario: "+formulario.getUsuarioBean().getUsuario());
  	}
 	//String valor = LoginBO.getInstance().validarUsuario(formulario.getUsuarioBean());
  	String valor = "1" ;
  	
  	if ( !formulario.getUsuarioBean().getUsuario().equals("admin") | !formulario.getUsuarioBean().getPassword().equals("admin")  ){
  		valor ="0" ;
  	}
  	
   	if ("0".equals(valor)) {
      forwardString = "inicio";
	  formulario.setUsuarioBean(new UsuarioVO());
	  formulario.setStrMensaje(ParmGene.getString("login.mensaje"));
	} else {	
	  formulario.setStrMensaje("");
	  forwardString = "cuenta";
	  UsuarioVO objUsuarioVO = new UsuarioVO();
	  objUsuarioVO.setNombre("portabilidad") ;
  	  //formulario.setUsuarioBean(LoginBO.getInstance().ObtenerUsuario(formulario.getUsuarioBean()));
	  formulario.setUsuarioBean(objUsuarioVO);
  	  if (log.isInfoEnabled())  {
        log.info(" => Call LoginAction.login ::: Usuario conectado: "+formulario.getUsuarioBean().getNombre());
   	  }
  	  session.setAttribute(ParmGene.getString("login.session"),formulario.getUsuarioBean());

   	}
    return mapping.findForward(forwardString);
  }
	    
  
}
