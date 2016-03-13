package pe.com.nextel.provisioning.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.model.vo.OpcionEtiquetaVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class Listener implements ServletContextListener {
	
	private static final Log log = LogFactory.getLog(Listener.class);

	public void contextDestroyed(ServletContextEvent ctx) {
//		ctx = null;
	}
		
	public void contextInitialized(ServletContextEvent ctx) {
		log.info("[Listener]:[contextInitialized][inicio]");
		String contexto = ctx.getServletContext().getServletContextName();
		ctx.getServletContext().setAttribute("ctx", contexto);	
		
		List<OpcionEtiquetaVO> listaTipoContacto = new ArrayList<OpcionEtiquetaVO>();
		listaTipoContacto.add(new OpcionEtiquetaVO());
		listaTipoContacto.get(0).setCodigo("0");
		listaTipoContacto.get(0).setNombre("Todos");
		listaTipoContacto.add(new OpcionEtiquetaVO());
		listaTipoContacto.get(1).setCodigo("1");
		listaTipoContacto.get(1).setNombre("Correo");
		listaTipoContacto.add(new OpcionEtiquetaVO());
		listaTipoContacto.get(2).setCodigo("2");
		listaTipoContacto.get(2).setNombre("SMS");		
		System.out.println("listaTipoContacto "+listaTipoContacto.get(2).getCodigo());
		ctx.getServletContext().setAttribute(ValueConstants.LISTA_TIPO_CONTACTO  ,listaTipoContacto);
		
		log.info("[Listener]:[contextInitialized][fin]");	
	}

}


