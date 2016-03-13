package pe.com.nextel.provisioning.model.dao;

/**
 * <p>Title: Log DAO</p>
 * <p>Description: Clase encargada al mantenimiento de la Tabla LOG.(DAO)</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : LogDAO</p>
 * <p>Fecha       : 24 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  CARLOS NISHIMURA (COMSA)
 * @version 1.0
 */


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
import pe.com.nextel.provisioning.model.vo.LogVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class LogDAO {

	public LogDAO(){}

	private static final Log log = LogFactory.getLog(LogDAO.class);    

	/**
	 * Inserta registro en la Tabla Log
	 * @param bean
	 * @return
	 * @throws DAOException
	 */
	public static String insertarLog(LogVO bean) throws DAOException{

		Connection con = null;
		ParameterCollection inputCollection = new ParameterCollection();
		inputCollection.addInput(bean.getDescripcion() ,OracleTypes.VARCHAR);
		inputCollection.addInput(bean.getIdError() ,OracleTypes.VARCHAR);
		ParameterCollection outputCollection = new ParameterCollection();
		outputCollection.addOutput(OracleTypes.VARCHAR);
		String result = ValueConstants.RESULT;
		try {
			con = JdbcUtilitario.getInstance().getConnection();
			Object[] object  = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_CNC.concat(".SP_INSERTAR_LOG"), inputCollection, outputCollection);		
			if (object != null) {
				result = String.valueOf(object[0]);
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
	
	/**
	 * Actualiza registro en la tabla Log
	 * @param bean
	 * @return
	 * @throws DAOException
	 */
	public static String actualizarLog(LogVO bean) throws DAOException{

		Connection con = null;
		ParameterCollection inputCollection = new ParameterCollection();
		inputCollection.addInput(bean.getDescripcion(),OracleTypes.VARCHAR);
		inputCollection.addInput(bean.getIdError(),OracleTypes.VARCHAR);
		inputCollection.addInput(bean.getIdLog(),OracleTypes.VARCHAR);
		ParameterCollection outputCollection = new ParameterCollection();
		outputCollection.addOutput(OracleTypes.VARCHAR);
		String result = ValueConstants.RESULT;
		try {
			con = JdbcUtilitario.getInstance().getConnection();
			Object[] object  = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_CNC.concat(".SP_ACTUALIZA_LOG"), inputCollection, outputCollection);		
			if (object != null) {
				result = String.valueOf(object[0]);
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

	/**
	 * Elimina registro en la Tabla Log
	 * @param bean
	 * @return
	 * @throws DAOException
	 */
	public static String eliminarLog(LogVO bean) throws DAOException{

		Connection con = null;
		ParameterCollection inputCollection = new ParameterCollection();
		inputCollection.addInput(bean.getIdLog(),OracleTypes.VARCHAR);
		ParameterCollection outputCollection = new ParameterCollection();
		outputCollection.addOutput(OracleTypes.VARCHAR);
		String result = ValueConstants.RESULT;
		try {
			con = JdbcUtilitario.getInstance().getConnection();
			Object[] object  = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_CNC.concat(".SP_ELIMINAR_LOG"), inputCollection, outputCollection);		
			if (object != null) {
				result = String.valueOf(object[0]);
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

	/**
	 * Lista registro en la Tabla Log
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<DynaBean> cargarLista() throws Exception {
		 List<DynaBean> lista = null;
		 Connection con = JdbcUtilitario.getInstance().getConnection();
		 ParameterCollection inputCollection = new ParameterCollection();
         try
         {
             con= JdbcUtilitario.getInstance().getConnection();
             lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_CNC.concat(".SP_OBTENER_LOG"), inputCollection);     
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
}
