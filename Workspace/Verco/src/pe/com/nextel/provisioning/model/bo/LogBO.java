package pe.com.nextel.provisioning.model.bo;

/**
 * <p>Title: Log BO</p>
 * <p>Description: Clase encargada al mantenimiento de la Tabla LOG.(SERVICES)</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : LogDAO</p>
 * <p>Fecha       : 24 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  CARLOS NISHIMURA (COMSA)
 * @version 1.0
 */

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.dao.LogDAO;
import pe.com.nextel.provisioning.model.vo.LogVO;

public class LogBO {
	
	private static LogBO single = null ;
	private static final Log log = LogFactory.getLog(LogBO.class);    
	
    public static LogBO getInstance(){
    	if (single == null)
			single = new LogBO();
    	return single ;
    }
    /**
     * Servicio encargado de insertar registro a la Tabla LOG
     * @param bean
     * @return
     * @throws DAOException
     */
    public String insertarLog(LogVO bean) throws DAOException{
    	return LogDAO.insertarLog(bean) ;
    }
    
    /**
     * Servicio encargado de actualizar registro a la Tabla LOG
     * @param bean
     * @return
     * @throws DAOException
     */
    public String actualizarLog(LogVO bean) throws DAOException{
    	return LogDAO.actualizarLog(bean) ;
    }
    
    /**
     * Servicio encargado de eliminar registro a la Tabla LOG
     * @param bean
     * @return
     * @throws DAOException
     */
    public String eliminarLog(LogVO bean) throws DAOException{
    	return LogDAO.eliminarLog(bean) ;
    }
    
    /**
     * Servicio encargado de listar registros de la tabla LOG
     * @return
     * @throws DAOException
     */
    public List cargarList() throws DAOException {
    	List lista = new ArrayList() ;
    	 
    	List<DynaBean> list = null;   
		LogVO bean = new LogVO();
		try {
			list = LogDAO.cargarLista();
			for (int ii = 0 ; ii < list.size() ; ii++ ){
				DynaBean dyna = (DynaBean)list.get(ii);
				bean = new LogVO();
				bean.setIdLog(new BigInteger((String)dyna.get("IDLOG"))) ;
				bean.setDescripcion((String)dyna.get("DESCRIPCION")) ;
				bean.setIdError((String)dyna.get("IDERROR")) ;
				lista.add(bean) ;
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new DAOException(e.getMessage(),e);
		}
    	return lista;
    }

		
}
