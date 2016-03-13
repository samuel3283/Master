package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.util.Utilitarios;
import pe.com.nextel.provisioning.framework.util.format.DateFormatter;
import pe.com.nextel.provisioning.framework.util.jmail.Email;
import pe.com.nextel.provisioning.model.dao.ContactoDAO;
import pe.com.nextel.provisioning.model.dao.ErroresDAO;
import pe.com.nextel.provisioning.model.vo.ContactoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;

/**
 * COMSA		: provisioning
 * @date		: 15/12/2009
 * @time		: 10:40:00 AM
 * @author		: Walter P Rodriguez Silupú.
 * @descripcion	: Clase BO para mantenimiento de contactos.
 */
public class ContactoBO extends BaseVO{
  private static final Log log = LogFactory.getLog(ContactoBO.class);    
  private static ContactoBO single = null;

  public ContactoBO() {
  }

  public static ContactoBO getInstance() {
    if (single == null)
      single = new ContactoBO();
    return single;
  }	

  public String insertar(ContactoVO contacto) throws Exception
  {    
    String valor="";

    log.info("[ContactoBO insertar] ==> Nombre "+ contacto.getNombre() +
        " ==> Email "+ contacto.getEmail() +			
        " ==>Celular "+ contacto.getCelular() +
        " ==>Sms "+ contacto.getSms() +
        " ==>TipoContacto "+ contacto.getTipoContacto());

    try {
      valor = ContactoDAO.insertar(contacto);

    }catch(Exception e)
    {
      getError("",e.getMessage()) ;
      e.printStackTrace();
      throw new DAOException(e.getMessage(),e);
    }

    return valor;
  }	

  public String modificar(ContactoVO contacto) throws Exception
  {    
    String valor="";

    log.info("[ContactoBO modificar] ==> IdContacto "+ contacto.getIdContacto() +
        " ==> Nombre "+ contacto.getNombre() +
        " ==> Email "+ contacto.getEmail() +		
        " ==> Celular "+ contacto.getCelular() +
        " ==> Sms "+ contacto.getSms() +
        " ==> TipoContacto "+ contacto.getTipoContacto());

    try {
      valor = ContactoDAO.modificar(contacto);

    }catch(Exception e)
    {
      getError("",e.getMessage()) ;
      e.printStackTrace();
      throw new DAOException(e.getMessage(),e);
    }
    return valor;
  }	


  public String eliminar(ContactoVO contacto) throws Exception
  {    
    String valor="";

    log.info("[ContactoBO eliminar] ==> IdContacto "+ contacto.getIdContacto());

    try {
      valor = ContactoDAO.eliminar(contacto);

    }catch(Exception e)
    {
      getError("",e.getMessage()) ;
      e.printStackTrace();
      throw new DAOException(e.getMessage(),e);
    }

    return valor;
  }		

  public List<ContactoVO> consultar(FiltroVO filtro) throws DAOException{

    List<ContactoVO> listacontactos = new ArrayList<ContactoVO>();	
    List<DynaBean> lista = null;   
    ContactoVO contacto = new ContactoVO();

    log.info("[ContactoBO consultar] ==> Caso "+ filtro.getCaso()+
        " ==>Codigo "+ filtro.getCodigo()+
        " ==>Nombre "+ filtro.getNombre());	

    try {
      lista = ContactoDAO.consultar(filtro);
      for (int i = 0 ; i < lista.size() ; i++ ){
        DynaBean dyna = (DynaBean)lista.get(i);
        contacto = new ContactoVO();
        contacto.setIdContacto( Integer.parseInt((String)dyna.get("IDCONTACTO"))) ;
        contacto.setNombre( (String)dyna.get("NOMBRE")) ; 
        contacto.setEmail((String)dyna.get("EMAIL")) ;
        contacto.setCelular((String)dyna.get("CELULAR")) ;
        contacto.setSms((String)dyna.get("SMS")) ;
        contacto.setTipoContacto((String)dyna.get("TIPOCONTACTO")) ;
        contacto.setFechaModificacion((String)dyna.get("FECHAMODIFICACION")!=null?(((String)dyna.get("FECHAMODIFICACION")).equals("")?null:Utilitarios.stringToDate((String)dyna.get("FECHAMODIFICACION"),DateFormatter.dateDataBasee)):null);
        listacontactos.add(contacto);
      }
    }catch(Exception e){
      getError("",e.getMessage()) ;
      e.printStackTrace();
      throw new DAOException(e.getMessage(),e);
    }

    return listacontactos;
  }
  
  public void notificarContactos(Email mail, String asunto, String mensaje) {
    log.info("--------------->Entro a Notificar por correo");
    FiltroVO filtro = new FiltroVO();
    ContactoVO contacto = null;
    String mensajeError="";
    String mensajeDecripcion="";
    String mensajeExcepcion="";
    List<ContactoVO> listaContactos = null;
    filtro.setCaso("1");
    try {
      listaContactos = this.consultar(filtro);
      log.info("--------------->Se envia correo a -" + listaContactos.size());
      log.info("EL CODIGO DE ERROR RECIBIDO ES-" + mensaje + "-");
      if ("".equals(mensaje)){
        mensaje = "ERRTEC0000";
      }
      if (mensaje.length()>10) {
        mensajeExcepcion = mensaje.substring(10);
      }
      log.info("La excepcion es-" + mensajeExcepcion + "-");
      ErroresDAO errores = new ErroresDAO();
      log.info("mensaje de error es:" + mensaje.substring(0,10) + "==>");
      mensajeDecripcion = errores.obtenerDescripcionError(mensaje.substring(0,10));
      log.info("mensajeDecripcion es:" + mensajeDecripcion + "==>");
      mensajeError = "Ha ocurrido un error del tipo ".concat(mensaje.substring(0,10))
      .concat("\n").concat(mensajeDecripcion.trim());
      log.info("mensajeError es::" + mensajeError);
      if (!"".equals(mensajeExcepcion.trim())) {
        mensajeError = mensajeError.concat(".\nLa Excepcion arrojada es: ").concat(mensajeExcepcion);
      }
      
      for (int i=0;i<listaContactos.size();i++) {
        contacto = listaContactos.get(i);
        if ("0".equals(contacto.getTipoContacto())) {
         log.info(" ==> EMAIL: "+contacto.getEmail()+" ==> Asunto: "+asunto+" ==> mje: "+mensajeError);
          mail.sendMail(contacto.getEmail(), asunto, mensajeError);
          log.info(" ==> Sns: "+contacto.getEmail()+" ==> Asunto: "+asunto+" ==> mje: "+mensajeError);
          mail.sendMail(contacto.getSms(), asunto, mensajeError);
        } else if ("1".equals(contacto.getTipoContacto())) {
          log.info(" ==> EMAIL: "+contacto.getEmail()+" ==> Asunto: "+asunto+" ==> mje: "+mensajeError);
          mail.sendMail(contacto.getEmail(), asunto, mensajeError);
        } else if ("2".equals(contacto.getTipoContacto())) {
          log.info(" ==> Sns: "+contacto.getEmail()+" ==> Asunto: "+asunto+" ==> mje: "+mensajeError);
          mail.sendMail(contacto.getSms(), asunto, mensajeError);
        }
      }
    } catch (DAOException e) {
      
    } catch (Exception e) {
      
    }
  }
  
  public void notificarContactosOK(Email mail, String asunto, String mensaje) {
    log.info("--------------->Entro a Notificar por correo");
    FiltroVO filtro = new FiltroVO();
    ContactoVO contacto = null;
    List<ContactoVO> listaContactos = null;
    filtro.setCaso("1");
    try {
      listaContactos = this.consultar(filtro);
      log.info("--------------->Se envia correo a -" + listaContactos.size());
      log.info("EL MENSAJE ES-" + mensaje + "-");
      
      for (int i=0;i<listaContactos.size();i++) {
        contacto = listaContactos.get(i);
        if ("0".equals(contacto.getTipoContacto())) {
         log.info(" ==> EMAIL: "+contacto.getEmail()+" ==> Asunto: "+asunto+" ==> mje: "+mensaje);
          mail.sendMail(contacto.getEmail(), asunto, mensaje);
          log.info(" ==> Sns: "+contacto.getEmail()+" ==> Asunto: "+asunto+" ==> mje: "+mensaje);
          mail.sendMail(contacto.getSms(), asunto, mensaje);
        } else if ("1".equals(contacto.getTipoContacto())) {
          log.info(" ==> EMAIL: "+contacto.getEmail()+" ==> Asunto: "+asunto+" ==> mje: "+mensaje);
          mail.sendMail(contacto.getEmail(), asunto, mensaje);
        } else if ("2".equals(contacto.getTipoContacto())) {
          log.info(" ==> Sns: "+contacto.getEmail()+" ==> Asunto: "+asunto+" ==> mje: "+mensaje);
          mail.sendMail(contacto.getSms(), asunto, mensaje);
        }
      }
    } catch (DAOException e) {
      
    } catch (Exception e) {
      
    }
  }
  
  
  public void notificarContactosTRK(Email mail, String asunto, String mensaje) {
	    log.info("--------------->Entro a Notificar por correo");
	    //mail.sendMail("centroatencionportabilidad@comsa.com.pe", asunto, mensaje);
	    mail.sendMail("snavarro@comsa.com.pe,fjulcamanyan@comsa.com.pe,manaya@comsa.com.pe", asunto, mensaje);	  		 
  }

}
