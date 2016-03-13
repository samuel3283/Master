package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.dao.ArchivoFRDAO;
import pe.com.nextel.provisioning.model.vo.ArchivoFRVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
/**
 * COMSA		: provisioning
 * @date		: 03/12/2009
 * @time		: 05:58:34 PM
 * @author		: Walter P Rodriguez Silupú.
 * @descripcion	: Clase BO para CabeceraArchivo.
*/
public class ArchivoFRBO extends BaseVO{
	 private static final Log log = LogFactory.getLog(ArchivoFRBO.class);    
	 private static ArchivoFRBO single = null;
	 
	 public ArchivoFRBO() {
	 }
	    
	 public static ArchivoFRBO getInstance() {
	    if (single == null)
	        single = new ArchivoFRBO();
	    return single;
	 }	
		
		
	 public List<ArchivoFRVO> consultarArchivoFR(FiltroVO filtro) throws DAOException{
		List<ArchivoFRVO> lista = new ArrayList<ArchivoFRVO>();	
		List<DynaBean> list = null;   
		ArchivoFRVO archivoFR = new ArchivoFRVO();
		
		log.info("[ArchivoFRBO consultarArchivoFR] ==> Caso "+ filtro.getCaso()+
				 " ==> Codigo "+ filtro.getCodigo()+
				 " ==> Nombre "+ filtro.getNombre());	
		
		try {
			list = ArchivoFRDAO.consultar(filtro);
			for (int i = 0 ; i < list.size() ; i++ ){
				DynaBean dyna = (DynaBean)list.get(i);
				archivoFR = new ArchivoFRVO();
				archivoFR.setNumeroTelefono((String)dyna.get("NUMEROTELEFONO")) ;
				archivoFR.setFechaEjecucion((String)dyna.get("FECHAEJECUCION"));
				archivoFR.setFechaComunicacion((String)dyna.get("FECHACOMUNICACION"));
				archivoFR.setReceptor( (String)dyna.get("RECEPTOR")) ; 
				archivoFR.setCedenteInicial((String)dyna.get("CEDENTEINICIAL")) ;
				archivoFR.setMotivoRetorno((String)dyna.get("MOTIVORETORNO")) ;
				lista.add(archivoFR);
			}
		}catch(Exception e){
			getError("",e.getMessage()) ;
			e.printStackTrace();
			throw new DAOException(e.getMessage(),e);
		}
		
		return lista;
     }	 
	 
	 
}
