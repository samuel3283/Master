package pe.com.nextel.provisioning.model.bo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.model.dao.ApiDAO;

public class ApiBO {

	private static ApiBO single = null ;
	private static final Log log = LogFactory.getLog(ApiBO.class);    
	
    public static ApiBO getInstance(){
    	if (single == null)
			single = new ApiBO();
    	return single ;
    }
    
    public String obtenerWorldNumber(String numberTelephone) {
    	String worldNumber = null;
    	try {
    		worldNumber = ApiDAO.obtenerWorldNumber(numberTelephone);
    	} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
    	return worldNumber;
    }
    
    
    public String obtenerTipoTecnologia(String worldNumber, String process,String numberSolicitud, int numberRouting) {
    	String tipoTecnologia = null;
    	try {
    		tipoTecnologia = ApiDAO.obtenerTipoTecnologia(worldNumber, process, numberSolicitud, numberRouting);
    	} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
    	return tipoTecnologia;	
    }
    

    public Map<String,String> obtenerOperadorOrigen(String worldNumber) {
    	Map<String,String> map = new HashMap<String, String>();
    	try {
    		map = ApiDAO.obtenerOperadorOrigen(worldNumber);

    	} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
    	return map;
    }
    
}
