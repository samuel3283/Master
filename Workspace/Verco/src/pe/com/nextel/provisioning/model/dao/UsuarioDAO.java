
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
import pe.com.nextel.provisioning.model.vo.UsuarioVO;
import pe.com.nextel.provisioning.view.ValueConstants;


public class UsuarioDAO {
	 private static final Log log = LogFactory.getLog(UsuarioDAO.class);    
		
	 public UsuarioDAO() 
	 {
	 }  	

	 
	 public static String validarUsuario(UsuarioVO usuarioVO) throws Exception
	 {       
		 log.info("[validarUsuario] INICIO");
		 Object[] object = new Object[1];
         String codigo="0";
         Connection con = null;
         
		log.info("UsuarioDAO [validarUsuario] ==> Nombre: "+ usuarioVO.getNombre()+
				" ==> Usuario: "+ usuarioVO.getUsuario() +
				" ==> Password: "+ usuarioVO.getPassword());         
         
		 ParameterCollection inputCollection = new ParameterCollection();
         inputCollection.addInput(usuarioVO.getUsuario().trim(),OracleTypes.VARCHAR);
         inputCollection.addInput(usuarioVO.getPassword().trim(),OracleTypes.VARCHAR);
		 ParameterCollection outputCollection = new ParameterCollection();
		 outputCollection.addOutput(OracleTypes.VARCHAR);
         
         try {
    		 con = JdbcUtilitario.getInstance().getConnection();
             object = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_LOGIN.concat(".SP_VALIDAR_USUARIO"), inputCollection, outputCollection);
             if (object != null) {
                 codigo = String.valueOf(object[0]);  
                 log.info("[validarUsuario] codigo:::: " +codigo);
             }

         } catch (Exception e) { 
        	 codigo="0";
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
         
         log.info("[validarUsuario] FIN");
	     return codigo;     
     }
	 
	 

	@SuppressWarnings("unchecked")
	public static List<DynaBean> ObtenerUsuario(UsuarioVO usuarioVO) throws Exception
	 {     
	     List<DynaBean> lista = null;
		 Connection con = JdbcUtilitario.getInstance().getConnection();
		 ParameterCollection inputCollection = new ParameterCollection();
         inputCollection.addInput(usuarioVO.getUsuario(),OracleTypes.VARCHAR);
         inputCollection.addInput(usuarioVO.getPassword(),OracleTypes.VARCHAR);

         try
         {
             con= JdbcUtilitario.getInstance().getConnection();
             lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_LOGIN.concat(".SP_OBTENER_USUARIO"), inputCollection);     
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

	  /**
	   * Metodo que se encarga de registrar un usuario
	   * @param UsuarioVO usuario
	   * @return String
	   * @throws Exception
	   */
	  public static String insertar(UsuarioVO usuario) throws Exception {       
	    Object[] objectL = new Object[1];
	    String codigo = "";
	    Connection con = null;
	    ParameterCollection inputCollection = new ParameterCollection();
	    inputCollection.addInput(usuario.getUsuario(),OracleTypes.VARCHAR);
	    inputCollection.addInput(usuario.getPassword(),OracleTypes.VARCHAR);
	    inputCollection.addInput(usuario.getNombre(),OracleTypes.VARCHAR);
	    ParameterCollection outputCollection = new ParameterCollection();
	    outputCollection.addOutput(OracleTypes.VARCHAR);
	    try {
	      con = JdbcUtilitario.getInstance().getConnection();
	      objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_LOGIN.concat(".SP_INSERTAR_USUARIO"), inputCollection, outputCollection);
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
