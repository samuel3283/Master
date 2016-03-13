package pe.com.nextel.provisioning.controller.action.administracion;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import pe.com.nextel.provisioning.controller.form.administracion.ContactoForm;
import pe.com.nextel.provisioning.model.bo.ContactoBO;
import pe.com.nextel.provisioning.model.vo.ContactoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.view.ValueConstants;

 /**
  * COMSA		: Provisioning
  * @date		: 15/12/2009
  * @time		: 10:53:00 AM
  * @author		: Walter P Rodriguez Silupú.
  * @descripcion: Action para mantenimiento de contactos.
 */
public class ContactoAction extends DispatchAction {
	
	private static final Log log = LogFactory.getLog(ContactoAction.class);
	
	 /**Inicializa mantenimiento de contactos
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return		: ActionForward
	  * @date		: 15/12/2009
	  * @time		: 11:00:00 AM
	  * @author		: Walter P Rodriguez Silupú.
	  * @descripcion: Inicializa mantenimiento de contactos
	 */
	public ActionForward iniciar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info("[iniciar]INICIO");
		
		request.getSession().removeAttribute(ValueConstants.LISTA_CONTACTOS);
		ContactoForm contactoForm = (ContactoForm)form;
		contactoForm.inicializar();
		request.getSession().setAttribute(ValueConstants.LISTA_CONTACTOS,new ArrayList<ContactoVO>());
		
		log.info("[iniciar]FIN");
		return mapping.findForward(ValueConstants.FW_LISTAR);			
	}	
	
	 /**Consulta listado de contactos
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return		: ActionForward
	  * @date		: 15/12/2009
	  * @time		: 11:00:00 AM
	  * @author		: Walter P Rodriguez Silupú.
	  * @descripcion: Consulta listado de contactos.
	 */
	public ActionForward consultar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info("[consultar]INICIO");			
		ContactoForm ContactoForm = (ContactoForm)form;
		ContactoForm.setStrMensaje("");
		List<ContactoVO> listaContactos = new ArrayList<ContactoVO>();
		String tipoAccion = ContactoForm.getTipoAccion();
		String nombre = ContactoForm.getContacto().getNombre();
		String tipoContacto = ContactoForm.getContacto().getTipoContacto();
		request.getSession().removeAttribute(ValueConstants.LISTA_CONTACTOS);
		System.out.println("tipoAccion " +tipoAccion);
		System.out.println("nombre " +nombre);
		System.out.println("tipoContacto " +tipoContacto);
			
		FiltroVO filtro = new FiltroVO();
		if ((( nombre==null) ||  (nombre.equals(""))) && 
				(( tipoContacto==null) ||  (tipoContacto.equals("")))){
			filtro.setCaso(ValueConstants.CASO_UNO);
		}else if ((( nombre!=null) &&  !(nombre.equals("")))){
			filtro.setCaso(ValueConstants.CASO_DOS);
		}else{
			filtro.setCaso(ValueConstants.CASO_TRES);
		}
		filtro.setCodigo(ContactoForm.getContacto().getTipoContacto());
		filtro.setNombre(ContactoForm.getContacto().getNombre());
		try {		
			listaContactos = ContactoBO.getInstance().consultar(filtro);
			request.getSession().setAttribute(ValueConstants.LISTA_CONTACTOS,listaContactos);
		} catch (Exception e) {
			ContactoForm.setStrMensaje(e.getMessage());
			e.printStackTrace();
			log.error(e);
		}
		
		log.info("[consultar]FIN");
		return mapping.findForward(ValueConstants.FW_LISTAR);					
	}
	
	 /** Método para registrar o modificar contactos.
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return		: ActionForward
	  * @date		: 15/12/2009
	  * @time		: 11:00:00 AM
	  * @author		: Walter P Rodriguez Silupú.
	  * @descripcion: Método para insertar o modificar contactos.
	 */
	public ActionForward insertarModificar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info("[insertarModificar]INICIO");		
		ContactoForm ContactoForm = (ContactoForm)form;
		ContactoForm.setStrMensaje("");
		String tipoAccion = "";
		String resultado = "";
		String seleccionado = "";
		request.getSession().removeAttribute(ValueConstants.LISTA_CONTACTOS);
		ContactoVO contacto = new ContactoVO();
		contacto=ContactoForm.getContacto();
		tipoAccion = ContactoForm.getTipoAccion();
		seleccionado = ContactoForm.getSeleccionado();
		System.out.println("tipoAccion "+tipoAccion);
		System.out.println("seleccionado "+seleccionado);
		
		List<ContactoVO> listaContactos = new ArrayList<ContactoVO>();
		if(request.getSession().getAttribute(ValueConstants.LISTA_CONTACTOS) != null){
			listaContactos = (List<ContactoVO>)request.getSession().getAttribute(ValueConstants.LISTA_CONTACTOS);
		}
		
		try {
			
			if(tipoAccion.equals(ValueConstants.ACCION_REGISTRAR)){
				resultado=ContactoBO.getInstance().insertar(contacto);
				if (resultado.equalsIgnoreCase(ValueConstants.VALOR_OK)){
					contacto.setFechaModificacion(new Date());
					listaContactos.add(contacto);
				}else{
					ContactoForm.setStrMensaje(resultado);
				}
				
			}
			else if(tipoAccion.equals(ValueConstants.ACCION_MODIFICAR)){
				contacto.setIdContacto(Integer.parseInt(seleccionado));
				resultado=ContactoBO.getInstance().modificar(contacto);
				if (resultado.equalsIgnoreCase(ValueConstants.VALOR_OK)){
					
			        for(int i = 0; i < listaContactos.size(); i++ ) {
			            ContactoVO contactoAux = (ContactoVO)listaContactos.get(i);
						int codigoLista =contactoAux.getIdContacto();
						int codigoModificado =Integer.parseInt(seleccionado);		            
						if(codigoLista==codigoModificado){ 
							System.out.println("encontrado "+i);
							listaContactos.set( i, contactoAux );
			                 break;
			            }
			        }
			        
				}else{
					ContactoForm.setStrMensaje(resultado);
				}				
				
				
			}			
			
			ContactoForm.setSeleccionado("");						
		
		}catch (Exception e) {
			ContactoForm.setStrMensaje(e.getMessage());
			e.printStackTrace();
			log.error(e);
		}		
		
		log.info("[insertarModificar] FIN");
		return mapping.findForward(ValueConstants.FW_LISTAR);
	}	
	
	 /** Método para eliminar contactos.
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return		: ActionForward
	  * @date		: 15/12/2009
	  * @time		: 11:00:00 AM
	  * @author		: Walter P Rodriguez Silupú.
	  * @descripcion: Método para elimnar contactos.
	 */	
	public ActionForward eliminar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		log.info("[eliminar]INICIO");
		ContactoForm ContactoForm = (ContactoForm)form;
		ContactoForm.setStrMensaje("");
		String tipoAccion = "";
		
		ContactoVO contacto = new ContactoVO();
		contacto=ContactoForm.getContacto();
		tipoAccion = ContactoForm.getTipoAccion();
		System.out.println("tipoAccion "+tipoAccion);		
		List<ContactoVO> listaContactos = (List<ContactoVO>)request.getSession().getAttribute(ValueConstants.LISTA_CONTACTOS);
		String seleccionado = ContactoForm.getSeleccionado();
		contacto.setIdContacto(Integer.parseInt(seleccionado));
		
		try {
			ContactoBO.getInstance().eliminar(contacto);
			
			for(ContactoVO contactoAux : listaContactos){
				if(contactoAux.getIdContacto() == Integer.parseInt(seleccionado)){
					listaContactos.remove(contactoAux);
					break;
				}
			}	        
	        
		}catch (Exception e) {
			ContactoForm.setStrMensaje(e.getMessage());
			e.printStackTrace();
			log.error(e);
		}
		
		log.info("[eliminar] FIN");
		return mapping.findForward(ValueConstants.FW_LISTAR);
	}	
	
	 /**
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return		: ActionForward
	  * @date		: 15/12/2009
	  * @time		: 11:00:00 AM
	  * @author		: Walter P Rodriguez Silupú.
	  * @descripcion: Obtiene datos de un contacto para modificación.
	 */
	public ActionForward obtenerContacto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
		log.info("[obtenerContacto]INICIO");
		ContactoForm ContactoForm = (ContactoForm)form;
		ContactoForm.setStrMensaje("");
		
		String tipoAccion = ContactoForm.getTipoAccion();
		System.out.println("tipoAccion " +tipoAccion);
		if(tipoAccion.equals(ValueConstants.ACCION_MODIFICAR)){
			List<ContactoVO> listaContactos = new ArrayList<ContactoVO>();
			listaContactos = (List<ContactoVO>)request.getSession().getAttribute(ValueConstants.LISTA_CONTACTOS);
		    String seleccionado = ContactoForm.getSeleccionado();
		    ContactoVO contacto = null;
		        
	        for(Iterator iter = listaContactos.iterator(); iter.hasNext(); ) {
	        	contacto = (ContactoVO)iter.next();
	            int id=contacto.getIdContacto();
	            if ( id == Integer.parseInt(seleccionado) ) {   
	                break;
	            }
	        }		    
    		ContactoForm.setContacto(contacto);
    		
        }else{
        	ContactoVO contacto = new ContactoVO();
        	contacto.setNombre("");
        	contacto.setEmail("");
        	contacto.setCelular("");
        	contacto.setSms("");
        	contacto.setTipoContacto("");
        	ContactoForm.setContacto(contacto);
        	ContactoForm.setSeleccionado("");
        	
        }
		
		log.info("[obtenerContacto]FIN");
		return mapping.findForward(ValueConstants.FW_INGRESAR_EDITAR);
		
	}	

}


