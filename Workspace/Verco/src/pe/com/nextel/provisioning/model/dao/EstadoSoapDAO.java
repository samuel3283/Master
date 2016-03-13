package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.conexion.ParameterCollection;
import pe.com.nextel.provisioning.framework.conexion.QueryUtil;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.view.ValueConstants;

public class EstadoSoapDAO {

	private static final Log log = LogFactory.getLog(BandejaSalidaDAO.class);    
	
	/**
	 * Carga la lista de la Tabla Estado SOAP
	 */
	@SuppressWarnings("unchecked")
	public static Map cargarLista() throws Exception {
		 List<DynaBean> lista = null;
		 Connection con = JdbcUtilitario.getInstance().getConnection();
		 ParameterCollection inputCollection = new ParameterCollection();
		 ParameterCollection outputCollection = new ParameterCollection();
         Map map =  new HashMap(); 		 
         String sError="000" ;
         try
         {
        	 outputCollection.addOutput(OracleTypes.VARCHAR);
        	 outputCollection.addOutput(OracleTypes.CURSOR);
        	 con= JdbcUtilitario.getInstance().getConnection();
             Object[] object = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_CNC.concat(".SP_OBTENER_ESTADO_SOAP"),inputCollection,outputCollection);	     
   			 sError = String .valueOf(object[0]) ;
			 lista = (List<DynaBean>)object[1];
             
         }catch(Exception e)
         {
             e.printStackTrace();
             log.error(e);
             throw new DAOException(e.getMessage(),e);
         }
         finally
         {
             try
             {
                 con.close();
             }catch(SQLException e)
             {
                 e.printStackTrace();
                 log.error(e);
             }
         }
         map.put("sError", sError);
 		 map.put("oResult", lista);
         
         return map;
	}
	
	
}
