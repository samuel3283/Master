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
import pe.com.nextel.provisioning.model.vo.ArchivoFRVO;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.model.vo.MensajeVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class ArchivoFRDAO {
	 private static final Log log = LogFactory.getLog(ArchivoFRDAO.class);    
		
	 public ArchivoFRDAO() 
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
	    	lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_RF.concat(".SP_CONSULTAR_FR"), inputCollection);
	
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
	 
	 public static String insertar(ArchivoFRVO archivoFR) throws Exception{       
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			log.info("[ArchivoFRDAO insertar] ==> IdCabecera "+ archivoFR.getCabecera().getCodigo() +
			" ==> NumeroTelefono "+ archivoFR.getNumeroTelefono() +
			" ==> FechaEjecucion "+ archivoFR.getFechaEjecucion() +
			" ==> FechaComunicacion "+ archivoFR.getFechaComunicacion() +
			" ==> Receptor "+ archivoFR.getReceptor() +
			" ==> CedenteInicial "+ archivoFR.getCedenteInicial() +           	 
			" ==> MotivoRetorno "+ archivoFR.getMotivoRetorno() +
			" ==> TipoTecnologia "+ archivoFR.getTipoTecnologia() +
			" ==> WorldNumber "+ archivoFR.getWorldNumber() +		
			" ==> Asignatario "+ archivoFR.getAsignatario());
			
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(Integer.parseInt(archivoFR.getCabecera().getCodigo()),OracleTypes.NUMBER);
			inputCollection.addInput(archivoFR.getNumeroTelefono().trim(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoFR.getFechaEjecucion().trim(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoFR.getFechaComunicacion().trim(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoFR.getReceptor().trim(),OracleTypes.VARCHAR);
			inputCollection.addInput(archivoFR.getCedenteInicial().trim(),OracleTypes.VARCHAR);		
			inputCollection.addInput(archivoFR.getMotivoRetorno().trim(),OracleTypes.VARCHAR);
			inputCollection.addInput((archivoFR.getTipoTecnologia()!=null) ? archivoFR.getTipoTecnologia().trim():"",OracleTypes.VARCHAR);
			inputCollection.addInput((archivoFR.getWorldNumber()!=null) ? archivoFR.getWorldNumber().trim():"",OracleTypes.VARCHAR);
			inputCollection.addInput((archivoFR.getAsignatario()!=null) ? archivoFR.getAsignatario().trim():"",OracleTypes.VARCHAR);
//	        inputCollection.addInput(blob, OracleTypes.BLOB);			
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_RF.concat(".SP_GRABAR_FR"), inputCollection, outputCollection);
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
	 
	 public static List<DynaBean> ObtenerArchivosFr(CabeceraArchivoVO cabeceraArchivoVO) throws DAOException {     
	     List<DynaBean> lista = null;
		 Connection con = null;
		 ParameterCollection inputCollection = new ParameterCollection();
         inputCollection.addInput(cabeceraArchivoVO.getIdcabecera(),OracleTypes.NUMBER);
         ParameterCollection outputCollection = new ParameterCollection();
		 outputCollection.addOutput(OracleTypes.VARCHAR);
       
         try
         {
             con= JdbcUtilitario.getInstance().getConnection();
             lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_ARCHIVOS_FR"), inputCollection);     
         }catch(Exception e)
         {
             e.printStackTrace();
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
         return lista;
	 }

	
	
	public static void updateArchivoFrWs(ArchivoFRVO archivoFRVO, MensajeVO mensajeVO, TransactionContext context) throws DAOException {
		
		ParameterCollection inputCollection = new ParameterCollection();
        inputCollection.addInput(archivoFRVO.getIdarchivo(),OracleTypes.NUMBER);
        inputCollection.addInput(mensajeVO.getComando(),OracleTypes.VARCHAR);
	    inputCollection.addInput(mensajeVO.getHLRAddress(),OracleTypes.VARCHAR);
	    inputCollection.addInput(mensajeVO.getNumberType(),OracleTypes.VARCHAR);
	    inputCollection.addInput(mensajeVO.getNewRoute(),OracleTypes.VARCHAR);
	         
             try
             {
                  QueryUtil.execute(context.getConnection(),ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_UPDATE_ARCHIVO_FRWS"), inputCollection);
             }
             catch(Exception e)
             { 
                log.error(e.getMessage()); 
                throw new DAOException(e.getMessage(), e);
             }
     }
	
	
	
}
