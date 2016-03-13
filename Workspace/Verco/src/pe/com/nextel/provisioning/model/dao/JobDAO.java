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
import pe.com.nextel.provisioning.model.vo.JobVO;
import pe.com.nextel.provisioning.view.ValueConstants;

/**
 * <p>Title: JobDAO</p>
 * <p>Description: Clase DAO encargada del acceso a los datos de la tabla JOB.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : JobDAO</p>
 * <p>Fecha       : 20 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  FRANK PICOY ROSAS (COMSA)
 * @version 1.0
 */

public class JobDAO {
  private static final Log log = LogFactory.getLog(JobDAO.class);    

  /**
   * Metodo constructor de la clase
   */
  public JobDAO() 
  {
  } 

  /**
   * Metodo que inserta un registro en la tabla JOB
   * @param JobVO job
   * @return String
   * @throws Exception
   */
  public String insertar(JobVO job) throws Exception{       
    Object[] objectL = new Object[1];
    String codigo = "";
    Connection con = null;
    log.info("[JobDAO insertar] ==> IdCabecera "+ job.getIdarchivo() +
    " ==> IdCabecera "+ job.getIdcabecera() +
    " ==> IdMensaje "+ job.getMensaje() +
    " ==> Fec Ejecucion "+ job.getFechaejecucion());
    ParameterCollection inputCollection = new ParameterCollection();
    inputCollection.addInput(Integer.parseInt(job.getIdarchivo()),OracleTypes.NUMBER);
    inputCollection.addInput(job.getFechaejecucion(),OracleTypes.VARCHAR);
    inputCollection.addInput(Integer.parseInt(job.getIdcabecera()),OracleTypes.NUMBER);
    inputCollection.addInput(job.getMensaje().trim(),OracleTypes.VARCHAR);
    ParameterCollection outputCollection = new ParameterCollection();
    outputCollection.addOutput(OracleTypes.VARCHAR);
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_REGISTRAR_JOB"), inputCollection, outputCollection);
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

  /**
   * Metodo que elimina un registro en la tabla JOB
   * @param JobVO job
   * @return String
   * @throws Exception
   */
  public String eliminar(JobVO job) throws Exception{       
    Object[] objectL = new Object[1];
    String codigo = "";
    Connection con = null;
    log.info("[JobDAO eliminar] ==> IdCabecera "+ job.getIdarchivo());
    ParameterCollection inputCollection = new ParameterCollection();
    inputCollection.addInput(Integer.parseInt(job.getIdjob()),OracleTypes.NUMBER);
    ParameterCollection outputCollection = new ParameterCollection();
    outputCollection.addOutput(OracleTypes.VARCHAR);
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_ELIMINAR_JOB"), inputCollection, outputCollection);
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
  
  /**
   * Metodo que obtiene los Jobs que esten listos para su Aprovisionamiento
   * @return List<DynaBean>
   * @throws Exception
   */
  public List<DynaBean> obtenerJobsParaAprovisionamiento() throws Exception{
    
    List<DynaBean> lista = null;
    Connection con = null;
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_JOBS"));
    }catch(Exception e) {
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
  
  /**
   * Metodo que inserta un registro en la tabla JOB
   * @param JobVO job
   * @return String
   * @throws Exception
   */
  public String actualizar(JobVO job) throws Exception{       
    Object[] objectL = new Object[1];
    String codigo = "";
    Connection con = null;
    log.info("[JobDAO actualizar] ==> IdJob "+ job.getIdjob());

    ParameterCollection inputCollection = new ParameterCollection();
    inputCollection.addInput(Integer.parseInt(job.getIdjob()),OracleTypes.NUMBER);
    ParameterCollection outputCollection = new ParameterCollection();
    outputCollection.addOutput(OracleTypes.VARCHAR);
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_ACTUALIZAR_JOB"), inputCollection, outputCollection);
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
