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
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.model.vo.ParametrosVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class ParametrosDAO {
	 private static final Log log = LogFactory.getLog(ParametrosDAO.class);    
		
	 public ParametrosDAO() 
	 {
	 } 
	 
	 @SuppressWarnings("unchecked")
	public static List<DynaBean> consultar(FiltroVO filtro) throws Exception
	 {    
		List<DynaBean> lista = null;
		Connection con = null;
		
		ParameterCollection inputCollection = new ParameterCollection();
		inputCollection.addInput(filtro.getCaso(),OracleTypes.VARCHAR);	//caso
		inputCollection.addInput(filtro.getCodigo(),OracleTypes.VARCHAR);	//grupo	
		inputCollection.addInput(filtro.getNombre(),OracleTypes.VARCHAR);	//item
		
        try {
        	con = JdbcUtilitario.getInstance().getConnection();
        	lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_RF.concat(".SP_CONSULTAR_PARAMETRO"), inputCollection);

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
	 
		public static String consultarParametro(String nombreCorto) throws Exception
		 {    
			List<DynaBean> lista = null;
			String valor="";
			
			FiltroVO filtro = new FiltroVO();
			filtro.setCaso("2");
			filtro.setCodigo("");
			filtro.setNombre(nombreCorto);
			
	        try {
	        	lista = consultar(filtro);
	        	DynaBean dyna = (DynaBean)lista.get(0);
	        	valor = (String)dyna.get("VALOR") ;

	        }catch(Exception e)
			{
			     e.printStackTrace();
			     log.error(e);
			     throw new DAOException(e.getMessage(),e);
			}
			
			return valor;
		 }		 
	 
	 public static String insertar(ParametrosVO parametro) throws Exception{       

			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			
			ParameterCollection inputCollection = new ParameterCollection();
			inputCollection.addInput(parametro.getCodigo(),OracleTypes.VARCHAR);
			inputCollection.addInput(parametro.getNombreCorto(),OracleTypes.VARCHAR);
			inputCollection.addInput(parametro.getNombre(),OracleTypes.VARCHAR);
			inputCollection.addInput(parametro.getValor(),OracleTypes.VARCHAR);
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
	        	con = JdbcUtilitario.getInstance().getConnection();
	        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_RF.concat(".SP_GRABAR_PARAMETRO"), inputCollection, outputCollection);
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
	 

	 public static List<DynaBean> ObtenerParametro(String valorParametro, String opcion) throws Exception
	 {     
		List<DynaBean> lista = null;
		Connection con = JdbcUtilitario.getInstance().getConnection();
		ParameterCollection inputCollection = new ParameterCollection();
	    inputCollection.addInput(valorParametro,OracleTypes.VARCHAR);
	    inputCollection.addInput(opcion,OracleTypes.VARCHAR);

	         try
	         {
	             con= JdbcUtilitario.getInstance().getConnection();
	             lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_OBTENER_PARAMETRO"), inputCollection);     
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
	 

	 

	 public static String obtenerCantidadParametros() throws Exception{
		 
			Object[] objectL = new Object[1];
			String codigo = "";
			Connection con = null;
			ParameterCollection inputCollection = new ParameterCollection();
			
			ParameterCollection outputCollection = new ParameterCollection();
			outputCollection.addOutput(OracleTypes.VARCHAR);
			
	        try {
        	con = JdbcUtilitario.getInstance().getConnection();
        	objectL = QueryUtil.executeProcedure(con, ValueConstants.PK_PROVISIONING_PROCESO.concat(".SP_LISTAR_PARAMETRO"), inputCollection, outputCollection);
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
