package pe.com.nextel.provisioning.model.bo;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.model.dao.ConsultaDAO;
import pe.com.nextel.provisioning.model.dao.ConsultasDAO;
import pe.com.nextel.provisioning.model.vo.JobReporteVO;
import pe.com.nextel.provisioning.model.vo.PreFacturacionVO;
import pe.com.nextel.provisioning.model.vo.Producto;

public class ConsultaBO {
	
    private static final Log log = LogFactory.getLog(ConsultaBO.class);    
	private static ConsultaBO single = null;
	public ConsultaBO() {
	}
	    
	public static ConsultaBO getInstance() {
	    if (single == null)
	        single = new ConsultaBO();
	    return single;
	}	
	
	public List consulta1(String fechaInicio, String fechaFin, String cadena) throws Exception{
		 return ConsultasDAO.consulta1(fechaInicio , fechaFin, cadena) ;
	}
	
	public List consulta2(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.consulta2(fechaInicio , fechaFin) ;
	}
	
	public List consulta3(String fecha) throws Exception{
		 return ConsultasDAO.consulta3(fecha) ;
	}
	
	public List consulta4(String fecha) throws Exception{
		 return ConsultasDAO.consulta4(fecha) ;
	}
	public Map rechazosUnicos(String fechaI, String fechaF) throws Exception{
		 return ConsultasDAO.rechazosUnicos(fechaI, fechaF) ;
	}	
	public List rechazo(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.rechazo(fechaInicio , fechaFin) ;
	}
	
	public Map portadosMesAnterior(String fechaI, String fechaF) throws Exception{
		 return ConsultasDAO.portadosMesAnterior(fechaI, fechaF) ;
	}
	
	public List portados(String fechaI, String fechaF) throws Exception{
		 return ConsultasDAO.portados(fechaI, fechaF) ;
	}
	
	public List numeroSolicitados(String fechaI, String fechaF) throws Exception{
		 return ConsultasDAO.numeroSolicitados(fechaI, fechaF) ;
	}
		
	public List consultaMensajes() throws Exception{
		 return ConsultasDAO.consultaMensajes() ;
	}
	
	
	public List consultaFacturacion(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.consultaFacturacion(fechaInicio , fechaFin) ;
	}
	public List consultaOCC(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.consultaOCC(fechaInicio , fechaFin) ;
	}	
	public List consultaRechazo(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.consultaRechazo(fechaInicio , fechaFin) ;
	}	
	public List consultaPortados(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.consultaPortados(fechaInicio , fechaFin) ;
	}	
	
	public List consultaMensajesPep(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.consultaMensajesPep(fechaInicio , fechaFin) ;
	}
	public List consultaMensajesOcc(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.consultaMensajesOcc(fechaInicio , fechaFin) ;
	}

	public List consultaMensajesRec(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.consultaMensajesRec(fechaInicio , fechaFin) ;
	}
	
	public List consultaNumerosPortados(String fechaInicio, String fechaFin, String receptor) throws Exception{
    	log.info(" ===>BO: consultaNumerosPortados");
		return ConsultasDAO.consultaNumerosPortados(fechaInicio , fechaFin, receptor) ;
	}
	
	public List consultaTipoProceso() throws Exception{
		 return ConsultasDAO.consultaTipoProceso() ; 
	}
	
	public String insertProcesoJobReporte(JobReporteVO consulta) throws Exception{		
		return ConsultasDAO.insertProcesoJobReporte(consulta);
	}
	 
	public List consultaPreFacturacion(PreFacturacionVO consulta) throws Exception{
		return ConsultasDAO.consultaPreFacturacion(consulta);
	}
	public  List<PreFacturacionVO> consultaDetalleOperadores(PreFacturacionVO consulta,String tipo) throws Exception{
		return ConsultasDAO.consultaDetalleOperadores(consulta,tipo);
	} 
	
	public List consultaValidaPortados(String fechaInicio) throws Exception{
    	log.info(" ===>BO: consultaValidaPortados");
		return ConsultasDAO.consultaValidaPortados(fechaInicio) ;
	}
	
	public List ConsultaEstadisticaPortabilidades(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.ConsultaEstadisticaPortabilidades(fechaInicio , fechaFin) ;
	}
	
	public List ConsultaEstadisticaRetornos(String fechaInicio, String fechaFin) throws Exception{
		 return ConsultasDAO.ConsultaEstadisticaRetornos(fechaInicio , fechaFin) ;
	}
	
	
	
	
	public List buscarProductos(Producto producto) throws Exception{
		 return ConsultaDAO.buscarProductos(producto) ;
	}

	public static Producto obtenerProducto(String codigo) throws Exception{
		 return ConsultaDAO.obtenerProducto(codigo);
	}
	


	
	
}
