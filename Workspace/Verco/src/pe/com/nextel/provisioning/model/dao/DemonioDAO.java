package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.conexion.ParameterCollection;
import pe.com.nextel.provisioning.framework.conexion.QueryUtil;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.view.ValueConstants;

public class DemonioDAO {
	 private static final Log log = LogFactory.getLog(DemonioDAO.class);    
		
	 public DemonioDAO() 
	 {
	 } 
	 
	 public static String obtenerProcesoPendiente() {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			log.info("[DemonioDAO obtenerProcesoPendiente] ==> ");
		 
			ParameterCollection inputCollection = new ParameterCollection();
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_PROCESO_PENDIENTE"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	
	        } catch (DAOException de) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error(de);
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error(e);            
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
     
     return codigo;
 }
	 
	 
	 public static String verificarProcesoPendiente() {
		 
			Object[] objectL = new Object[1];
			String codigo = "0";
			Connection con = null;
			
			
			
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(ValueConstants.VALOR_UNO,OracleTypes.VARCHAR);
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.NUMBER);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_VERIFICAR_PROCESO_PENDIENTE"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	            log.info("[DemonioDAO verificarProcesoPendiente] ==> "+codigo);
	        } catch (DAOException de) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error(de);
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error(e);            
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
  
  return codigo;
}
	 
	 public static void asignarProcesoPendiente(String fecha, String opcion) {
		 
			Connection con = null;
			log.info(" asignarProcesoPendiente   Fecha: "+ fecha+" ==> Opcion: "+opcion);
		    
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(fecha,OracleTypes.VARCHAR);
			inputCollection.addInput(opcion,OracleTypes.VARCHAR);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	QueryUtil.execute(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_ASIGNAR_PROCESO_PENDIENTE"), inputCollection);
	        } catch (Exception e) { 
	        	log.error(e);
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
 }
	 
	 public static String validarProcesoPendiente(String fechaProceso, String tipoArchivo) {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			log.info("[DemonioDAO validarProcesoPendiente] ==> fechaProceso: "+fechaProceso+" ==> tipoArchivo: "+tipoArchivo);
		 
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(fechaProceso,OracleTypes.VARCHAR);	
			inputCollection.addInput(tipoArchivo,OracleTypes.VARCHAR);	
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.NUMBER);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_VALIDAR_PROCESO_PENDIENTE"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	
	        } catch (DAOException de) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error(de);
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error(e);            
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

return codigo;
}
}
