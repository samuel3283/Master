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
import pe.com.nextel.provisioning.model.vo.ContactoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.view.ValueConstants;

/**
 * <p>Title: Lista de Contactos</p>
 * <p>Description: Clase encargada del acceso a datos a la tabla Contacto.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : ContactoDAO</p>
 * <p>Fecha       : 04 Diciembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  WALTER RODRIGUEZ (COMSA)
 * @version 1.0
 */

public class ContactoDAO {
  private static final Log log = LogFactory.getLog(ContactoDAO.class);    

  /**
   * Constructor de la clase
   */
  public ContactoDAO() {
  
  } 

  /**
   * Metodo que se encarga de listar los contactos de acuerdo al filtro ingresado
   * @param FiltroVO filtro
   * @return List<DynaBean>
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static List<DynaBean> consultar(FiltroVO filtro) throws Exception {    
    List<DynaBean> lista = null;
    Connection con = null;
    ParameterCollection inputCollection = new ParameterCollection();
    inputCollection.addInput(filtro.getCaso(),OracleTypes.VARCHAR);	//caso
    inputCollection.addInput(filtro.getNombre(),OracleTypes.VARCHAR);	//nombre	
    inputCollection.addInput(filtro.getCodigo(),OracleTypes.VARCHAR);	//tipoContacto
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_RF.concat(".SP_CONSULTAR_CONTACTO"), inputCollection);
    } catch(Exception e) {
      e.printStackTrace();
      log.error(e);
      throw new DAOException(e.getMessage(),e);
    } finally {
      try {
        con.close();
      } catch(SQLException e) {
        e.printStackTrace();
        log.error(e);
      }
    }        
    return lista;
  }	 

  /**
   * Metodo que se encarga de registrar un contacto
   * @param ContactoVO filtro
   * @return String
   * @throws Exception
   */
  public static String insertar(ContactoVO contacto) throws Exception {       
    Object[] objectL = new Object[1];
    String codigo = "";
    Connection con = null;
    ParameterCollection inputCollection = new ParameterCollection();
    inputCollection.addInput(contacto.getNombre(),OracleTypes.VARCHAR);
    inputCollection.addInput(contacto.getEmail(),OracleTypes.VARCHAR);
    inputCollection.addInput(contacto.getCelular(),OracleTypes.VARCHAR);
    inputCollection.addInput(contacto.getSms(),OracleTypes.VARCHAR);
    inputCollection.addInput(contacto.getTipoContacto(),OracleTypes.VARCHAR);
    ParameterCollection outputCollection = new ParameterCollection();
    outputCollection.addOutput(OracleTypes.VARCHAR);
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_RF.concat(".SP_INSERTAR_CONTACTO"), inputCollection, outputCollection);
      if (objectL != null) {
        codigo = String.valueOf(objectL[0]);
      }
    } catch (Exception e) { 
      log.error(e);
      throw new DAOException(e.getMessage(), e);
    }
    finally {
      try {
        con.close();
      } catch(SQLException e) {
        e.printStackTrace();
        log.error(e);
      }
    }	    
    return codigo;         
  }

  /**
   * Metodo que se encarga de modificar un contacto
   * @param ContactoVO filtro
   * @return String
   * @throws Exception
   */
  public static String modificar(ContactoVO contacto) throws Exception {       
    Object[] objectL = new Object[1];
    String codigo = "";
    Connection con = null;
    ParameterCollection inputCollection = new ParameterCollection();
    inputCollection.addInput(contacto.getIdContacto(),OracleTypes.NUMBER);
    inputCollection.addInput(contacto.getNombre(),OracleTypes.VARCHAR);
    inputCollection.addInput(contacto.getEmail(),OracleTypes.VARCHAR);
    inputCollection.addInput(contacto.getCelular(),OracleTypes.VARCHAR);
    inputCollection.addInput(contacto.getSms(),OracleTypes.VARCHAR);
    inputCollection.addInput(contacto.getTipoContacto(),OracleTypes.VARCHAR);
    ParameterCollection outputCollection = new ParameterCollection();
    outputCollection.addOutput(OracleTypes.VARCHAR);
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_RF.concat(".SP_MODIFICAR_CONTACTO"), inputCollection, outputCollection);
      if (objectL != null) {
        codigo = String.valueOf(objectL[0]);
      }
    } catch (Exception e) { 
      log.error(e);
      throw new DAOException(e.getMessage(), e);
    }
    finally {
      try {
        con.close();
      } catch(SQLException e) {
        e.printStackTrace();
        log.error(e);
      }
    }	    
    return codigo;
  }

  /**
   * Metodo que se encarga de eliminar un contacto
   * @param ContactoVO filtro
   * @return String
   * @throws Exception
   */
  public static String eliminar(ContactoVO contacto) throws Exception {       		 
    Object[] objectL = new Object[1];
    String codigo = "";
    Connection con = null;
    ParameterCollection inputCollection = new ParameterCollection();
    inputCollection.addInput(contacto.getIdContacto(),OracleTypes.NUMBER);
    ParameterCollection outputCollection = new ParameterCollection();
    outputCollection.addOutput(OracleTypes.VARCHAR);
    try {
      con = JdbcUtilitario.getInstance().getConnection();
      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_RF.concat(".SP_ELIMINAR_CONTACTO"), inputCollection, outputCollection);
      if (objectL != null) {
        codigo = String.valueOf(objectL[0]);
      }
    } catch (Exception e) { 
      log.error(e);
      throw new DAOException(e.getMessage(), e);
    }
    finally {
      try {
        con.close();
      } catch(SQLException e) {
        e.printStackTrace();
        log.error(e);
      }
    }	    
    return codigo;
  }	 
}
