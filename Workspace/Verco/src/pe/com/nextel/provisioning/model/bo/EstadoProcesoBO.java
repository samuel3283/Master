package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.dao.EstadoProcesoDAO;
import pe.com.nextel.provisioning.model.vo.EstadoProcesoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
/**
 * COMSA		: provisioning
 * @date		: 03/12/2009
 * @time		: 05:58:34 PM
 * @author		: Walter P Rodriguez Silupú.
 * @descripcion	: Clase BO para mantenimiento de parametros.
*/
public class EstadoProcesoBO extends BaseVO{
	 private static final Log log = LogFactory.getLog(EstadoProcesoBO.class);    
	 private static EstadoProcesoBO single = null;
	 
	 public EstadoProcesoBO() {
	 }
	    
	 public static EstadoProcesoBO getInstance() {
	    if (single == null)
	        single = new EstadoProcesoBO();
	    return single;
	 }	
	
	 public List<EstadoProcesoVO> consultarListaProcesos(FiltroVO filtro) throws DAOException{
		
		List<EstadoProcesoVO> listaProcesos = new ArrayList<EstadoProcesoVO>();	
		List<DynaBean> lista = null;   
		EstadoProcesoVO estadoProceso = new EstadoProcesoVO();
		
		log.info("[EstadoProcesoBO consultarListaProcesos] ==> Caso "+ filtro.getCaso()+
				 " ==> Nombre "+ filtro.getNombre());	
		
		try {
			lista = EstadoProcesoDAO.consultarListaProcesos(filtro);
			for (int i = 0 ; i < lista.size() ; i++ ){
				DynaBean dyna = (DynaBean)lista.get(i);
				estadoProceso = new EstadoProcesoVO();
				estadoProceso.setCodigo((String)dyna.get("CODIGO")) ; 
				estadoProceso.setProceso((String)dyna.get("PROCESO")) ;
				estadoProceso.setCodigo(estadoProceso.getCodigo().trim());
				listaProcesos.add(estadoProceso);
			}
		}catch(Exception e){
			getError("",e.getMessage()) ;
			e.printStackTrace();
			throw new DAOException(e.getMessage(),e);
		}
		
		return listaProcesos;
     }
 
}
