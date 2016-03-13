package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.conexion.ConexionAPI;
import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitarioPortanode;
import pe.com.nextel.provisioning.framework.conexion.ParameterCollection;
import pe.com.nextel.provisioning.framework.conexion.QueryUtil;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.util.Utilitarios;
import pe.com.nextel.provisioning.framework.util.format.DateFormatter;
import pe.com.nextel.provisioning.model.vo.ArchivoPortadoVO;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.model.vo.EstadoProcesoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.model.vo.LogVO;
import pe.com.nextel.provisioning.view.ValueConstants;
/**Clase DAO para el proceso uno: Obtener archivos fp y fr
 * COMSA		: Aprovisionamiento Nextel
 * @date		: 30/11/2009
 * @time		: 11:03:15 AM
 * @author		: Walter P Rodriguez Silupú.
 * @descripcion	: Clase DAO para el proceso uno: Obtener archivos fp y fr
*/
public class ProcesoDAO {
	 private static final Log log = LogFactory.getLog(ProcesoDAO.class);    
		
	 public ProcesoDAO() 
	 {
	 }  

	 public static void numeracionPortadaPortanode(Connection conexionPortanode,Connection conexionLocal, String fecha) throws Exception{       
		 	log.info("[numeracionPortadaPortanode] ");
			String numero = "";
			String proceso = "";
			String fechaejecucion = "";
			//Connection con = null;
			ArchivoPortadoVO archivoPortadoVO;
			int i=1;
			try {
	        	//con = JdbcUtilitarioPortanode.getInstance().getConnection();
	        	
	        	Statement stmt = conexionPortanode.createStatement();
	        	ResultSet rset = stmt.executeQuery("SELECT numero, ultimo_proceso, to_char(inicio_ventana,'yyyymmdd')  from numeracion_portada where estado ='01' ");
	        	    while (rset.next()) {
	        	    	numero = rset.getString(1);  
	        	    	proceso = rset.getString(2);  
	        	    	fechaejecucion = rset.getString(3);  
	        	    	log.info("[NP] ==>NUMERO: "+i+" ==> "+numero);
	        	    	archivoPortadoVO = new ArchivoPortadoVO();
						i++;
						archivoPortadoVO.setIdentificadorProceso(proceso);
						archivoPortadoVO.setNumero(numero);
						archivoPortadoVO.setInicioVentana(fechaejecucion);
						ProcesoDAO.insertaArchivoNP(conexionLocal, archivoPortadoVO,fecha);
	        	    }
	            
	        } catch (Exception e) { 
	        	log.error(e);
	            throw new DAOException(e.getMessage(), e);
	        }
     
	        
	       
	 }
	 
	 
	 
	  public static void limpiarTabla(Connection con) throws Exception{       
			 	log.info("[limpiarTabla] ");
				Statement stmt = null;
				
	  	try {
		        	stmt = con.createStatement();
		        	stmt.execute(" delete from PORTAFLOW_NUMERACION_PORTADA");
		        	
		        } catch (Exception e) { 	        	
		        	log.error(e);
		        }
		        finally
		         {
		             try
		             {
		            	 stmt.close();
		             }catch(SQLException e)
		             {
		                 log.error(e);
		             }
		         }	  
	  }
	 
	  public static void limpiarTablaHis(Connection con) throws Exception{       
		 	log.info("[limpiarTabla His] ");
			Statement stmt = null;
			
			try {
	        	stmt = con.createStatement();
	        	stmt.execute(" delete from PORTAFLOW_HNUMERACION_PORTADA");
	        	
	        } catch (Exception e) { 	        	
	        	log.error(e);
	        }
	        finally
	         {
	             try
	             {
	            	 stmt.close();
	             }catch(SQLException e)
	             {
	                 log.error(e);
	             }
	         }	  
	  }
	  
	 public static String cargarArchivo() {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			log.info("[cargarArchivo] ==> ");
		 	
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput("",OracleTypes.VARCHAR);

			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	con = ConexionAPI.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_MONITOREO.concat(".SP_CARGA_FILE"), inputCollection, outputCollection);
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
	 

	 
	 public static String insertaArchivo(Connection con, ArchivoPortadoVO archivoPortadoVO) {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			
			log.info("[cargarArchivo] ==> ");
		    
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(archivoPortadoVO.getNumero(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getDonante(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getReceptor(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getDonanteInicial(),OracleTypes.VARCHAR);
			
			inputCollection.addInput(archivoPortadoVO.getIdentificadorProceso(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getNumeroSecuencial(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getInicioProceso(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getInicioVentana(),OracleTypes.VARCHAR);
			log.info("==> getInicioVentana ==>"+ archivoPortadoVO.getInicioVentana());
			inputCollection.addInput(archivoPortadoVO.getDuracionVentana(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getEstado(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getFechaModificacion(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getDonantePrevio(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getTipoPortabilidad(),OracleTypes.VARCHAR);
			

			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	//con = ConexionAPI.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_MONITOREO.concat(".SP_INSERTA_ARCHIVO"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error("ERROR ORACLE:"+e);            
	        }
        
  
	        return codigo;
	 	}


	 
	 public static String insertaArchivoHis(Connection con, ArchivoPortadoVO archivoPortadoVO) {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			
			log.info("[cargarArchivo] ==> ");
		    
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(archivoPortadoVO.getNumero(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getDonante(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getReceptor(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getDonanteInicial(),OracleTypes.VARCHAR);
			
			inputCollection.addInput(archivoPortadoVO.getIdentificadorProceso(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getNumeroSecuencial(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getInicioProceso(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getInicioVentana(),OracleTypes.VARCHAR);
			log.info("==> getInicioVentana ==>"+ archivoPortadoVO.getInicioVentana());
			inputCollection.addInput(archivoPortadoVO.getDuracionVentana(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getEstado(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getFechaModificacion(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getDonantePrevio(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getTipoPortabilidad(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getFechaRegistro(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getTipoModificacion(),OracleTypes.VARCHAR);


			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	//con = ConexionAPI.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_MONITOREO.concat(".SP_INSERTA_ARCHIVO_HIS"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error("ERROR ORACLE:"+e);            
	        }
     

	        return codigo;
	 	}
 
	 
	 public static String maestraNumeracionPortada(String fechaEjecucion) {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			log.info("[maestraNumeracionPortada] ==> fechaEjecucion:"+fechaEjecucion);
		    
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(fechaEjecucion,OracleTypes.VARCHAR);
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	con = ConexionAPI.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_MONITOREO_PORTABILIDAD.concat(".SP_VALIDA_MAESTRA"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS+" - "+e.getMessage();
	        	log.error("ERROR ORACLE:"+e);            
	        }
        
	        log.info("[maestraNumeracionPortada] ==> RESULTADO:"+codigo);
		    
	        return codigo;
	 	}



	 public static String validaNumeracionPortada() {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			log.info("[validaNumeracionPortada] ==> ");
			String fechaEjecucion=" ";
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(fechaEjecucion,OracleTypes.VARCHAR);
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	con = ConexionAPI.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_MONITOREO_PORTABILIDAD.concat(".SP_VALIDA_NPORTADA"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS+" - "+e.getMessage();
	        	log.error("ERROR ORACLE:"+e);            
	        }
        
	        log.info("[validaNumeracionPortada] ==> codigo :"+codigo);
	        return codigo;
	 	}

	 
	 
	 public static String insertaArchivoNP(Connection con, ArchivoPortadoVO archivoPortadoVO, String fecha) {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			
			log.info("[cargarArchivo] ==> ");
		    
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(archivoPortadoVO.getIdentificadorProceso(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getNumero(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getInicioVentana(),OracleTypes.VARCHAR);
			inputCollection.addInput(fecha,OracleTypes.VARCHAR);
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	//con = ConexionAPI.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_MONITOREO.concat(".SP_INSERTA_ARCHIVO_NP"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error("ERROR ORACLE:"+e);            
	        }
     

	        return codigo;
	 	}
	 
	 public static String insertaArchivoSP(Connection con, ArchivoPortadoVO archivoPortadoVO, String fecha) {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			
			log.info("[cargarArchivo] ==> ");
		    
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(archivoPortadoVO.getIdentificadorProceso(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getNumero(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getInicioVentana(),OracleTypes.VARCHAR);
			inputCollection.addInput(fecha,OracleTypes.VARCHAR);
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	//con = ConexionAPI.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_MONITOREO.concat(".SP_INSERTA_ARCHIVO_SP"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error("ERROR ORACLE:"+e);            
	        }
  

	        return codigo;
	 	}
	 
	 public static String insertaArchivoSR(Connection con, ArchivoPortadoVO archivoPortadoVO, String fecha) {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			
			log.info("[cargarArchivo] ==> ");
		    
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(archivoPortadoVO.getIdentificadorProceso(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getNumero(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getInicioVentana(),OracleTypes.VARCHAR);
			inputCollection.addInput(fecha,OracleTypes.VARCHAR);
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	//con = ConexionAPI.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_MONITOREO.concat(".SP_INSERTA_ARCHIVO_SR"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error("ERROR ORACLE:"+e);            
	        }


	        return codigo;
	 	}
	 
	 public static String insertaArchivoNP_aux(Connection con, ArchivoPortadoVO archivoPortadoVO, String fecha) {
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			
			log.info("[cargarArchivo] ==> ");
		    
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(archivoPortadoVO.getIdentificadorProceso(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getNumero(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoPortadoVO.getInicioVentana(),OracleTypes.VARCHAR);
			inputCollection.addInput(fecha,OracleTypes.VARCHAR);
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	//con = ConexionAPI.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_MONITOREO.concat(".SP_INSERTA_ARCHIVO_NP_AUX"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
	            }
	            
	        } catch (Exception e) { 
	        	codigo = ValueConstants.ERROR_CAUSAS_TECNICAS;
	        	log.error("ERROR ORACLE:"+e);            
	        }
     

	        return codigo;
	 	}
	 
}
