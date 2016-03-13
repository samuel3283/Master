package pe.com.nextel.provisioning.model.bo;

import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.util.Cifrado;
import pe.com.nextel.provisioning.model.dao.UsuarioDAO;
import pe.com.nextel.provisioning.model.vo.UsuarioVO;

public class LoginBO {

	 private static final Log log = LogFactory.getLog(LoginBO.class);    
	 private static LoginBO single = null;
	 
	 public LoginBO() {
	 }
	    
	 public static LoginBO getInstance() {
	    if (single == null)
	        single = new LoginBO();
	    return single;
	 }
	 
	 public String validarUsuario(UsuarioVO usuarioVO) {
		 log.info("[validarUsuario] INICIO");
		 	String cantidadRegistros = "0";
		    try {
			    //Cifrado
			    String claveE =Cifrado.encrypt(usuarioVO.getPassword());		    	
			    log.info("Password "+ usuarioVO.getPassword());    
			    log.info("ClaveE "+ claveE);
			    //Descifrado
			    String claveD =Cifrado.decrypt(claveE);		    	
			    log.info("claveD "+ claveD);   
			    
			    usuarioVO.setPassword(claveE);
				cantidadRegistros = UsuarioDAO.validarUsuario(usuarioVO);
				log.info(" => LoginBO validarUsuario ::: Usuario: "+usuarioVO.getUsuario()+"  VALIDA:: "+cantidadRegistros);
		    }catch(Exception e) {
				cantidadRegistros="0";
		    	log.info(e.getMessage());
		    }
		    
		    log.info("[validarUsuario] FIN");
		    return cantidadRegistros;
     }
	 
	 public UsuarioVO ObtenerUsuario(UsuarioVO usuarioVO) throws DAOException 
	 {       
		 	log.info(" => LoginBO ObtenerUsuario ::: Usuario: "+usuarioVO.getUsuario());
		 	List<DynaBean> lista = null;   
		 	try {
		 	lista = UsuarioDAO.ObtenerUsuario(usuarioVO);
		 	if(lista.size()>0) {
		 		
			DynaBean dyna = (DynaBean)lista.get(0);
		 	usuarioVO.setNombre(String.valueOf(dyna.get("NOMBRE")));
		 	usuarioVO.setPassword(String.valueOf(dyna.get("PASSWORD")));
		 	usuarioVO.setUsuario(String.valueOf(dyna.get("USUARIO")));
		 	}
		 	else {
		 		usuarioVO = new UsuarioVO();
		 	}
		 	}catch(Exception e)
            {
                  e.printStackTrace();
                  throw new DAOException(e.getMessage(),e);
            }
		 	return usuarioVO;      
     }	 	 	 
 
}
