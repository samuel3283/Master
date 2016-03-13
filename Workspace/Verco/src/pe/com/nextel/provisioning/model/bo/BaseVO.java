package pe.com.nextel.provisioning.model.bo;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.dao.LogDAO;
import pe.com.nextel.provisioning.model.vo.LogVO;

public class BaseVO {
	public BaseVO(){}

	protected static void getError(String cod, String desc ) throws DAOException{
		LogVO beanE = new LogVO() ;
        beanE.setDescripcion(desc) ;
        beanE.setIdError(cod) ;
        LogDAO.insertarLog(beanE) ;
	}
}
