package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.conexion.ConexionAPI;
import pe.com.nextel.provisioning.framework.conexion.ParameterCollection;
import pe.com.nextel.provisioning.framework.conexion.QueryUtil;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.util.Utilitarios;
import pe.com.nextel.provisioning.view.ValueConstants;

public class ApiDAO {
	 private static final Log log = LogFactory.getLog(ApiDAO.class);    
		
	 public ApiDAO() 
	 {
	 } 
	 
	 /**
	  * COMSA		: Provisioning
	  * @date		: 01/12/2009 05:58:34 PM
	  * @author		: Samuel Navarro Rivera
	  * @descripcion: Metodo que obtiene el World Number.
	  * @parametros : 1
	  * 			  IN String numberTelephone:	Número de Teléfono	
	  * @resultado	: World Number	
	 */
	 public static String obtenerWorldNumber(String numberTelephone) throws Exception{       
		 	log.info("[obtenerWorldNumber] init ==> "+numberTelephone);
			String worldNumber = "";
			Connection con = null;
		
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(numberTelephone,OracleTypes.VARCHAR);

			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
			String paqueteApi=Utilitarios.getMessage(ValueConstants.FRAMEWORK_PROPERTIES,"PAQUETE_API");
			
	        try {
	        	con = ConexionAPI.getInstance().getConnection();
	        	
	        	Statement stmt = con.createStatement();
	        	ResultSet rset = stmt.executeQuery("SELECT "+paqueteApi+"FXI_WORLD_NUMBER('"+numberTelephone+"') FROM dual");
	        	    while (rset.next()) {
	        	    	worldNumber = rset.getString(1);   
	        	    }
	            
	        } catch (Exception e) { 
	        	log.error(e);
	            throw new DAOException(e.getMessage(), e);
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
	        
	     return worldNumber;
         
     }
	 
	 
	 /**
	  * COMSA		: Provisioning
	  * @date		: 01/12/2009 05:58:34 PM
	  * @author		: Samuel Navarro Rivera
	  * @descripcion: Metodo que obtiene el Tipo de Tecnologia.
	  * @parametros : 4
	  * 			  IN String worldNumber: Número de Teléfono transformado a World Number
	  * 			  IN String process	: Si es Proceso de Portabilidad: 	  P
	  * 								  Si es Proceso de Retorno por Fraude RF  
	  * 								  Si es Proceso de Retorno por Terminación de contrato  RT  
	  * 			  IN String numberSolicitud:  Se enviara Número de Solicitud en caso process sea P, sino se enviara null	
	  * 			  IN String numberRouting	:  En caso que process sea:
                  							   P: Se enviará el campo Prestador receptor(RN).
                  							   RF/RT: Se enviará el campo Prestador Cedente Inicial/Ant
	  * @resultado	: Tipo de Tecnologia 20 ó 27	
	 */	  
	 public static String obtenerTipoTecnologia(String worldNumber, String process,String numberSolicitud, int numberRouting) throws Exception{       
		 	log.info("[obtenerTipoTecnologia] init :WorldNumber:: "+worldNumber+"  ==> :Proceso:: ="+process+" ==> :Nro Sol:: ="+numberSolicitud+" ==> :NumberRouting:: ="+numberRouting);
			String tipoTecnologia = "";
			Connection con = null;
			
			String paqueteApi=Utilitarios.getMessage(ValueConstants.FRAMEWORK_PROPERTIES,"PAQUETE_API");
			
	        try {
	        	con = ConexionAPI.getInstance().getConnection();
	        	
	        	Statement stmt = con.createStatement();
	        	ResultSet rset = stmt.executeQuery("SELECT "+paqueteApi+"FXI_ORIGIN_TECNOLOGY('"+worldNumber+"','"+process+"','"+numberSolicitud+"',"+numberRouting+") FROM dual");
	        	    while (rset.next()) {
	        	    	tipoTecnologia = rset.getString(1);   
	        	    }
	            
	        } catch (Exception e) { 
	        	log.error(e);
	            throw new DAOException(e.getMessage(), e);
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
	        
	     return tipoTecnologia;
      
  }
	 
	 

	 /**
	  * COMSA		: Provisioning
	  * @date		: 01/12/2009 05:58:34 PM
	  * @author		: Samuel Navarro Rivera
	  * @descripcion: Metodo que obtiene el código de operador Origen.
	  * @parametros : 4 
	  * 			  IN String worldNumber: Número de Teléfono transformado a World Number	
	  * 			  OUT String codigoReceptor
	  * 			  OUT String estado
	  * 			  OUT String mensaje
	  * @resultado	: Código de Operador Origen
	 */

	 public static Map<String, String> obtenerOperadorOrigen(String worldNumber) throws Exception{       
		 	log.info("[obtenerOperadorOrigen] init ==> "+worldNumber);
			String operadorOrigen = "";
			int estado = 0;
			String mensaje = "";
			Connection con = null;
			Object[] objectL = new Object[3];
			Map<String,String> map = new HashMap<String, String>();
	 
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(worldNumber,OracleTypes.VARCHAR);

			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			outputCollection.addOutput(OracleTypes.VARCHAR);
			outputCollection.addOutput(OracleTypes.VARCHAR);
					
			String paqueteApi=Utilitarios.getMessage(ValueConstants.FRAMEWORK_PROPERTIES,"PAQUETE_API");
        	    
	    	 try {
	    	  		con = ConexionAPI.getInstance().getConnection();
	    	        objectL = QueryUtil.executeProcedure(con, paqueteApi.concat("SPI_ORIGIN_OPERATOR"), inputCollection, outputCollection);
	    	        if (objectL != null) {
	    	            operadorOrigen = String.valueOf(objectL[0]);
	    	            estado = Integer.parseInt(String.valueOf(objectL[1]));
	    	            mensaje = String.valueOf(objectL[2]);
	    	            map.put(ValueConstants.STATUS,String.valueOf(estado));
	    	            if (estado==0){
	    	            	map.put(ValueConstants.MESSAGE,operadorOrigen);
	    	            }else{
	    	            	map.put(ValueConstants.MESSAGE,mensaje);
	    	            }
	    	            	
	    	        }
	        } catch (Exception e) { 
	        	log.error(e);
	            throw new DAOException(e.getMessage(), e);
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
	        
	     return map;         
     }
	 
	 
	 
	 
}
