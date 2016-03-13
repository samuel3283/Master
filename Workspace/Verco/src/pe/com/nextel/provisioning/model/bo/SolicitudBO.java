package pe.com.nextel.provisioning.model.bo;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.model.dao.SolicitudDAO;

public class SolicitudBO {

    private static final Log log = LogFactory.getLog(SolicitudBO.class);    
	private static SolicitudBO single = null;
	public SolicitudBO() {
	}
	    
	public static SolicitudBO getInstance() {
	    if (single == null)
	        single = new SolicitudBO();
	    return single;
	}	
	public List consultarSolicitudes(String tipo, String idProceso, String numeroSecuencial) throws Exception{
		 return SolicitudDAO.consultarSolicitudes(tipo,idProceso, numeroSecuencial) ;
	}
	public String obtenerProcessInstance(String tipo, String idProceso, String numeroSecuencial) throws Exception{
		 return SolicitudDAO.obtenerProcessInstance(tipo,idProceso, numeroSecuencial) ;
	}
	public List consultarAlarmas(String processInstance) throws Exception{
		 return SolicitudDAO.consultarAlarmas(processInstance);
	}
	
	public List consultarEstados() throws Exception{
		 return SolicitudDAO.consultarEstados();
	}
	
	public List consultarListaSolicitudes(String tipo, String telefono, String track_id) throws Exception{
		 return SolicitudDAO.consultarListaSolicitudes(tipo, telefono, track_id) ;
	}
}
