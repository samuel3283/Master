package pe.com.nextel.provisioning.controller.action;

import java.io.File;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import pe.com.nextel.provisioning.controller.form.ConsultaForm;
import pe.com.nextel.provisioning.framework.util.ManagerFile;
import pe.com.nextel.provisioning.model.bo.ConsultaBO;
import pe.com.nextel.provisioning.model.bo.PreFacturacionBO;
import pe.com.nextel.provisioning.model.bo.SolicitudBO;
import pe.com.nextel.provisioning.model.dao.ConsultaDAO;
import pe.com.nextel.provisioning.model.vo.Cliente;
import pe.com.nextel.provisioning.model.vo.Consulta1VO;
import pe.com.nextel.provisioning.model.vo.PortabilidadVO;
import pe.com.nextel.provisioning.model.vo.PreFacturacionVO;
import pe.com.nextel.provisioning.model.vo.Producto;
import pe.com.nextel.provisioning.util.BundleGeneric;
import pe.com.nextel.provisioning.view.ValueConstants;

public class ConsultaAction extends DispatchAction {
	  private static final Log log = LogFactory.getLog(ConsultaAction.class);
	  
	  public ActionForward iniciar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: iniciar]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje("");
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			try {
//				String fechaInicio = "08012010 000001" ;
//				String fechaFin    = "08012010 235959"  ;
//				List consultaList = ConsultaBO.getInstance().consulta1(fechaInicio,fechaFin)   ;
//				if ( request.getSession().getAttribute(ValueConstants.LISTA_CONSULTA1 ) != null ) 
//					 request.getSession().removeAttribute(ValueConstants.LISTA_CONSULTA1 ) ;
//				
//				log.info("Talla de la lista ::: " + consultaList.size()) ;
//				
//				request.getSession().setAttribute(ValueConstants.LISTA_CONSULTA1,consultaList);
			
			}catch(Exception e){
				consultaForm.setStrMensaje(e.getMessage());
				e.printStackTrace();
				log.error(e);
			}
			log.info("[ConsultaAction ::: iniciar]FIN");
			return mapping.findForward(ValueConstants.FW_LISTAR_CONSULTA1);			
		}
	  
	  public ActionForward regcli(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: regcli]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje("");
			consultaForm.setClienteBean(new Cliente());

			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			
			try {

				
			}catch(Exception e){
				consultaForm.setStrMensaje(e.getMessage());
				e.printStackTrace();
				log.error(e);
			}
			log.info("[ConsultaAction ::: regcli]FIN");
			return mapping.findForward("registrocliente");			
		}
	  
	  public ActionForward registrocli(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: registrocli]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			
			log.info("getClienteBean::::"+consultaForm.getClienteBean().getCodcliente()+
					"==>getNombre():"+consultaForm.getClienteBean().getNombre());
			
			consultaForm.setClienteBean(new Cliente());
			consultaForm.setStrMensaje("Cliente registrado satisfactoriamente.");
			
			log.info("[ConsultaAction ::: registrocli]FIN");
			return mapping.findForward("registrocliente");			
		}
	  
	  
	  

	  public ActionForward conpro(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: conpro]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje(null);
			
			consultaForm.setProductoBean(new Producto());
			log.info("[ConsultaAction ::: conpro]FIN");
			return mapping.findForward("consultaproducto");			
		}
	  
	  public ActionForward buscapro(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: buscapro]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje(null);
			log.info("getProductoBean:::   ==>getTipoproducto:"+consultaForm.getProductoBean().getTipoproducto()+
					"==>getNombre():"+consultaForm.getProductoBean().getNombre());
			try {
				consultaForm.setListaMensajes(ConsultaBO.getInstance().buscarProductos(consultaForm.getProductoBean()));
			
			}catch(Exception e){
			consultaForm.setStrMensaje("Error Ocurrido: "+e.getMessage());
			log.error(e);
			}
			
			log.info("[ConsultaAction ::: buscapro]FIN");
			return mapping.findForward("consultaproducto");			
	}
	  
	  public ActionForward dispro(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  	ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje(null);
			log.info("[ConsultaAction ::: dispro]INICIO  ==>getCodigo:"+consultaForm.getCodigo());
			
			consultaForm.setProducto(new Producto());
			try {
			consultaForm.setProducto(ConsultaDAO.obtenerProducto(consultaForm.getCodigo()));
			}catch(Exception e){
			consultaForm.setStrMensaje("Error Ocurrido: "+e.getMessage());
			log.error(e);
			}
		
			log.info("[ConsultaAction ::: dispro]FIN");
			return mapping.findForward("disproducto");			
		}
	  
	  public ActionForward disponibilidad(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: disponibilidad]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje(null);
			
			
			log.info("[ConsultaAction ::: disponibilidad]FIN");
			return mapping.findForward("disproducto");			
		}

	  
	  public ActionForward pedidos(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: conpro]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje(null);
			
			consultaForm.setProductoBean(new Producto());
			log.info("[ConsultaAction ::: conpro]FIN");
			return mapping.findForward("carrito");			
		}

	  public ActionForward buscacarrito(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: buscapro]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje(null);
			log.info("getProductoBean:::   ==>getTipoproducto:"+consultaForm.getProductoBean().getTipoproducto()+
					"==>getNombre():"+consultaForm.getProductoBean().getNombre());
			try {
				consultaForm.setListaMensajes(ConsultaBO.getInstance().buscarProductos(consultaForm.getProductoBean()));
			
			}catch(Exception e){
			consultaForm.setStrMensaje("Error Ocurrido: "+e.getMessage());
			log.error(e);
			}
			
			log.info("[ConsultaAction ::: buscapro]FIN");
			return mapping.findForward("carrito");			
	}

	  
	  
	  public ActionForward iniciar2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: iniciar]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje("");
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			List listaMensajes = new ArrayList();
			try {
//				String fechaInicio = "08012010 000001" ;
//				String fechaFin    = "08012010 235959"  ;
//				List consultaList = ConsultaBO.getInstance().consulta1(fechaInicio,fechaFin)   ;
//				if ( request.getSession().getAttribute(ValueConstants.LISTA_CONSULTA1 ) != null ) 
//					 request.getSession().removeAttribute(ValueConstants.LISTA_CONSULTA1 ) ;				
//				log.info("Talla de la lista ::: " + consultaList.size()) ;
//				request.getSession().setAttribute(ValueConstants.LISTA_CONSULTA1,consultaList);
				
				listaMensajes = ConsultaBO.getInstance().consultaMensajes();
				
			}catch(Exception e){
				consultaForm.setStrMensaje(e.getMessage());
				e.printStackTrace();
				log.error(e);
			}
			request.setAttribute("listaMensajes", listaMensajes);
			log.info("[ConsultaAction ::: iniciar]FIN");
			return mapping.findForward(ValueConstants.FW_LISTAR_CONSULTA2);			
		}
	  
	  public ActionForward consultar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: consultar]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			String fInicio = "-" ;
			String fFin    = "-" ;
			Map map = new HashMap();
			try {
				fInicio = consultaForm.getConsulta1VO().getFecha_inicio() != null ? consultaForm.getConsulta1VO().getFecha_inicio() : "-" ;
				fFin    = consultaForm.getConsulta1VO().getFecha_fin() != null ? consultaForm.getConsulta1VO().getFecha_fin() : "-" ;
				
				Calendar calendar = new GregorianCalendar();
								
                String minuto = Integer.toString(calendar.get(Calendar.MINUTE)) ;
                minuto = minuto.length() == 1 ? "0"+minuto :  minuto ;
                String segundo = Integer.toString(calendar.get(Calendar.SECOND)) ;
                segundo = segundo.length() == 1 ? "0"+segundo :  segundo ;
				String hora = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY) );
				hora = hora.length() == 1 ? "0"+hora :  hora ;
				String time = hora+ ":" + minuto + ":" + segundo ;
				
				if ( ! fInicio.equals("-") | !fFin.equals("-") ){
				
					map.put("fInicio", fInicio);
					map.put("fFin", fFin) ;
					request.getSession().removeAttribute("sMap") ;
					request.getSession().setAttribute("sMap", map) ;
					
			   } else  if ( request.getSession().getAttribute("sMap") == null ){
									 
					map.put("fInicio", fInicio);
					map.put("fFin", fFin) ;
					request.getSession().removeAttribute("sMap") ;
					request.getSession().setAttribute("sMap", map) ;
					
				} else {
					
					Map mapTemp = (HashMap)request.getSession().getAttribute("sMap") ; 
					fInicio = (String)mapTemp.get("fInicio") ;
					fFin = (String)mapTemp.get("fFin") ;
				}
								
				String Iniformat = "00:00:00" ;
				String Finformat = "23:59:59" ;
				
				List consultaList1 = ConsultaBO.getInstance().consulta2(fInicio+" "+ Iniformat ,fFin+" " +Finformat )   ;
				List rechazo = ConsultaBO.getInstance().rechazo(fInicio+" "+ Iniformat ,fFin+" " +Finformat )   ;
				List portado = ConsultaBO.getInstance().portados(fInicio+" "+ Iniformat ,fFin+" " +Finformat )   ;
				List numerosPortado = ConsultaBO.getInstance().numeroSolicitados(fInicio+" "+ Iniformat ,fFin+" " +Finformat);
				
				Map parametros = ConsultaBO.getInstance().portadosMesAnterior(fInicio+" "+ Iniformat ,fFin+" " +Finformat);
				Map param = ConsultaBO.getInstance().rechazosUnicos(fInicio+" "+ Iniformat ,fFin+" " +Finformat);
				
				String resultado="Hay "+parametros.get("cantidadPortados")+" números portados, provenientes de solicitudes registradas el mes anterior"; //en "+parametros.get("valorPortados");
				String valor="Rechazos por  Solicitud: "+param.get("rechazoTotal")+"        \n\n         " +
							 "Rechazos    Subsanables: "+param.get("rechazoSubsanable")+"        \n\n         "+
							 "Rechazos No Subsanables: "+param.get("rechazoNoSubsanable");
						
				log.info("RESULTADO:::: " + valor) ;
				request.getSession().setAttribute("rechazosTotal",param.get("rechazoTotal"));
				request.getSession().setAttribute("rechazoSubsanable",param.get("rechazoSubsanable"));
				request.getSession().setAttribute("rechazoNoSubsanable",param.get("rechazoNoSubsanable"));
				
				if ( request.getSession().getAttribute("listaPortadosAnterior") != null ) 
					 request.getSession().removeAttribute("listaPortadosAnterior") ;
				request.getSession().setAttribute("listaPortadosAnterior",resultado);
				if ( request.getSession().getAttribute("totalRechazos") != null ) 
					 request.getSession().removeAttribute("totalRechazos") ;
				request.getSession().setAttribute("totalRechazos",valor);
				
				if ( request.getSession().getAttribute(ValueConstants.LISTA_CONSULTA2 ) != null ) 
					 request.getSession().removeAttribute(ValueConstants.LISTA_CONSULTA2 ) ;
				request.getSession().setAttribute(ValueConstants.LISTA_CONSULTA2,consultaList1);
				log.info("Talla de la lista 2 ::: " + consultaList1.size()) ;
				
				if ( request.getSession().getAttribute(ValueConstants.LISTA_RECHAZO ) != null ) 
					 request.getSession().removeAttribute(ValueConstants.LISTA_RECHAZO ) ;
				     request.getSession().setAttribute(ValueConstants.LISTA_RECHAZO,rechazo);
				log.info("Talla de la lista rechazo ::: " + rechazo.size()) ;
				
				if ( request.getSession().getAttribute(ValueConstants.LISTA_PORTADO ) != null ) 
					 request.getSession().removeAttribute(ValueConstants.LISTA_PORTADO ) ;
				     request.getSession().setAttribute(ValueConstants.LISTA_PORTADO,portado);
				log.info("Talla de la lista portado ::: " + portado.size()) ;
				
				if ( request.getSession().getAttribute(ValueConstants.LISTA_NUMERO_PORTADO) != null ) 
					 request.getSession().removeAttribute(ValueConstants.LISTA_NUMERO_PORTADO ) ;
				     request.getSession().setAttribute(ValueConstants.LISTA_NUMERO_PORTADO,numerosPortado);
			    log.info("Talla de la lista numeros portado ::: " + numerosPortado.size()) ;
								
				String cadenaFecha = "Fecha Inicio = " + fInicio + "; Fecha Fin = " + fFin + "; Hora = " + time ;
				if ( request.getSession().getAttribute("rFecha") != null ) 
					 request.getSession().removeAttribute("rFecha") ;
				     request.getSession().setAttribute("rFecha",cadenaFecha);
				     
				     
				     
							
			}catch(Exception e){
				consultaForm.setStrMensaje(e.getMessage());
				e.printStackTrace();
				log.error(e);
			}
						
			log.info("[ConsultaAction ::: consultar]FIN");
			
			return mapping.findForward("consulta0");
		}	
	
	  
	  public ActionForward iniciarMensajes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: iniciarMensajes]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje("");
			consultaForm.setPortabilidadVO(new PortabilidadVO());
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			try {
			
			}catch(Exception e){
				consultaForm.setStrMensaje(e.getMessage());
				e.printStackTrace();
				log.error(e);
			}
			log.info("[ConsultaAction ::: iniciarMensajes]FIN");
			return mapping.findForward("consultaM");			
		}
	  
	  public ActionForward consultarMensajes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: consultarMensajes]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			String  fInicio = "" ;
			Map map = new HashMap();
			try {
							
				log.info("consultaForm.getConsulta1VO().getFecha_inicio() ::: " + consultaForm.getConsulta1VO().getFecha_inicio() ) ;
				
				fInicio = consultaForm.getConsulta1VO().getFecha_inicio() != null ? consultaForm.getConsulta1VO().getFecha_inicio() : "-" ;
						
    		   if ( ! fInicio.equals("-")  ){
			
					map.put("fInicio", fInicio);
					request.getSession().removeAttribute("sMapMensaje") ;
					request.getSession().setAttribute("sMapMensaje", map) ;
					
			   } else if ( request.getSession().getAttribute("sMapMensaje") == null ){

					map.put("fInicio", fInicio);
					request.getSession().removeAttribute("sMapMensaje") ;
					request.getSession().setAttribute("sMapMensaje", map) ;
					
				} else {
					
					Map mapTemp = (HashMap)request.getSession().getAttribute("sMapMensaje") ; 
					fInicio = (String)mapTemp.get("fInicio") ;
					
				}
    		    log.info("fInicio ::: " + fInicio) ;
				//List consultaList = ConsultaBO.getInstance().consulta3(fInicio+" "+ "00:00:00")   ;
    		    List consultaList = ConsultaBO.getInstance().consulta3(fInicio);
				List consultaList1 = ConsultaBO.getInstance().consulta4(fInicio);
									
				if ( request.getSession().getAttribute(ValueConstants.LISTA_CONSULTAM ) != null ) 
					 request.getSession().removeAttribute(ValueConstants.LISTA_CONSULTAM ) ;
				     request.getSession().setAttribute(ValueConstants.LISTA_CONSULTAM, consultaList );
		   		     log.info("Talla de la lista Mensajes1 ::: " + consultaList.size()) ;
				     
		   		if ( request.getSession().getAttribute(ValueConstants.LISTA_CONSULTAM1 ) != null ) 
					 request.getSession().removeAttribute(ValueConstants.LISTA_CONSULTAM1 ) ;
				     request.getSession().setAttribute(ValueConstants.LISTA_CONSULTAM1, consultaList1 );
			   	     log.info("Talla de la lista Mensajes2 ::: " + consultaList1.size()) ;	
			   	     
			   	  if ( request.getSession().getAttribute(ValueConstants.FECHA_PROCESO ) != null ) 
						 request.getSession().removeAttribute(ValueConstants.FECHA_PROCESO ) ;
					     request.getSession().setAttribute(ValueConstants.FECHA_PROCESO, fInicio );
				   	     log.info("Fecha ::: " + fInicio) ;	 	     
			   	     
				
					  Calendar calendar = new GregorianCalendar();
						
		                String minuto = Integer.toString(calendar.get(Calendar.MINUTE)) ;
		                minuto = minuto.length() == 1 ? "0"+minuto :  minuto ;
		                String segundo = Integer.toString(calendar.get(Calendar.SECOND)) ;
		                segundo = segundo.length() == 1 ? "0"+segundo :  segundo ;
						String hora = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY) );
						hora = hora.length() == 1 ? "0"+hora :  hora ;
						String time = hora+ ":" + minuto + ":" + segundo ;	   	     
				   	     
						String cadenaFecha = "Fecha = " + fInicio +  "; Hora = " + time ;
						if ( request.getSession().getAttribute("rFecha") != null ) 
							 request.getSession().removeAttribute("rFecha") ;
						     request.getSession().setAttribute("rFecha",cadenaFecha); 	     
				   	     
			}catch(Exception e){
				consultaForm.setStrMensaje(e.getMessage());
				e.printStackTrace();
				log.error(e);
			}
			log.info("[ConsultaAction ::: consultarMensajes]FIN");
			//return mapping.findForward(ValueConstants.FW_LISTAR_CONSULTA1);	
			return mapping.findForward("consultaMC");
		}	
	  
	  
	  public ActionForward consultar1_ref(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[consultar1_ref :::");
			ConsultaForm consultaForm = (ConsultaForm)form;
			
			try {
				log.info(":: ARRAYS ::"+consultaForm.getListado().length);
			}catch(Exception e) {
				log.info(e.getMessage());
			}
			
		return mapping.findForward("consulta1");
	  }	
	  
	  
	  public ActionForward consultar1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: consultar]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			String cadena="";
			
			
			try {
			log.info(":: ARRAY ::"+consultaForm.getListado().length);
			
			if(consultaForm.getListado().length>0) {
				
			for(int i=0;i<consultaForm.getListado().length;i++) {
				if(i==0) {
					cadena=" ('"+consultaForm.getListado()[i]+"'";
				}
				else {
					cadena= cadena + " ,'"+consultaForm.getListado()[i]+"'";
				}
				
				if(i==consultaForm.getListado().length-1) {
					cadena = cadena +")";
				}
			}
			}
			else {
				cadena="0";	
			}
			
			}catch(Exception e) {
				log.info(e.getMessage());
				cadena="0";	
			}
			
			consultaForm.setListado(consultaForm.getListado());
			log.info(":: VALORES ::"+cadena);
			
			String fInicio = "-" ;
			String fFin    = "-" ;
			Map map = new HashMap();
			try {
				fInicio = consultaForm.getConsulta1VO().getFecha_inicio() != null ? consultaForm.getConsulta1VO().getFecha_inicio() : "-" ;
				fFin    = consultaForm.getConsulta1VO().getFecha_fin() != null ? consultaForm.getConsulta1VO().getFecha_fin() : "-" ;
				if ( ! fInicio.equals("-") | !fFin.equals("-") ){
				
					map.put("fInicio", fInicio);
					map.put("fFin", fFin) ;
					request.getSession().removeAttribute("sMap") ;
					request.getSession().setAttribute("sMap", map) ;
					
			   } else  if ( request.getSession().getAttribute("sMap") == null ){
									 
					map.put("fInicio", fInicio);
					map.put("fFin", fFin) ;
					request.getSession().removeAttribute("sMap") ;
					request.getSession().setAttribute("sMap", map) ;
					
				} else {
					
					Map mapTemp = (HashMap)request.getSession().getAttribute("sMap") ; 
					fInicio = (String)mapTemp.get("fInicio") ;
					fFin = (String)mapTemp.get("fFin") ;
				}
				
				List consultaList = ConsultaBO.getInstance().consulta1(fInicio+" "+ "00:00:00" ,fFin+" " +"23:59:59 ",cadena)   ;
						
				if ( request.getSession().getAttribute(ValueConstants.LISTA_CONSULTA1 ) != null ) 
					 request.getSession().removeAttribute(ValueConstants.LISTA_CONSULTA1 ) ;
				request.getSession().setAttribute(ValueConstants.LISTA_CONSULTA1,consultaList);
			    log.info("Talla de la lista 1 ::: " + consultaList.size()) ;
									
			    Calendar calendar = new GregorianCalendar();
				
              String minuto = Integer.toString(calendar.get(Calendar.MINUTE)) ;
              minuto = minuto.length() == 1 ? "0"+minuto :  minuto ;
              String segundo = Integer.toString(calendar.get(Calendar.SECOND)) ;
              segundo = segundo.length() == 1 ? "0"+segundo :  segundo ;
				String hora = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY) );
				hora = hora.length() == 1 ? "0"+hora :  hora ;
				String time = hora+ ":" + minuto + ":" + segundo ;
				
				String cadenaFecha = "Fecha Inicio = " + fInicio + "; Fecha Fin = " + fFin + "; Hora = " + time ;
				if ( request.getSession().getAttribute("rFecha") != null ) 
					 request.getSession().removeAttribute("rFecha") ;
				     request.getSession().setAttribute("rFecha",cadenaFecha);
			    
			    
			}catch(Exception e){
				consultaForm.setStrMensaje(e.getMessage());
				e.printStackTrace();
				log.error(e);
			}
						
			log.info("[ConsultaAction ::: consultar]FIN");
			//return mapping.findForward(ValueConstants.FW_LISTAR_CONSULTA1);	
			return mapping.findForward("consulta1");
		}	
	  	  
	 	  
	  
	  
	  public ActionForward facturacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[ConsultaAction ::: facturacion]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			
			
			try {
			
			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("facturacion");
		}	
	  	  
	  /*
	  public static void main(String[] args) {
		  String inicio="05/01/2010";
		  System.out.println("==>"+inicio.substring(0,2)+"---"+inicio.substring(3,5)+"---"+inicio.substring(6,10));
		  
	  }
	  */
	  
	  public ActionForward buscarFacturacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[ConsultaAction ::: facturacion]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaFacturacion = new ArrayList();
			try {
				log.info(" => Action :::==>FecIni:"+consultaForm.getConsulta1VO().getFecha_inicio()+"==>FecFin:"+consultaForm.getConsulta1VO().getFecha_fin());
			    
				String inicio=String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))+" 000000";
				String fin=String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))+" 235959";

				int dia = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))).intValue();
	            int mes = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))).intValue();
	            int anio = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))).intValue();
	            GregorianCalendar gc = new GregorianCalendar();
	            gc.set(anio, mes - 1, dia); 
	            Timestamp fechaInicio = new Timestamp(gc.getTime().getTime());
	            
	            int diap = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))).intValue();
	            int mesp = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))).intValue();
	            int aniop = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))).intValue();
	            GregorianCalendar gcp = new GregorianCalendar();
	            gcp.set(aniop, mesp - 1, diap); 
	            Timestamp fechaFin = new Timestamp(gcp.getTime().getTime());

			    if (fechaInicio.compareTo(fechaFin) == 1) 
	            {
			    log.info(" ===>FecIni:"+inicio+"==>FecFin:"+fin);
			 	request.setAttribute("bandera", ValueConstants.VALOR_TRES);
			    }
			    else {
			    consultaForm.setListaFacturacion(new ArrayList());	
		    	request.removeAttribute("bandera");
		    	listaFacturacion = ConsultaBO.getInstance().consultaFacturacion(inicio, fin);	
		    	log.info(" ===>Listado:"+listaFacturacion.size());
		    	request.setAttribute("listaFacturacion", listaFacturacion);
			    consultaForm.setListaFacturacion(listaFacturacion);
			    }
			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("facturacion");
		}	
	  
	  
	  public ActionForward limpiarFacturacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  	log.info("[ConsultaAction ::: limpiarFacturacion]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			
		  	try {
		  		request.removeAttribute("listaFacturacion");
		  		consultaForm.setListaFacturacion(null);
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("facturacion");
		}	
	  
	  
	  
	  
	  
	  /////////////////////////////////////////////////////////////
	  public ActionForward initMensajePep(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[initMensajePep ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;

			consultaForm.setListaMensajes(null);
			return mapping.findForward("mensajePep");
		}	
	  
	  public ActionForward buscarMensajePep(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[buscarMensajePep ::: ]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaMensajes = new ArrayList();
			try {
				log.info(" => Action :::==>FecIni:"+consultaForm.getConsulta1VO().getFecha_inicio()+"==>FecFin:"+consultaForm.getConsulta1VO().getFecha_fin());
			    
				String inicio=String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))+" 000000";
				String fin=String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))+" 235959";

				int dia = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))).intValue();
	            int mes = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))).intValue();
	            int anio = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))).intValue();
	            GregorianCalendar gc = new GregorianCalendar();
	            gc.set(anio, mes - 1, dia); 
	            Timestamp fechaInicio = new Timestamp(gc.getTime().getTime());
	            
	            int diap = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))).intValue();
	            int mesp = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))).intValue();
	            int aniop = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))).intValue();
	            GregorianCalendar gcp = new GregorianCalendar();
	            gcp.set(aniop, mesp - 1, diap); 
	            Timestamp fechaFin = new Timestamp(gcp.getTime().getTime());

			    if (fechaInicio.compareTo(fechaFin) == 1) 
	            {
			    log.info(" ===>FecIni:"+inicio+"==>FecFin:"+fin);
			 	request.setAttribute("bandera", ValueConstants.VALOR_TRES);
			    }
			    else {
			    consultaForm.setListaMensajes(new ArrayList());	
		    	request.removeAttribute("bandera");
		    	listaMensajes = ConsultaBO.getInstance().consultaMensajesPep(inicio, fin);	
		    	log.info(" ===>Listado:"+listaMensajes.size());
		    	
		    	//request.setAttribute("listaFacturacion", listaFacturacion);
			    if(listaMensajes.size()>0)
			    	consultaForm.setListaMensajes(listaMensajes);
			    else
			    	consultaForm.setListaMensajes(null);	
			    }
			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("mensajePep");
		}	
	  
	  
	  
	  public ActionForward initMensajeOcc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[initMensajeOcc ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			consultaForm.setListaMensajes(null);
			return mapping.findForward("consultaOCC");
		}	
	  
	  public ActionForward mensajeOccXls(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[mensajeOccXls ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;

			return mapping.findForward("consultaOCCXls");
		}	
	  
	  public ActionForward buscarMensajeOcc(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[buscarMensajeOcc ::: ]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaMensajes = new ArrayList();
			try {
				//consultaForm.getConsulta1VO().setFecha_fin(consultaForm.getConsulta1VO().getFecha_inicio());
				log.info(" => Action :::==>FecIni:"+consultaForm.getConsulta1VO().getFecha_inicio()+"==>FecFin:"+consultaForm.getConsulta1VO().getFecha_fin());
			    
				String inicio=String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))+" 000000";
				String fin=String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))+" 235959";
				

				int dia = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))).intValue();
	            int mes = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))).intValue();
	            int anio = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))).intValue();
	            GregorianCalendar gc = new GregorianCalendar();
	            gc.set(anio, mes - 1, dia); 
	            Timestamp fechaInicio = new Timestamp(gc.getTime().getTime());
	            
	            int diap = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))).intValue();
	            int mesp = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))).intValue();
	            int aniop = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))).intValue();
	            GregorianCalendar gcp = new GregorianCalendar();
	            gcp.set(aniop, mesp - 1, diap); 
	            Timestamp fechaFin = new Timestamp(gcp.getTime().getTime());

			    if (fechaInicio.compareTo(fechaFin) == 1) 
	            {
			    log.info(" ===>FecIni:"+inicio+"==>FecFin:"+fin);
			 	request.setAttribute("bandera", ValueConstants.VALOR_TRES);
			    }
			    else {
			    consultaForm.setListaMensajes(new ArrayList());	
		    	request.removeAttribute("bandera");
		    	listaMensajes = ConsultaBO.getInstance().consultaMensajesOcc(inicio, fin);	
		    	log.info(" ===>Listado:"+listaMensajes.size());
		    	
		    	//request.setAttribute("listaFacturacion", listaFacturacion);
			    if(listaMensajes.size()>0) {
			    	consultaForm.setListaMensajes(listaMensajes);
				 	request.getSession().setAttribute("listaMensajes",listaMensajes);
				 	request.getSession().setAttribute("fechaInicio",consultaForm.getConsulta1VO().getFecha_inicio());	    
				 	request.getSession().setAttribute("fechaFin",consultaForm.getConsulta1VO().getFecha_fin());	  

			    }
			    else {
			    	consultaForm.setListaMensajes(null);	
			    	request.getSession().removeAttribute("listaMensajes");
			    	
			    }
			    
			    }
			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("consultaOCC");
		}	
	  
	  
	  public ActionForward initMensajeRec(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[initMensajeRec ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			consultaForm.setListaMensajes(null);
			return mapping.findForward("consultaRec");
		}	
	 
	  public ActionForward buscarMensajeRec(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[buscarMensajeRec ::: ]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaMensajes = new ArrayList();
			try {
				//consultaForm.getConsulta1VO().setFecha_fin(consultaForm.getConsulta1VO().getFecha_inicio());
				log.info(" => Action :::==>FecIni:"+consultaForm.getConsulta1VO().getFecha_inicio()+"==>FecFin:"+consultaForm.getConsulta1VO().getFecha_fin());
			    
				String inicio=String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))+" 000000";
				String fin=String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))+" 235959";
				

				int dia = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))).intValue();
	            int mes = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))).intValue();
	            int anio = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))).intValue();
	            GregorianCalendar gc = new GregorianCalendar();
	            gc.set(anio, mes - 1, dia); 
	            Timestamp fechaInicio = new Timestamp(gc.getTime().getTime());
	            
	            int diap = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))).intValue();
	            int mesp = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))).intValue();
	            int aniop = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))).intValue();
	            GregorianCalendar gcp = new GregorianCalendar();
	            gcp.set(aniop, mesp - 1, diap); 
	            Timestamp fechaFin = new Timestamp(gcp.getTime().getTime());

			    if (fechaInicio.compareTo(fechaFin) == 1) 
	            {
			    log.info(" ===>FecIni:"+inicio+"==>FecFin:"+fin);
			 	request.setAttribute("bandera", ValueConstants.VALOR_TRES);
			    }
			    else {
			    consultaForm.setListaMensajes(new ArrayList());	
		    	request.removeAttribute("bandera");
		    	listaMensajes = ConsultaBO.getInstance().consultaMensajesRec(inicio, fin);	
		    	log.info(" ===>Listado:"+listaMensajes.size());
		    	
		    	//request.setAttribute("listaFacturacion", listaFacturacion);
			    if(listaMensajes.size()>0) {
			    	consultaForm.setListaMensajes(listaMensajes);
				 	request.getSession().setAttribute("listaMensajes",listaMensajes);
				 	request.getSession().setAttribute("fechaInicio",consultaForm.getConsulta1VO().getFecha_inicio());	    
				 	request.getSession().setAttribute("fechaFin",consultaForm.getConsulta1VO().getFecha_fin());	  

			    }
			    else {
			    	consultaForm.setListaMensajes(null);	
			    	request.getSession().removeAttribute("listaMensajes");
			    	
			    }
			    
			    }
			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("consultaRec");
		}	
	  
	  public ActionForward mensajeRecXls(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[mensajeRecXls ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;

			return mapping.findForward("consultaRecXls");
		}	

	  
	  
	  public ActionForward initConsultaSolicitudes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[initConsultaSolicitudes ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			consultaForm.setListaMensajes(null);
	    	request.getSession().removeAttribute("listaAlarmas");
		 	request.getSession().removeAttribute("listaMensajes");
		 	request.getSession().setAttribute("listaEstados", new ArrayList());

			return mapping.findForward("consultaSol");
		}	
	  
	  public ActionForward buscarSolicitudes(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[Solicitudes ::: ]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaMensajes = new ArrayList();
			Consulta1VO consulta= new Consulta1VO();
			consulta=consultaForm.getConsulta1VO();
			String processInstance = "";
			List listaAlarmas = new ArrayList();
			List listaEstados = new ArrayList();
			try {
				log.info(" => Action :::==>TIP REP:"+consulta.getTipoReporte()+" ==>SEC:"+consulta.getNumeroSecuencial()+" ==>ID PRO:"+consulta.getIdentificadorProceso()+" ==> ID PROC::"+consulta.getBusqueda());
			    
				consultaForm.setListaMensajes(new ArrayList());	
		    	request.removeAttribute("bandera");

		    	if(consulta.getTipoReporte().equals("P")) {
		    		consulta.setIdentificadorProceso(consulta.getBusqueda());
		    	}
		    		
		    	listaMensajes = SolicitudBO.getInstance().consultarSolicitudes(consulta.getTipoReporte(),consulta.getIdentificadorProceso(),consulta.getNumeroSecuencial());	
		    	log.info(" ===>Listado:"+listaMensajes.size());
		    	
		    	if(listaMensajes.size()>0) {
			    	processInstance = SolicitudBO.getInstance().obtenerProcessInstance(consulta.getTipoReporte(),consulta.getIdentificadorProceso(),consulta.getNumeroSecuencial());
			    	log.info(" ===>processInstance:"+processInstance);
			    	listaAlarmas = SolicitudBO.getInstance().consultarAlarmas(processInstance);
			    	log.info(" ===>listaAlarmas:"+listaAlarmas.size());
			    	listaEstados = SolicitudBO.getInstance().consultarEstados();
			    	log.info(" ===>listaAlarmas:"+listaEstados.size());
			    	consultaForm.setListaMensajes(listaMensajes);
			    	consultaForm.setListaAlarmas(listaAlarmas);
			    	request.getSession().setAttribute("listaAlarmas",listaAlarmas);
				 	request.getSession().setAttribute("listaMensajes",listaMensajes);
				 	request.getSession().setAttribute("listaEstados",listaEstados);
				 	request.getSession().setAttribute("fechaInicio",consultaForm.getConsulta1VO().getFecha_inicio());	    
				 	request.getSession().setAttribute("fechaFin",consultaForm.getConsulta1VO().getFecha_fin());	  
			    }
			    else {
			    	consultaForm.setListaMensajes(null);	
			    	request.getSession().removeAttribute("listaMensajes");
			    	
			    }
			    
			    consulta.setIdentificadorProceso("");
			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("consultaSol");
		}	
	  
	  
	  public ActionForward initConsultaSolicitudesNumero(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[initConsultaSolicitudesNumero ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			consultaForm.setListaMensajes(null);
	    	request.getSession().removeAttribute("listaAlarmas");
		 	request.getSession().removeAttribute("listaMensajes");
		 	request.getSession().setAttribute("listaEstados", new ArrayList());
		 	
			return mapping.findForward("consultaNum");
		}	
	  
	  public ActionForward buscarSolicitudesNumero(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  	log.info("[buscarSolicitudesNumero ::: ]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaMensajes = new ArrayList();
			Consulta1VO consulta= new Consulta1VO();
			consulta=consultaForm.getConsulta1VO();
			String processInstance = "";
			List listaAlarmas = new ArrayList();
			List listaEstados = new ArrayList();
			try {
				//Tipo: S: Numero	 P: Trk
				log.info(" => Action :::==>TIP REP:"+consulta.getTipoReporte()+" ==>Num:"+consulta.getTelefono()+" ==>Trk:"+consulta.getTrk());
			    
				consultaForm.setListaMensajes(new ArrayList());	
		    	request.removeAttribute("bandera");

		    	listaMensajes = SolicitudBO.getInstance().consultarListaSolicitudes(consulta.getTipoReporte(),consulta.getTelefono(),consulta.getTrk());	
		    	log.info(" ===>Listado:"+listaMensajes.size());
		    	
		    	if(listaMensajes.size()>0) {
			    	consultaForm.setListaMensajes(listaMensajes);
				 	request.getSession().setAttribute("listaMensajes",listaMensajes);
				 	request.getSession().setAttribute("fechaInicio",consultaForm.getConsulta1VO().getFecha_inicio());	    
				 	request.getSession().setAttribute("fechaFin",consultaForm.getConsulta1VO().getFecha_fin());	  
			    }
			    else {
			    	consultaForm.setListaMensajes(null);	
			    	request.getSession().removeAttribute("listaMensajes");			    	
			    }
			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("consultaNum");
		}	
	  
	  
	  public ActionForward initConsultaEstadsitica(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[initConsultaEstadsitica ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			consultaForm.setListaMensajes(null);
		 	
			return mapping.findForward("consultaEst");
		}	
	  

	  public ActionForward buscarEstadistica(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[buscarEstadistica ::: ]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaMensajes = new ArrayList();

			String fInicio = "-" ;
			String fFin    = "-" ;
			
			fInicio = consultaForm.getConsulta1VO().getFecha_inicio() != null ? consultaForm.getConsulta1VO().getFecha_inicio() : "-" ;
			fFin    = consultaForm.getConsulta1VO().getFecha_fin() != null ? consultaForm.getConsulta1VO().getFecha_fin() : "-" ;
			
			String Iniformat = "00:00:00" ;
			String Finformat = "23:59:59" ;
			
			fInicio = fInicio+" "+Iniformat;
			fFin = fFin+" "+Finformat;
			
			
			try {
				log.info(" => Action :::==>Fec Ini:"+fInicio+" ==>Fec Fin:"+fFin);
			    
				consultaForm.setListaMensajes(new ArrayList());	
		    
		    	listaMensajes = ConsultaBO.getInstance().ConsultaEstadisticaPortabilidades(fInicio, fFin);	
		    	log.info(" ===>Listado:"+listaMensajes.size());
		    	consultaForm.setListaMensajes(listaMensajes);
			 	request.getSession().setAttribute("listaMensajes",listaMensajes);
			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("consultaEst");
		}	
	  
	  public ActionForward initConsultaEstadsiticaSr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  	log.info("[initConsultaEstadsiticaSr ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			consultaForm.setListaMensajes(null);
		 	
			return mapping.findForward("consultaEstSr");
		}	
		  
	  public ActionForward buscarEstadisticaSr(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		  
		  log.info("[buscarEstadisticaSr ::: ]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaMensajes = new ArrayList();
			
			String fInicio = "-" ;
			String fFin    = "-" ;
			
			fInicio = consultaForm.getConsulta1VO().getFecha_inicio() != null ? consultaForm.getConsulta1VO().getFecha_inicio() : "-" ;
			fFin    = consultaForm.getConsulta1VO().getFecha_fin() != null ? consultaForm.getConsulta1VO().getFecha_fin() : "-" ;
			
			String Iniformat = "00:00:00" ;
			String Finformat = "23:59:59" ;
			
			fInicio = fInicio+" "+Iniformat;
			fFin = fFin+" "+Finformat;
			
			
			try {
				log.info(" => Action :::==>Fec Ini:"+fInicio+" ==>Fec Fin:"+fFin);
			    
				consultaForm.setListaMensajes(new ArrayList());	
		    
		    	listaMensajes = ConsultaBO.getInstance().ConsultaEstadisticaPortabilidades(fInicio, fFin);	
		    	log.info(" ===>Listado:"+listaMensajes.size());
		    	consultaForm.setListaMensajes(listaMensajes);
			 	request.getSession().setAttribute("listaMensajes",listaMensajes);
			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("consultaEstSr");
		}	
	  
	  
	  ///// NUMERO PORTADOS
	  public ActionForward initNumerosPortados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  	log.info("[initNumerosPortados ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setConsulta1VO(new Consulta1VO());
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
			consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
			
			consultaForm.setListaMensajes(null);
			return mapping.findForward("numeroPortado");
		}	
	  
	  public ActionForward numerosPortadosXls(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[numerosPortadosXls ::: ");
			ConsultaForm consultaForm = (ConsultaForm)form;

			return mapping.findForward("numeroPortadoXls");
		}	
	  
	  public ActionForward buscarNumerosPortados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[buscarNumerosPortados ::: ]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaMensajes = new ArrayList();
			try {
				//consultaForm.getConsulta1VO().setFecha_fin(consultaForm.getConsulta1VO().getFecha_inicio());
				log.info(" => Action :::==>FecIni:"+consultaForm.getConsulta1VO().getFecha_inicio()+"==>FecFin:"+consultaForm.getConsulta1VO().getFecha_fin());
			    				
				String inicio=String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))+" 000000";
				String fin=String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))+String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))+" 235959";			
				
				int dia = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(0,2))).intValue();
	            int mes = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(3,5))).intValue();
	            int anio = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_inicio().substring(6,10))).intValue();
	            GregorianCalendar gc = new GregorianCalendar();
	            gc.set(anio, mes - 1, dia); 
	            Timestamp fechaInicio = new Timestamp(gc.getTime().getTime());
	            
	            int diap = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(0,2))).intValue();
	            int mesp = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(3,5))).intValue();
	            int aniop = new Integer(String.valueOf(consultaForm.getConsulta1VO().getFecha_fin().substring(6,10))).intValue();
	            GregorianCalendar gcp = new GregorianCalendar();
	            gcp.set(aniop, mesp - 1, diap); 
	            Timestamp fechaFin = new Timestamp(gcp.getTime().getTime());

			    if (fechaInicio.compareTo(fechaFin) == 1) 
	            {
			    log.info(" ===>FecIni:"+inicio+"==>FecFin:"+fin);
			 	request.setAttribute("bandera", ValueConstants.VALOR_TRES);
			    }
			    else {
			    consultaForm.setListaMensajes(new ArrayList());	
		    	request.removeAttribute("bandera");
		    	log.info(" ===>Action Listado:");
		    	listaMensajes = ConsultaBO.getInstance().consultaNumerosPortados(inicio, fin,consultaForm.getConsulta1VO().getReceptor());	
		    	log.info(" ===>Listado:"+listaMensajes.size());
		    	
			    if(listaMensajes.size()>0) {
			    	consultaForm.setListaMensajes(listaMensajes);
				 	request.getSession().setAttribute("listaMensajes",listaMensajes);
				 	request.getSession().setAttribute("fechaInicio",consultaForm.getConsulta1VO().getFecha_inicio());	    
				 	request.getSession().setAttribute("fechaFin",consultaForm.getConsulta1VO().getFecha_fin());	  
				 	request.getSession().setAttribute("receptor",consultaForm.getConsulta1VO().getReceptor());	  
			    }
			    else {
			    	consultaForm.setListaMensajes(null);	
			    	request.getSession().removeAttribute("listaMensajes");			    	
			    }
			    
			    }			    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("numeroPortado");
		}	
	   

	  public ActionForward iniciarSendXls(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: iniciarSendXls]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;
			consultaForm.setStrMensaje("");
			consultaForm.setConsulta1VO( new Consulta1VO() );
			List listaTipoReporte = new ArrayList();		
			
			Date date = new Date();
		    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		    String fechaActual = formatter.format(date);
			consultaForm.getReporteVO().setFechaInicio(fechaActual);
			consultaForm.getReporteVO().setFechaFin(fechaActual);
			try {			
				listaTipoReporte=(ArrayList)ConsultaBO.getInstance().consultaTipoProceso();
				consultaForm.getReporteVO().setListaTipoReporte(listaTipoReporte);			
			}catch(Exception e){
				consultaForm.setStrMensaje(e.getMessage());
				e.printStackTrace();
				log.error(e);
			}
			log.info("[ConsultaAction ::: iniciarSendXls]FIN");
			return mapping.findForward(ValueConstants.FW_LISTAR_CONSULTA3);			
		}
	  
	  
	  public ActionForward ConsultaProcesoJobReporte(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: ConsultaSendXls]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;						
			List listaTipoReporte = new ArrayList();
			String insertJob="";
			try {
				listaTipoReporte=(ArrayList)ConsultaBO.getInstance().consultaTipoProceso();
				consultaForm.getReporteVO().setListaTipoReporte(listaTipoReporte);
				insertJob=ConsultaBO.getInstance().insertProcesoJobReporte(consultaForm.getReporteVO());				
				if ("OK".equals(insertJob)){
					request.setAttribute("mensajeJobReporte","Su requerimiento esta siendo procesado. <br>Se le enviará en unos minutos por correo electrónico el archivo Excel con la información solicitada.");
				}else{
					request.setAttribute("mensajeJobReporte",insertJob);
				}
			}catch(Exception e){
				consultaForm.setStrMensaje(e.getMessage());
				e.printStackTrace();
				log.error(e);
			}
			log.info("[ConsultaAction ::: ConsultaProcesoJobReporte]FIN");
			return mapping.findForward(ValueConstants.FW_LISTAR_CONSULTA3);			
		}
	  
	  
	  public  String nombreMes(Integer nMes){
		  String mes="";
		  if (nMes==1) {mes="Enero";}
		  if (nMes==2) {mes="Febrero";}
		  if (nMes==3) {mes="Marzo";}
		  if (nMes==4) {mes="Abril";}
		  if (nMes==5) {mes="Mayo";}
		  if (nMes==6) {mes="Junio";}
		  if (nMes==7) {mes="Juli";}
		  if (nMes==8) {mes="Agosto";}
		  if (nMes==9) {mes="Setiembre";}
		  if (nMes==10){mes="Octubre";}
		  if (nMes==11){mes="Noviembre";}
		  if (nMes==12){mes="Diciembre";}
		  return mes;
	  }
	  public String obtenerHora(){
		  Calendar calendar = new GregorianCalendar();
		  String minuto = Integer.toString(calendar.get(Calendar.MINUTE)) ;
          minuto = minuto.length() == 1 ? "0"+minuto :  minuto ;
          String segundo = Integer.toString(calendar.get(Calendar.SECOND)) ;
          segundo = segundo.length() == 1 ? "0"+segundo :  segundo ;
		  String hora = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY) );
		  hora = hora.length() == 1 ? "0"+hora :  hora ;			
		  return hora+ ":" + minuto + ":" + segundo;
	  }

	
	



	  public ActionForward consultaInicioPreFacturacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
			log.info("[ConsultaAction ::: consultaInicioPreFacturacion]INICIO");
			ConsultaForm consultaForm = (ConsultaForm)form;			
			consultaForm.setPrefacturacionVO(new PreFacturacionVO());
			
			java.util.Date iniDate = new java.util.Date();
			Calendar cal = new GregorianCalendar();
			long iniMilisegundos = iniDate.getTime();
			Timestamp inicioTimestamp = new Timestamp(iniMilisegundos);
			SimpleDateFormat formatoFechaInicio = new SimpleDateFormat("dd/MM/yyyy");			
			consultaForm.getPrefacturacionVO().setFechaInicio("01/"+formatoFechaInicio.format(inicioTimestamp).substring(3,10));
			consultaForm.getPrefacturacionVO().setFechaFin(cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH)+"/"+formatoFechaInicio.format(inicioTimestamp).substring(3,10));
			consultaForm.getPrefacturacionVO().setMesInicio(formatoFechaInicio.format(inicioTimestamp).substring(3,5));
			consultaForm.getPrefacturacionVO().setAnioInicio(formatoFechaInicio.format(inicioTimestamp).substring(6,10));
			consultaForm.getPrefacturacionVO().setMesfin(formatoFechaInicio.format(inicioTimestamp).substring(3,5));
			consultaForm.getPrefacturacionVO().setAniofin(formatoFechaInicio.format(inicioTimestamp).substring(6,10));
			log.info("[ConsultaAction ::: consultaInicioPreFacturacion]FIN");
			return mapping.findForward("consultaPreFacturado");
		}
 public ActionForward consultaPreFacturacion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("[ConsultaAction ::: consultaPreFacturacion]INICIO");
		ConsultaForm consultaForm = (ConsultaForm)form;
		PreFacturacionVO consulta =consultaForm.getPrefacturacionVO();			
		String fInicio = (String)request.getSession().getAttribute("fInicios") ;
		String fFin    = (String)request.getSession().getAttribute("fFins") ;
		String tipoCons= consulta.getTipoCons()!=null?consulta.getTipoCons():(String)request.getSession().getAttribute("tipoCons") ;
		String nombreMes="";						
		String tipocon =request.getParameter("tipocon")!=null ?request.getParameter("tipocon"):"1";
		String forward="consulPreFacturado";
     String time=obtenerHora();
     if("P".equals(tipoCons)){
		if(consulta.getPeriodoInicio() != null){
		fInicio = consulta.getPeriodoInicio();
		fFin    = consulta.getPeriodoFin();
		tipoCons= consulta.getTipoCons();
		}}
     if("F".equals(tipoCons)){
     if(consulta.getFechaInicio()!=null){
		fInicio = consulta.getFechaInicio();
		fFin    = consulta.getFechaFin();
		tipoCons= consulta.getTipoCons();
		}}
     nombreMes=nombreMes(Integer.valueOf(fInicio.substring(1,2)));
		request.getSession().setAttribute("fInicios", fInicio);
		request.getSession().setAttribute("fFins", fFin);
		request.getSession().setAttribute("tipoCons", tipoCons);			
		
		String cadenaFecha = "Fecha Inicio = " + fInicio + "; Fecha Fin = " + fFin + "; Hora = " + time ;			
		if (request.getSession().getAttribute("rFechas") != null )
		request.getSession().removeAttribute("rFechas") ;
		request.getSession().setAttribute("rFechas",cadenaFecha);			
	
		request.getSession().setAttribute("nombreMes","<b>PreFacturación del mes de "+nombreMes+"<b>");
		request.getSession().setAttribute("consolInfo","Consolidado de la información tomada según sistema PortaFlow, desde el 01 de "+nombreMes+" hasta la fecha.");		
		
	    consulta.setPeriodoInicio(fInicio);
	    consulta.setPeriodoFin(fFin);
	    consulta.setTipoCons(tipoCons);
	    consulta.setFechaInicio(fInicio);
	    consulta.setFechaFin(fFin);		    
	    
		if (Integer.valueOf(tipocon)==3 || Integer.valueOf(tipocon)==2){
		List<PreFacturacionVO> listaConsolidado=(ArrayList)ConsultaBO.getInstance().consultaPreFacturacion(consulta);	
		List<PreFacturacionVO> listaDetalle20=ConsultaBO.getInstance().consultaDetalleOperadores(consulta,"20");
		List<PreFacturacionVO> listaDetalle21=ConsultaBO.getInstance().consultaDetalleOperadores(consulta,"21");
		List<PreFacturacionVO> listaDetalle22=ConsultaBO.getInstance().consultaDetalleOperadores(consulta,"22");			
		PreFacturacionBO.getInstance().generarArchivoExcel(consulta,listaConsolidado,listaDetalle20,listaDetalle21,listaDetalle22);	 		
		ManagerFile file=new ManagerFile();
		File filesXls =new File(BundleGeneric.getBundle("rutaSave")+"/ReporteDetalle.xls");
		file.getInstance().LevantarFile(BundleGeneric.getBundle("rutaSave")+"/ReporteDetalle.xls","ReporteDetalle.xls","application/vnd.ms-excel", response);			
		file.getInstance().EliminarFile(filesXls);
		}
		
		List listaPreFacturacion=(ArrayList)ConsultaBO.getInstance().consultaPreFacturacion(consulta);
		consultaForm.getPrefacturacionVO().setListaPreFacturacion(listaPreFacturacion);
		
		if (Integer.valueOf(tipocon)==1){forward="consulPreFacturado";}
	    //if (Integer.valueOf(tipocon)==2){forward="consulPreFacturadoXls";}
	    
		log.info("[ConsultaAction ::: consultaPreFacturacion]FIN");
		return mapping.findForward(forward);
	}	 

 
 
 	///// Validar Portados
 	public ActionForward initValidaPortados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

	  	log.info("[initValidaPortados ::: ");
		ConsultaForm consultaForm = (ConsultaForm)form;
		consultaForm.setConsulta1VO(new Consulta1VO());
		Date date = new Date();
	    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String fechaActual = formatter.format(date);
		consultaForm.getConsulta1VO().setFecha_inicio(fechaActual);
		//consultaForm.getConsulta1VO().setFecha_fin(fechaActual);
		
		consultaForm.setListaMensajes(null);
		return mapping.findForward("validaPortado");
	}	
 
 	
 	public ActionForward buscarValidaPortados(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		  log.info("[buscarValidaPortados ::: ]");
			ConsultaForm consultaForm = (ConsultaForm)form;
			List listaMensajes = new ArrayList();
			try {
				log.info(" => Action :::==>FecIni:"+consultaForm.getConsulta1VO().getFecha_inicio());
			    	
			    consultaForm.setListaMensajes(new ArrayList());	
		    	request.removeAttribute("bandera");
		    	listaMensajes = ConsultaBO.getInstance().consultaValidaPortados(consultaForm.getConsulta1VO().getFecha_inicio());	
		    	log.info(" ===>Listado:"+listaMensajes.size());
		    	
			    if(listaMensajes.size()>0) {
			    	consultaForm.setListaMensajes(listaMensajes);
				 	request.getSession().setAttribute("listaMensajes",listaMensajes);
				 	request.getSession().setAttribute("fechaInicio",consultaForm.getConsulta1VO().getFecha_inicio());	    
			    }
			    else {
			    	consultaForm.setListaMensajes(null);	
			    	request.getSession().removeAttribute("listaMensajes");			    	
			    }
			    
			   		    
			}catch(Exception e){
				log.error(e);
			}
						
			return mapping.findForward("validaPortado");
		}	
	   
	  
	  
}
