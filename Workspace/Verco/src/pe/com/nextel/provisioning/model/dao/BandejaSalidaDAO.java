package pe.com.nextel.provisioning.model.dao;

/**
 * <p>Title: Bande de Salida DAO</p>
 * <p>Description: Clase encargada al mantenimiento de la Tabla BANDEJA DE SALIDA.(DAO)</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : BandejaSalidaDAO</p>
 * <p>Fecha       : 24 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  CARLOS NISHIMURA (COMSA)
 * @version 1.0
 */

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
import pe.com.nextel.provisioning.model.vo.BandejaSalidaVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class BandejaSalidaDAO {

	private static final Log log = LogFactory.getLog(BandejaSalidaDAO.class);    
	private static String desClassMethod;
	public BandejaSalidaDAO(){}

	/**
	 * Lee la tabla Bandeja de Salida 
	 * @param estado
	 * @return
	 * @throws Exception
	 */
	public static Map leerBanSal(String estado) throws Exception {
		Connection con = null;
		ParameterCollection inputCollection = new ParameterCollection();
		ParameterCollection outputCollection = new ParameterCollection();
		String cuenta = "0";
		desClassMethod="BandejaSalidaDAO.leerBanSal()";
		Map map = new HashMap();
		String sError="000" ;
		try {
			con = JdbcUtilitario.getInstance().getConnection();
			inputCollection.addInput(estado,OracleTypes.VARCHAR);
			outputCollection.addOutput(OracleTypes.VARCHAR);
			outputCollection.addOutput(OracleTypes.VARCHAR);
			Object[] object  = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_CNC.concat(".SP_BANDEJA_SALIDA"), inputCollection, outputCollection);		
			if (object != null) {
				cuenta = String.valueOf(object[0]);
				sError = String .valueOf(object[1]) ;
				if (log.isInfoEnabled()) {
					log.info(" => Call "+ desClassMethod +" "+cuenta);    		
				}
			}
		} catch (Exception e) { 
			sError = e.getMessage() ;
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
			}catch(SQLException e)
			{
				sError = e.getMessage() ;
				e.printStackTrace();
				log.error(e);
			}
		}
		map.put("sError", sError);
		map.put("oResult", cuenta);
		return map;
	}
    
	
	public static Map leerBanSalXId(String idBandeja) throws Exception {
		Connection con = null;
		ParameterCollection inputCollection = new ParameterCollection();
		ParameterCollection outputCollection = new ParameterCollection();
		String cuenta = "0";
		desClassMethod="BandejaSalidaDAO.leerBanSalXId()";
		Map map = new HashMap();
		String sError="000" ;
		try {
			con = JdbcUtilitario.getInstance().getConnection();
			inputCollection.addInput(idBandeja,OracleTypes.VARCHAR);
			outputCollection.addOutput(OracleTypes.VARCHAR);
			outputCollection.addOutput(OracleTypes.VARCHAR);
			Object[] object  = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_CNC.concat(".SP_OBTENER_BANDEJA_SALIDA_ID"), inputCollection, outputCollection);		
			if (object != null) {
				cuenta = String.valueOf(object[0]);
				sError = String .valueOf(object[1]) ;
				if (log.isInfoEnabled()) {
					log.info(" => Call "+ desClassMethod +" "+cuenta);    		
				}
			}
		} catch (Exception e) { 
			sError = e.getMessage() ;
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
			}catch(SQLException e)
			{
				sError = e.getMessage() ;
				e.printStackTrace();
				log.error(e);
			}
		}
		map.put("sError", sError);
		map.put("oResult", cuenta);
		return map;
	}
	
	public static Map insertarBandejaSalida(String idBandeja) throws Exception {
		Connection con = null;
		ParameterCollection inputCollection = new ParameterCollection();
		ParameterCollection outputCollection = new ParameterCollection();
		String cuenta = "0";
		desClassMethod="BandejaSalidaDAO.leerBanSalXId()";
		Map map = new HashMap();
		String sError="000" ;
		try {
			con = JdbcUtilitario.getInstance().getConnection();
			inputCollection.addInput(idBandeja,OracleTypes.VARCHAR);
			outputCollection.addOutput(OracleTypes.VARCHAR);
			outputCollection.addOutput(OracleTypes.VARCHAR);
			Object[] object  = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_CNC.concat(".SP_INSERTAR_BANDEJA_SALIDA"), inputCollection, outputCollection);		
			if (object != null) {
				cuenta = String.valueOf(object[0]);
				sError = String .valueOf(object[1]) ;
				if (log.isInfoEnabled()) {
					log.info(" => Call "+ desClassMethod +" "+cuenta);    		
				}
			}
		} catch (Exception e) { 
			sError = e.getMessage() ;
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
			}catch(SQLException e)
			{
				sError = e.getMessage() ;
				e.printStackTrace();
				log.error(e);
			}
		}
		map.put("sError", sError);
		map.put("oResult", cuenta);
		return map;
	}
	
	
	/**
	 * Elimina Registro en la Tabla Bandeja Salida
	 * @param codRegistro
	 * @return
	 * @throws Exception
	 */
	public static String  eliminarRegBanSal(Object codRegistro ) throws Exception {
		Connection con = null;
		ParameterCollection inputCollection = new ParameterCollection();
		inputCollection.addInput(((java.math.BigDecimal)codRegistro).toString(),OracleTypes.VARCHAR);
		ParameterCollection outputCollection = new ParameterCollection();
		outputCollection.addOutput(OracleTypes.VARCHAR);
		String result = ValueConstants.RESULT;
		desClassMethod="BandejaSalidaDAO.eliminarRegBanSal() ::: " ;
		try {
			con = JdbcUtilitario.getInstance().getConnection();
			Object[] object  = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_CNC.concat(".SP_ELIMINA_REG_BANDEJA_SALIDA"), inputCollection, outputCollection);		
			if (object != null) {
				result = String.valueOf(object[0]);
				if ( ! result.equals("OK") ) result=desClassMethod+result;
				if (log.isInfoEnabled())  {
					log.info(" => Call " + desClassMethod+ " "+ result);    		
				}
			}
		} catch (Exception e) { 
			result = e.getMessage(); 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
			}catch(SQLException e)
			{
				result = e.getMessage(); 
				e.printStackTrace();
				log.error(e);
			}
		}
		return result;
    }
	
//	@SuppressWarnings("unchecked")
//	public static List<DynaBean> cargarLista(String estado) throws Exception {
//		 List<DynaBean> lista = null;
//		 Connection con = JdbcUtilitario.getInstance().getConnection();
//		 ParameterCollection inputCollection = new ParameterCollection();
//         
//         try
//         {
//        	 inputCollection.addInput(estado,OracleTypes.VARCHAR); 
//             con= JdbcUtilitario.getInstance().getConnection();
//             lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_CNC.concat(".SP_OBTENER_BANDEJA_SALIDA"), inputCollection);     
//         }catch(Exception e)
//         {
//             e.printStackTrace();
//             log.error(e);
//             throw new DAOException(e.getMessage(),e);
//         }
//         finally
//         {
//             try
//             {
//                 con.close();
//             }catch(SQLException e)
//             {
//                 e.printStackTrace();
//                 log.error(e);
//             }
//         }
//         return lista;
//	}

	/**
	 * Carga la lista de la Tabla Bandeja de Salida
	 */
	@SuppressWarnings("unchecked")
	public static Map cargarLista(String estado) throws Exception {
		 List<DynaBean> lista = null;
		 Connection con = JdbcUtilitario.getInstance().getConnection();
		 ParameterCollection inputCollection = new ParameterCollection();
		 ParameterCollection outputCollection = new ParameterCollection();
         Map map =  new HashMap(); 		 
         String sError="000" ;
         try
         {
        	 inputCollection.addInput(estado,OracleTypes.VARCHAR); 
        	 outputCollection.addOutput(OracleTypes.VARCHAR);
        	 outputCollection.addOutput(OracleTypes.CURSOR);
        	 con= JdbcUtilitario.getInstance().getConnection();
             Object[] object = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_CNC.concat(".SP_OBTENER_BANDEJA_SALIDA"),inputCollection,outputCollection);	     
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
	
	/**
	 * Actualiza el registro de la Tabla en la Bandeja de Salida
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	public static String actualizarBandejaSalida(BandejaSalidaVO obj) throws DAOException{
		Connection con = null;
		ParameterCollection inputCollection = new ParameterCollection();
		inputCollection.addInput(obj.getReIntentos(),OracleTypes.VARCHAR);
		inputCollection.addInput(obj.getIdBandeja() ,OracleTypes.VARCHAR);
		ParameterCollection outputCollection = new ParameterCollection();
		outputCollection.addOutput(OracleTypes.VARCHAR);
		String result= ValueConstants.RESULT ;
		desClassMethod = "BandejaSalidaDAO.actualizarBandejaSalida() ::: " ;
		try {
			con = JdbcUtilitario.getInstance().getConnection();
			Object[] object  = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_CNC.concat(".SP_ACTUALIZA_BANDEJA_SALIDA"), inputCollection, outputCollection);		
			if (object != null) {
				result = String.valueOf(object[0]);
				if ( ! result.equals("OK") ) result=desClassMethod+result;
				if (log.isInfoEnabled())  {
					log.info(" => Call " + desClassMethod + " " + result );    		
				}
			}
		} catch (Exception e) { 
			result = e.getMessage() ; 
			throw new DAOException(e.getMessage(), e);
		}
		finally
		{
			try
			{
				con.close();
			}catch(SQLException e)
			{
				result = e.getMessage() ; 
				e.printStackTrace();
				log.error(e);
			}
		}
		return result;
    }
	
	/**
   * Metodo que inserta un registro en la tabla BANDEJASALIDA
   * @param BandejaSalidaVO bandsal
   * @return String
   * @throws Exception
   */
  public String insertar(BandejaSalidaVO bandsal) throws Exception{       
	  Object[] objectL = new Object[1];
	  String codigo = "";
	  Connection con = null;
	  log.info("BandejaSalidaDAO [insertar]  idbandeja:: "+bandsal.getIdBandeja()+" ==> reintentos ::"+bandsal.getReIntentos()+" ==> bloq::"+bandsal.getbLoqueado());
	  ParameterCollection inputCollection = new ParameterCollection();
	  inputCollection.addInput(bandsal.getIdBandeja(),OracleTypes.NUMBER);
	  inputCollection.addInput(bandsal.getReIntentos(),OracleTypes.NUMBER);
	  inputCollection.addInput(bandsal.getbLoqueado(),OracleTypes.VARCHAR);
	  ParameterCollection outputCollection = new ParameterCollection();
	  outputCollection.addOutput(OracleTypes.VARCHAR);
	  try {
	    con = JdbcUtilitario.getInstance().getConnection();
	    objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_REGISTRAR_BANDEJASALIDA"), inputCollection, outputCollection);
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
  

	 public static String obtenerCantidadMensajes() throws Exception{
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
		 	ParameterCollection inputCollection = new ParameterCollection();
		 	inputCollection.addInput(codigo, OracleTypes.VARCHAR);
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
     	con = JdbcUtilitario.getInstance().getConnection();
     	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_LISTAR_BANDEJA_SALIDA"), inputCollection, outputCollection);
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
	 
	 
	 
}