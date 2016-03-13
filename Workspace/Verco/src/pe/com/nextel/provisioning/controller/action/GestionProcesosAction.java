package pe.com.nextel.provisioning.controller.action;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

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

import pe.com.nextel.provisioning.controller.form.GestionProcesosForm;
import pe.com.nextel.provisioning.model.bo.ApiBO;
import pe.com.nextel.provisioning.model.bo.CabeceraArchivoBO;
import pe.com.nextel.provisioning.view.ValueConstants;
//import pe.com.nextel.provisioning.controller.form.GestionProcesosForm;


/**
 * <p>Title: Gestion de Procesos</p>
 * <p>Description: Clase controladora encargada del flujo del aplicativo.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : GestionProcesosAction</p>
 * <p>Fecha       : 20 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  FRANK PICOY ROSAS (COMSA)
 * @version 1.0
 */

public class GestionProcesosAction extends Action {
  private static final Log log = LogFactory.getLog(GestionProcesosAction.class);

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
  HttpServletResponse response) {
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
	  } else if ("procesar".equals(method)) {
		    actionforward = procesar(mapping, form, request, response, session);
	  }
		    //	  } else if ("ejecutarProceso".equals(method)) {
//		    actionforward = ejecutarProceso(mapping, form, request, response, session);
//	  } 
    } catch (Exception e){
	} finally {
    }
    return actionforward;
  }
	  
  /**
   * Metodo execute
   * @param mapping ActionMapping
   * @param form ActionForm
   * @param request HttpServletRequest
   * @param response HttpServletResponse
   * @param session HttpSession
   * @throws Exception
   */
  public ActionForward inicio(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
  HttpServletResponse response,HttpSession session) {
  	String forwardString = "inicio";
   	if (log.isInfoEnabled())  {
      log.info(" => Call GestionProcesosAction.inicio ::: ");    		
	}
   	return mapping.findForward(forwardString);
  }
	  
  public ActionForward procesar(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
		  HttpServletResponse response,HttpSession session) {
		  	String forwardString = "procesar";
		   	if (log.isInfoEnabled())  {
		      log.info(" => Action procesar ::: ");    		
			}
		   	request.setAttribute("bandera", null);
		   	return mapping.findForward(forwardString);
  }
  
//  public ActionForward ejecutarProceso(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
//		  HttpServletResponse response,HttpSession session) {
//		  	String forwardString = "procesar";
//		   	
//		        		
//			GestionProcesosForm formulario = (GestionProcesosForm) form;
//		   	
//		   	
//		   	Date date = new Date();
//		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
//		    String fechaActual = formatter.format(date);
//		    log.info(" => Action ejecutarProceso :::==>ACTUAL:"+fechaActual+"==>fechaProceso:"+formulario.getCabeceraArchivo().getFecha());
//		    
//            int dia = new Integer(String.valueOf(fechaActual.substring(0,2))).intValue();
//            int mes = new Integer(String.valueOf(fechaActual.substring(3,5))).intValue();
//            int anio = new Integer(String.valueOf(fechaActual.substring(6,10))).intValue();
//            GregorianCalendar gc = new GregorianCalendar();
//            gc.set(anio, mes - 1, dia); 
//            Timestamp fechaAhora = new Timestamp(gc.getTime().getTime());
//            
//            int diap = new Integer(String.valueOf(formulario.getCabeceraArchivo().getFecha().substring(0,2))).intValue();
//            int mesp = new Integer(String.valueOf(formulario.getCabeceraArchivo().getFecha().substring(3,5))).intValue();
//            int aniop = new Integer(String.valueOf(formulario.getCabeceraArchivo().getFecha().substring(6,10))).intValue();
//            GregorianCalendar gcp = new GregorianCalendar();
//            gcp.set(aniop, mesp - 1, diap); 
//            Timestamp fechaProceso = new Timestamp(gcp.getTime().getTime());
//
//		    if (fechaProceso.compareTo(fechaAhora) == 1) 
//            {
//		    log.info(" => ENTRO :==>ACTUAL:"+fechaActual+"==>fechaProceso:"+formulario.getCabeceraArchivo().getFecha());
//		 	request.setAttribute("bandera", ValueConstants.VALOR_TRES);
//		    	
//            }
//		    else {
//		    String resultado = ReprocesoBO.getInstance().asignarProcesoPendiente(formulario.getCabeceraArchivo().getFecha(), ValueConstants.VALOR_UNO);
//		   	log.info(" => Ejecutar Proceso archivos con fecha ::: "+formulario.getCabeceraArchivo().getFecha()+"==> resultado: "+resultado);    
//		   	 
//		   	if(ValueConstants.PROCESO_SATISFACTORIO.equals(resultado)) {
//			   	request.setAttribute("bandera", ValueConstants.VALOR_UNO);
//		   	} else {
//		   		request.setAttribute("bandera", ValueConstants.VALOR_DOS);
//		   	}
//		    }
//		   	return mapping.findForward(forwardString);
//  }
  
}
