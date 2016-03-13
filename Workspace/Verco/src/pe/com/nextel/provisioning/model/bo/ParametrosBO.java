package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.util.Utilitarios;
import pe.com.nextel.provisioning.framework.util.format.DateFormatter;
import pe.com.nextel.provisioning.model.dao.ParametrosDAO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.model.vo.ParametrosVO;
import pe.com.nextel.provisioning.view.ValueConstants;
/**
 * COMSA		: provisioning
 * @date		: 03/12/2009
 * @time		: 05:58:34 PM
 * @author		: Walter P Rodriguez Silupú.
 * @descripcion	: Clase BO para mantenimiento de parametros.
*/
public class ParametrosBO extends BaseVO{
	 private static final Log log = LogFactory.getLog(ParametrosBO.class);    
	 private static ParametrosBO single = null;
	 
	 public ParametrosBO() {
	 }
	    
	 public static ParametrosBO getInstance() {
	    if (single == null)
	        single = new ParametrosBO();
	    return single;
	 }	
	
	public String consultarParametro(String nombreCorto) throws Exception
	 {    
		String valor="";
		
        try {
        	valor = ParametrosDAO.consultarParametro(nombreCorto) ;

        }catch(Exception e)
		{
			getError("",e.getMessage()) ;
			e.printStackTrace();
			throw new DAOException(e.getMessage(),e);
		}
		
		return valor;
	 }
	 
	public String registrarModificarParametros(ParametrosVO parametro) throws Exception
	 {    
		String valor="";
		
		log.info("[ParametrosBO registrarModificarParametros] ==> Codigo "+ parametro.getCodigo()+
				 " ==>NombreCorto "+ parametro.getNombreCorto()+			
				 " ==>Nombre "+ parametro.getNombre()+
				 " ==>Valor "+ parametro.getValor());
		
       try {
       	valor = ParametrosDAO.insertar(parametro);

       }catch(Exception e)
		{
			getError("",e.getMessage()) ;
			e.printStackTrace();
			throw new DAOException(e.getMessage(),e);
		}
		
		return valor;
	 }	
	
	 public List<ParametrosVO> consultarListaParametros(FiltroVO filtro) throws DAOException{
		List<ParametrosVO> listaParametros = new ArrayList<ParametrosVO>();	
		List<DynaBean> lista = null;   
		ParametrosVO parametros = new ParametrosVO();
		
		log.info("[ParametrosBO consultarListaParametros] ==> Caso "+ filtro.getCaso()+
				 " ==>Codigo "+ filtro.getCodigo()+
				 " ==>Nombre "+ filtro.getNombre());	
		
		try {
			lista = ParametrosDAO.consultar(filtro);
			for (int i = 0 ; i < lista.size() ; i++ ){
				DynaBean dyna = (DynaBean)lista.get(i);
				parametros = new ParametrosVO();
				parametros.setCodigo( (String)dyna.get("CODIGO")) ; 
				parametros.setNombreCorto((String)dyna.get("NOMBRECORTO")) ;
				parametros.setNombre((String)dyna.get("NOMBRE")) ; 
				parametros.setValor((String)dyna.get("VALOR")) ;
				parametros.setFechaModificacion((String)dyna.get("FECHAMODIFICACION")!=null?(((String)dyna.get("FECHAMODIFICACION")).equals("")?null:Utilitarios.stringToDate((String)dyna.get("FECHAMODIFICACION"),DateFormatter.dateDataBasee)):null);
				listaParametros.add(parametros);
			}
		}catch(Exception e){
			getError("",e.getMessage()) ;
			e.printStackTrace();
			throw new DAOException(e.getMessage(),e);
		}
		
		return listaParametros;
     }
	 
	 public Map<String, String> ObtenerParametros() throws Exception{
			List<ParametrosVO> listaParametros = new ArrayList<ParametrosVO>();
			Map<String,String> mapParametros = new HashMap<String, String>();
			FiltroVO filtro = new FiltroVO();
			filtro.setCaso(ValueConstants.CASO_TRES);
			try {		
				
				listaParametros = ParametrosBO.getInstance().consultarListaParametros(filtro);
				for(int k=0;k<listaParametros.size();k++){
					mapParametros.put(listaParametros.get(k).getNombreCorto().trim(),listaParametros.get(k).getValor().trim());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
			}		
			return mapParametros;		 
		 }
	 
	 
}
