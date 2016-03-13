package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.util.Utilitarios;
import pe.com.nextel.provisioning.framework.util.format.DateFormatter;
import pe.com.nextel.provisioning.model.dao.CabeceraArchivoDAO;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.model.vo.EstadoProcesoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
/**
 * COMSA		: provisioning
 * @date		: 03/12/2009
 * @time		: 05:58:34 PM
 * @author		: Walter P Rodriguez Silupú.
 * @descripcion	: Clase BO para CabeceraArchivo.
*/
public class CabeceraArchivoBO extends BaseVO{
	 private static final Log log = LogFactory.getLog(CabeceraArchivoBO.class);    
	 private static CabeceraArchivoBO single = null;
	 
	 public CabeceraArchivoBO() {
	 }
	    
	 public static CabeceraArchivoBO getInstance() {
	    if (single == null)
	        single = new CabeceraArchivoBO();
	    return single;
	 }	
	
	 public List<CabeceraArchivoVO> consultarCabeceraArchivo(FiltroVO filtro) throws DAOException{
			List<CabeceraArchivoVO> lista = new ArrayList<CabeceraArchivoVO>();	
			List<DynaBean> list = null;   
			CabeceraArchivoVO cabeceraArchivo = new CabeceraArchivoVO();
			
			log.info("[CabeceraArchivoBO consultar] ==> Caso "+ filtro.getCaso()+
					 " ==> Nombre "+ filtro.getNombre());	
			
			try {
				list = CabeceraArchivoDAO.consultar(filtro);
				for (int i = 0 ; i < list.size() ; i++ ){
					DynaBean dyna = (DynaBean)list.get(i);
					cabeceraArchivo = new CabeceraArchivoVO();
					cabeceraArchivo.setCodigo(((String)dyna.get("IDCABECERA"))) ;
					cabeceraArchivo.setFechaRegistro((String)dyna.get("FECHAREGISTRO")!=null?(((String)dyna.get("FECHAREGISTRO")).equals("")?null:Utilitarios.stringToDate((String)dyna.get("FECHAREGISTRO"),DateFormatter.dateDataBasee)):null);
					cabeceraArchivo.setFecha((String)dyna.get("FECHAREGISTRO"));
					cabeceraArchivo.setFechaProceso((String)dyna.get("FECHAPROCESO"));
					cabeceraArchivo.setArchivo((String)dyna.get("ARCHIVO")) ;
					cabeceraArchivo.setTipoArchivo((String)dyna.get("TIPOARCHIVO")) ;
					cabeceraArchivo.setCantidadRegistros(Integer.parseInt(((String)dyna.get("CANTIDADREGISTROS")))) ; 
					cabeceraArchivo.setCantidadAprovsionados(Integer.parseInt(((String)dyna.get("CONTADOREXITOS")))) ; 
					cabeceraArchivo.setEstadoProcesoBean(new EstadoProcesoVO());
					cabeceraArchivo.getEstadoProcesoBean().setCodigo((String)dyna.get("ESTADO"));
					lista.add(cabeceraArchivo);
				}
			}catch(Exception e){
				getError("",e.getMessage()) ;
				e.printStackTrace();
				throw new DAOException(e.getMessage(),e);
			}
			return lista;
	     }
	 
	   
	   public static String asignarCabeceraReproceso(CabeceraArchivoVO cabecera, String opcion) {
		   String valor ="";
		   try {
			   valor = CabeceraArchivoDAO.asignarCabeceraReproceso(cabecera, opcion);
			}catch(Exception e){
			    log.info(e.getMessage());
			}
			return valor;
	   }
	   
	   public static int cerrarProcesos() {
		   String valor ="";
		   int cantidad=0;
		   try {
			   valor = CabeceraArchivoDAO.cerrarProcesos();
			   cantidad = Integer.valueOf(valor);
			}catch(Exception e){
				cantidad=0;
			    log.info(e.getMessage());
			}
			return cantidad;
	   }
	   
	   
		 public CabeceraArchivoVO ObtenerCabeceraArchivo(String tipo, String fechaProceso) throws DAOException{
				
				List<DynaBean> list = new ArrayList<DynaBean>();   
				CabeceraArchivoVO cabeceraArchivo = new CabeceraArchivoVO();
				
				log.info("[CabeceraArchivoBO ObtenerCabeceraArchivo] ==> tipo: "+ tipo+" ==> fechProceso: "+ fechaProceso);	
				
				try {
					list = CabeceraArchivoDAO.ObtenerCabeceraArchivo(tipo, fechaProceso);
					if(list.size()>0) {
						DynaBean dyna = (DynaBean)list.get(0);
						cabeceraArchivo.setIdcabecera(Integer.valueOf((String)dyna.get("IDCABECERA")));
						cabeceraArchivo.setFecha((String)dyna.get("FECHAREGISTRO"));
						cabeceraArchivo.setFechaProceso((String)dyna.get("FECHAPROCESO"));
						cabeceraArchivo.setArchivo((String)dyna.get("ARCHIVO")) ;
						cabeceraArchivo.setTipoArchivo((String)dyna.get("TIPOARCHIVO")) ;
						cabeceraArchivo.setCantidadRegistros( Integer.parseInt(((String)dyna.get("CANTIDADREGISTROS")))) ; 
						cabeceraArchivo.setEstadoProcesoBean(new EstadoProcesoVO());
						cabeceraArchivo.setEstado((String)dyna.get("ESTADO"));
					    
					}
				}catch(Exception e){
					cabeceraArchivo = new CabeceraArchivoVO();
					log.info(e.getMessage());
					throw new DAOException(e.getMessage(),e);
				}
				return cabeceraArchivo;
		     }
		 
}
