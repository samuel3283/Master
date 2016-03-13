package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.dao.EstadoSoapDAO;
import pe.com.nextel.provisioning.model.vo.EstadoSoapVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class EstadoSoapBO  extends BaseVO {

	private static final Log log = LogFactory.getLog(EstadoSoapBO.class);    
	private static EstadoSoapBO single = null ;
	private static String desClassMethod;
	public static EstadoSoapBO getInstance(){
    	if (single == null)
			single = new EstadoSoapBO();
    	return single ;
    }
   

	/**
	 * Carga registros de la tabla BandejaSalida (not in BLOQUEADO = 0) 
	 * @return 
	 * @throws DAOException
	 */
	public List cargarLista() throws DAOException{
		List lista = new ArrayList(); 
		desClassMethod="EstadoSoapBO.cargarLista() ::: ";
		try {
			Map map = EstadoSoapDAO.cargarLista();
    		String sError = (String)map.get("sError");
			if ( sError.equals(ValueConstants.CODIGO_ERROR) ){
				List<DynaBean> list = (List<DynaBean>)map.get("oResult");
			    if (list != null ){
			    	for (int ii = 0 ; ii < list.size() ; ii++ ){
						DynaBean dyna = (DynaBean)list.get(ii);
						EstadoSoapVO bean = new EstadoSoapVO();
						bean.setCodigo((String)dyna.get("CODIGO")) ; 
						bean.setDescripcion((String)dyna.get("DESCRIPCION")) ;
						lista.add(bean) ;
					}
			    }else getError("",desClassMethod+" List null ");
		    }else {
				log.error(desClassMethod+sError);
				getError("",desClassMethod+sError);
			}
	    }catch(Exception e){
			getError("",desClassMethod+e.getMessage()) ;
			e.printStackTrace();
			throw new DAOException(e.getMessage(),e);
		}
		return lista;
	}
		
}
