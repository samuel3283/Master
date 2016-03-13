package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import oracle.jdbc.OracleTypes;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.conexion.ParameterCollection;
import pe.com.nextel.provisioning.framework.conexion.QueryUtil;
import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.util.transaction.TransactionContext;
import pe.com.nextel.provisioning.model.vo.ArchivoFPVO;
import pe.com.nextel.provisioning.model.vo.ArchivoFRVO;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.model.vo.MensajeVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class ArchivoFPDAO {
	 private static final Log log = LogFactory.getLog(ArchivoFPDAO.class);    
		
	 public ArchivoFPDAO() 
	 {
	 } 
	 
	 @SuppressWarnings("unchecked")
		public static List<DynaBean> consultar(FiltroVO filtro) throws Exception
		 {    
			List<DynaBean> lista = null;
			Connection con = null;
			
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(filtro.getCaso(),OracleTypes.VARCHAR);	//caso
			inputCollection.addInput(filtro.getNombre(),OracleTypes.VARCHAR);	//valor	
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_RF.concat(".SP_CONSULTAR_FP"), inputCollection);

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
	         
			return lista;
		 }	 	 
	 
	 public static String insertar(ArchivoFPVO archivoFP) throws Exception{       
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			log.info("ArchivoFPDAO [insertar] ==> IdCabecera: "+ archivoFP.getCabecera().getCodigo()+
			" ==> NumeroSolicitud: "+ archivoFP.getNumeroSolicitud() +
			" ==> NumeroTelefono: "+ archivoFP.getNumeroTelefono() +
			" ==> FechaEjecucion: "+ archivoFP.getFechaEjecucion() +
			" ==> Receptor: "+ archivoFP.getReceptor() +
			" ==> Cedente: "+ archivoFP.getCedente() +     			
			" ==> TipoTecnologia: "+ archivoFP.getTipoTecnologia() +
			" ==> WorldNumber: "+ archivoFP.getWorldNumber() + " ==> Asignatario: "+ archivoFP.getAsignatario());
			
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(Integer.parseInt(archivoFP.getCabecera().getCodigo()),OracleTypes.NUMBER);
			inputCollection.addInput(archivoFP.getNumeroSolicitud().trim(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoFP.getNumeroTelefono().trim(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoFP.getFechaEjecucion().trim(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoFP.getReceptor().trim(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoFP.getCedente().trim(),OracleTypes.VARCHAR);	
			inputCollection.addInput((archivoFP.getTipoTecnologia()!=null) ? archivoFP.getTipoTecnologia().trim():"",OracleTypes.VARCHAR);
			inputCollection.addInput((archivoFP.getWorldNumber()!=null) ? archivoFP.getWorldNumber().trim():"",OracleTypes.VARCHAR);			
			inputCollection.addInput((archivoFP.getAsignatario()!=null) ? archivoFP.getAsignatario().trim():"",OracleTypes.VARCHAR);
//	        inputCollection.addInput(blob, OracleTypes.BLOB);
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_RF.concat(".SP_GRABAR_FP"), inputCollection, outputCollection);
	            if (objectL != null) {
	                codigo = String.valueOf(objectL[0]);
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
	        
	     return codigo;
         
     }
	 
	
		public static List<DynaBean> ObtenerArchivosFp(CabeceraArchivoVO cabeceraArchivoVO) throws DAOException {     
		     List<DynaBean> lista = null;
			 Connection con = null;
			 ParameterCollection inputCollection = new ParameterCollection();
	         inputCollection.addInput(cabeceraArchivoVO.getIdcabecera(),OracleTypes.NUMBER);
	         ParameterCollection outputCollection = new ParameterCollection();
			 outputCollection.addOutput(OracleTypes.VARCHAR);
	       
	         try
	         {
	             con= JdbcUtilitario.getInstance().getConnection();
	             lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_ARCHIVOS_FP"), inputCollection);     
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
	         return lista;
		 }

		
		public static void updateArchivoFpWs(ArchivoFPVO archivoFPVO, MensajeVO mensajeVO, TransactionContext context) throws DAOException {
			
			ParameterCollection inputCollection = new ParameterCollection();
	        inputCollection.addInput(archivoFPVO.getIdarchivo(),OracleTypes.NUMBER);
	        inputCollection.addInput(mensajeVO.getComando(),OracleTypes.VARCHAR);
		    inputCollection.addInput(mensajeVO.getHLRAddress(),OracleTypes.VARCHAR);
		    inputCollection.addInput(mensajeVO.getNumberType(),OracleTypes.VARCHAR);
		    inputCollection.addInput(mensajeVO.getNewRoute(),OracleTypes.VARCHAR);
  
	             try
	             {
	                 QueryUtil.execute(context.getConnection(),ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_UPDATE_ARCHIVO_FPWS"), inputCollection);
	             }
	             catch(Exception e)
	             { 
	                log.error(e.getMessage()); 
	                throw new DAOException(e.getMessage(), e);
	             }

	             
	     }

		
		public static void updateArchivoFrWs(ArchivoFRVO archivoFRVO, MensajeVO mensajeVO) throws DAOException {
			Connection con = null;
			
			ParameterCollection inputCollection = new ParameterCollection();             
	        inputCollection.addInput(archivoFRVO.getIdarchivo(),OracleTypes.NUMBER);
	        inputCollection.addInput(mensajeVO.getComando(),OracleTypes.VARCHAR);
		    inputCollection.addInput(mensajeVO.getHLRAddress(),OracleTypes.VARCHAR);
		    inputCollection.addInput(mensajeVO.getNumberType(),OracleTypes.VARCHAR);
		    inputCollection.addInput(mensajeVO.getNewRoute(),OracleTypes.VARCHAR);

	             try
	             {
	                  QueryUtil.execute(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_UPDATE_ARCHIVO_FRWS"), inputCollection);
		                
	             }
	             catch(Exception e)
	             { 
	                log.error(e.getMessage()); 
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

	     }
	 
}
