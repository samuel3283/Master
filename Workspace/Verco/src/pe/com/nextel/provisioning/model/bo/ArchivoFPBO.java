package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.dao.ArchivoFPDAO;
import pe.com.nextel.provisioning.model.vo.ArchivoFPVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
/**
 * COMSA		: provisioning
 * @date		: 03/12/2009
 * @time		: 05:58:34 PM
 * @author		: Walter P Rodriguez Silupú.
 * @descripcion	: Clase BO para CabeceraArchivo.
*/
public class ArchivoFPBO extends BaseVO{
	 private static final Log log = LogFactory.getLog(ArchivoFPBO.class);    
	 private static ArchivoFPBO single = null;
	 
	 public ArchivoFPBO() {
	 }
	    
	 public static ArchivoFPBO getInstance() {
	    if (single == null)
	        single = new ArchivoFPBO();
	    return single;
	 }	
		
	 public List<ArchivoFPVO> consultarArchivoFP(FiltroVO filtro) throws DAOException{

		List<ArchivoFPVO> lista = new ArrayList<ArchivoFPVO>();	
		List<DynaBean> list = null;   
		ArchivoFPVO archivoFP = new ArchivoFPVO();
		
		log.info("[ArchivoFPBO consultarArchivoFP] ==> Caso "+ filtro.getCaso()+
 				 " ==> Codigo "+ filtro.getCodigo()+
		         " ==> Nombre "+ filtro.getNombre());	
		
		try {
			list = ArchivoFPDAO.consultar(filtro);
			for (int i = 0 ; i < list.size() ; i++ ){
				DynaBean dyna = (DynaBean)list.get(i);
				archivoFP = new ArchivoFPVO();
				archivoFP.setNumeroSolicitud( (String)dyna.get("NUMEROSOLICITUD")) ; 
				archivoFP.setNumeroTelefono((String)dyna.get("NUMEROTELEFONO")) ;
				archivoFP.setFechaEjecucion((String)dyna.get("FECHAEJECUCION"));
				archivoFP.setReceptor( (String)dyna.get("RECEPTOR")) ; 
				archivoFP.setCedente((String)dyna.get("CEDENTE")) ;
				lista.add(archivoFP);
			}
		}catch(Exception e){
			getError("",e.getMessage()) ;
			e.printStackTrace();
			throw new DAOException(e.getMessage(),e);
		}
		
		return lista;
     }	 
	 
}
