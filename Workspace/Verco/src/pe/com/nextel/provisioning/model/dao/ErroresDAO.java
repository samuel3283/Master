package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
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
import pe.com.nextel.provisioning.view.ValueConstants;

/**
 * <p>Title: ErroresDAO</p>
 * <p>Description: Clase DAO encargada del acceso a los datos de la tabla JOB.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : ErroresDAO</p>
 * <p>Fecha       : 20 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  FRANK PICOY ROSAS (COMSA)
 * @version 1.0
 */

public class ErroresDAO {
  private static final Log log = LogFactory.getLog(ErroresDAO.class);    

  /**
   * Metodo constructor de la clase
   */
  public ErroresDAO() {
    
  } 

  /**
   * Metodo que obtiene los Errores para determinar cuales son los que detienen el proceso
   * @return List<DynaBean>
   * @throws Exception
   */
  public Map<String, String> obtenerErrores() {
    Map<String, String> mapaErrores = new HashMap<String, String>();
    List<DynaBean> lista=null;
    Connection con = null;
    DynaBean dyna = null;
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_ERRORES"));
      for (int i=0;i<lista.size();i++) {
        dyna = (DynaBean)lista.get(i);
        log.info((String)dyna.get("CODIGO") + " " + (String)dyna.get("DETIENEPROCESO"));
        mapaErrores.put((String)dyna.get("CODIGO"), (String)dyna.get("DETIENEPROCESO"));
      }
    }catch(Exception e) {
      e.printStackTrace();
      log.error(e);
      //throw new DAOException(e.getMessage(),e);
    }
    return mapaErrores;
  }
  
  /**
   * Metodo que obtiene los Errores para determinar cuales son los que detienen el proceso
   * @return List<DynaBean>
   * @throws Exception
   */
  public String obtenerDescripcionError(String codigo) {
    Object[] objectL = new Object[1];
    String desError="";
    List<DynaBean> lista=null;
    Connection con = null;
    DynaBean dyna = null;
    try {
      ParameterCollection inputCollection = new ParameterCollection();
      inputCollection.addInput(codigo,OracleTypes.VARCHAR);
      log.info("el codigo de error es-" + codigo + "-");
      ParameterCollection outputCollection = new ParameterCollection();
      outputCollection.addOutput(OracleTypes.VARCHAR);
      con = JdbcUtilitario.getInstance().getConnection();
      objectL = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_ERROR"),inputCollection,outputCollection);
      if (objectL != null) {
        desError = String.valueOf(objectL[0]);
        log.info("el descripcion de error es-" + desError + "-");
      }
    }catch(Exception e) {
      e.printStackTrace();
      log.error(e);
      //throw new DAOException(e.getMessage(),e);
    }
    return desError;
  }
  
}
