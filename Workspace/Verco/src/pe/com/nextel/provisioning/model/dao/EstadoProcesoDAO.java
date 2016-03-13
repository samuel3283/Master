package pe.com.nextel.provisioning.model.dao;

import java.sql.Connection;
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
import pe.com.nextel.provisioning.view.ValueConstants;

public class EstadoProcesoDAO {
	 private static final Log log = LogFactory.getLog(EstadoProcesoDAO.class);    
		
	 public EstadoProcesoDAO() 
	 {
	 } 
	 
	 @SuppressWarnings("unchecked")
	public static List<DynaBean> consultarListaProcesos(FiltroVO filtro) throws Exception
	 {    
		List<DynaBean> lista = null;
		Connection con = null;
		
		ParameterCollection inputCollection = new ParameterCollection();
		inputCollection.addInput(filtro.getCaso(),OracleTypes.VARCHAR);	//caso
		inputCollection.addInput(filtro.getNombre(),OracleTypes.VARCHAR);	//valor
		
        try {
        	con = JdbcUtilitario.getInstance().getConnection();
        	lista = QueryUtil.executeProcedure(con,ValueConstants.PK_PROVISIONING_RF.concat(".SP_CONSULTAR_PROCESO"), inputCollection);

        }catch(Exception e)
		{
		     e.printStackTrace();
		     log.error(e);
		     throw new DAOException(e.getMessage(),e);
		}
		
		return lista;
	 }	 
	 		 
}
