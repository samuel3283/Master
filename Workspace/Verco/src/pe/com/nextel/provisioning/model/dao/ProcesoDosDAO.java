package pe.com.nextel.provisioning.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.util.Utilitarios;
import pe.com.nextel.provisioning.framework.util.format.DateFormatter;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.model.vo.EstadoProcesoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.model.vo.LogVO;
/**Clase DAO para el proceso uno: Obtener archivos fp y fr
 * COMSA		: Aprovisionamiento Nextel
 * @date		: 30/11/2009
 * @time		: 11:03:15 AM
 * @author		: Walter P Rodriguez Silupú.
 * @descripcion	: Clase DAO para el proceso uno: Obtener archivos fp y fr
*/
public class ProcesoDosDAO {
	 private static final Log log = LogFactory.getLog(ProcesoDosDAO.class);    
		
	 public ProcesoDosDAO() 
	 {
	 }  

	 public static List<CabeceraArchivoVO> consultarCabeceraArchivo(FiltroVO filtro) throws DAOException{
			List<CabeceraArchivoVO> lista = new ArrayList<CabeceraArchivoVO>();	
			List<DynaBean> list = null;   
			CabeceraArchivoVO cabeceraArchivo = new CabeceraArchivoVO();
			
			log.info("[ProcesoDosDAO consultarCabeceraArchivo] ==> Caso "+ filtro.getCaso() +
			" ==> Nombre "+ filtro.getNombre());	
			
			try {
				list = CabeceraArchivoDAO.consultar(filtro);
				for (int i = 0 ; i < list.size() ; i++ ){
					DynaBean dyna = (DynaBean)list.get(i);
					cabeceraArchivo = new CabeceraArchivoVO();
					cabeceraArchivo.setCodigo(((String)dyna.get("IDCABECERA"))) ;
					cabeceraArchivo.setFechaRegistro((String)dyna.get("FECHAREGISTRO")!=null?(((String)dyna.get("FECHAREGISTRO")).equals("")?null:Utilitarios.stringToDate((String)dyna.get("FECHAREGISTRO"),DateFormatter.dateDataBasee)):null); 
					cabeceraArchivo.setFecha((String)dyna.get("FECHAREGISTRO"));
					cabeceraArchivo.setArchivo((String)dyna.get("ARCHIVO")) ;
					cabeceraArchivo.setCantidadRegistros( Integer.parseInt(((String)dyna.get("CANTIDADREGISTROS")))) ; 
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
	 

	protected static void getError(String cod, String desc ) throws DAOException{
		LogVO beanE = new LogVO() ;
        beanE.setDescripcion(desc) ;
        beanE.setIdError(cod) ;
        LogDAO.insertarLog(beanE) ;
	}
	 
}
