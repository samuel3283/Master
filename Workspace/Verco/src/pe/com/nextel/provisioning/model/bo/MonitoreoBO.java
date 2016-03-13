package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.exception.EmailException;
import pe.com.nextel.provisioning.framework.util.Utilitarios;
import pe.com.nextel.provisioning.framework.util.format.DateFormatter;
import pe.com.nextel.provisioning.framework.util.jmail.Email;
import pe.com.nextel.provisioning.model.dao.ContactoDAO;
import pe.com.nextel.provisioning.model.dao.TRKDAO;
import pe.com.nextel.provisioning.model.vo.ContactoVO;
import pe.com.nextel.provisioning.model.vo.FiltroVO;
import pe.com.nextel.provisioning.model.vo.TRKVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class MonitoreoBO {
  
  private static final Log log = LogFactory.getLog(MonitoreoBO.class);
  private static MonitoreoBO single = null;

  public MonitoreoBO() {
  }

  public static MonitoreoBO getInstance() {
    if (single == null)
      single = new MonitoreoBO();
    return single;
  }
  
  public void ejecutarJobMonitoreoTRK() {
    String resultado = ValueConstants.VALOR_OK;
    String barraEnBlanco="                    ";
    log.info("Entro a ejecutarJobMonitoreoTRK ---> " + resultado);
    Map<String,Object> mapaResultado = null;
    StringBuffer cuerpoMensaje = null;
    List<TRKVO> listaErrores = null;
    TRKVO trkvo = null;
    Email mail = null;
    try {
      mail = new Email();
      mapaResultado = this.consultarTRKconNULL();
      if (mapaResultado!=null && mapaResultado.get("resultado")!=null) {
        listaErrores = (ArrayList<TRKVO>)mapaResultado.get("listaResultado");
        cuerpoMensaje = new StringBuffer("Se ha detectado un total de ")
        .append(mapaResultado.get("cantidad").toString())
        .append(" registro(s) con respuesta soap diferente de ack en los ultimos 50 minutos. \n")
        .append("El detalle de cada uno de ellos es el siguiente: \n")
        .append("\n")
        .append("TRK          ")
        .append("Numero de Solicitud ")
        .append("Tipo de Mensaje ")
        .append("Remitente    ")
        .append("Destinatario ")
        .append("Fecha/Hora Registro   ")
        .append("Respuesta SOAP      ")
        .append("\n");
        
        for (int i=0;i<listaErrores.size();i++) {
          trkvo = listaErrores.get(i);//.substring(342, 17)
          cuerpoMensaje.append(trkvo.getTrk()).append(barraEnBlanco.substring(0,5))
          .append(trkvo.getMensaje().substring(343, 358)).append(barraEnBlanco.substring(0,5))
          .append(trkvo.getTipo_mensaje()).append(barraEnBlanco.substring(0,14))
          .append(trkvo.getRemitente()).append(barraEnBlanco.substring(0,11))
          .append(trkvo.getDestinatario()).append(barraEnBlanco.substring(0,11))
          .append(trkvo.getFecha_insert()).append(barraEnBlanco.substring(0,3))
          .append(trkvo.getResp_soap())
          .append("\n");
        }
        cuerpoMensaje.append("\n");
        cuerpoMensaje.append("Accion ha tomar por parte del personal de Nivel 1: ")
        .append("\n")
        .append("Ingrese a la consola de Portaflow y mediante la opcion de Gestion de Incidencias ")
        .append("busque por el Numero de Solicitud el mensaje en cuestion, en caso si aparezca dicho ")
        .append("mensaje, dé por cerrada la incidencia. En caso no lo encuentre notifique a personal ")
        .append("de Nivel 2 para que ejecute el siguiente paso.")
        .append("\n")
        .append("\n")
        .append("Accion ha tomar SOLO por parte del personal de Nivel 2: ")
        .append("\n")
        .append("Ingrese a la consola de XCEWSMULTI de Portaflow y busque cada uno de los registros ")
        .append("por el codigo TRK, una vez encontrado vuelva ha reenviarlo y verifique luego de ")
        .append("unos segundos que el mensaje ya tenga como respuesta soap un ack. Si fue asi, ")
        .append("verifique en la consola de Portaflow, por el Numero de Solicitud, que el mensaje ")
        .append("ya se encuentre registrado.");
        
        ContactoBO.getInstance().notificarContactosTRK(mail, "Error SOAP en TRK de Portaflow - XCEWSMULTI", 
        cuerpoMensaje.toString());
      } else {
        log.info("No se encontró respuestas diferentes de ack en trk");
      }
    } catch (DAOException de) {
      log.info("Ocurrio un error en la BD");
    } catch (EmailException ee) {
      log.info("Ocurrio un error en el envio del mail");
    } catch (Exception e) {
      e.printStackTrace();
      log.info("Ocurrio un error tecnico");
    } 
  }
  
  public Map<String,Object> consultarTRKconNULL() throws DAOException{

    List<TRKVO> lista = null;   
    Map<String,Object> mapaResultado = new HashMap<String,Object>();

    try {
      lista = TRKDAO.consultarTRKconNULL();
      
      if (lista.size()>0) {
        mapaResultado.put("resultado", "OK");
        mapaResultado.put("cantidad", String.valueOf(lista.size()));
        mapaResultado.put("listaResultado", lista);
      }
      
    }catch(Exception e){
      //getError("",e.getMessage()) ;
      e.printStackTrace();
      throw new DAOException(e.getMessage(),e);
    }

    return mapaResultado;
  }

  public void ejecutarJobMonitoreoTRKNoProceso () {	    
	    String barraEnBlanco="                    ";
	    log.info("Consulta Validacion No Procesados---> " );	    
	    String cuerpoMensaje = null;    
	    List<TRKVO> listaNoProcesados = null;
	    TRKVO trkvo = null;
	    Email mail = null;
	    try {
	      mail = new Email();
	      listaNoProcesados= (ArrayList<TRKVO>)TRKDAO.consultarTRKNoProcesados();
	      if (listaNoProcesados.size()>0) {	    	  
	        cuerpoMensaje = "Se ha detectado un total de "+listaNoProcesados.size();	        
	        cuerpoMensaje+=(" registro(s) que no han sido procesados por PortaFlow en los ultimos 50 minutos. \n");
	        cuerpoMensaje+=("El detalle de cada uno de ellos es el siguiente: \n");
	        cuerpoMensaje+=("\n");
	        cuerpoMensaje+=("TRK	Tipo de Mensaje	Fecha/Hora Registro	Remitente	Destinatario	Respuesta SOAP");        
	        cuerpoMensaje+=("\n");
	        
	        for (int i=0;i<listaNoProcesados.size();i++) {
	          trkvo = listaNoProcesados.get(i);
	          cuerpoMensaje+=trkvo.getTrk()+"	"+trkvo.getTipo_mensaje()+"	"+
	        		  		  trkvo.getFecha_insert()+"	"+trkvo.getRemitente()+"	"+
	        		  		  trkvo.getDestinatario()+"	"+trkvo.getResp_soap();	          
	          cuerpoMensaje+=("\n");
	        }	        
	        cuerpoMensaje+=("\n");
	        cuerpoMensaje+=("Accion ha tomar SOLO por parte del personal de Nivel 2: ");
	        cuerpoMensaje+=("\n");
	        cuerpoMensaje+=("Ingrese a la consola de XCEWSMULTI de Portaflow y busque cada uno de los registros ");
	        cuerpoMensaje+=("por el codigo TRK, una vez encontrado vuelva ha reenviarlo y verifique luego de ");
	        cuerpoMensaje+=("unos segundos que el mensaje ya tenga como respuesta soap un ack. Si fue asi, ");
	        cuerpoMensaje+=("verifique en la consola de Portaflow, por el Numero de Solicitud, que el mensaje ");
	        cuerpoMensaje+=("ya se encuentre registrado.");	     
	        ContactoBO.getInstance().notificarContactosTRK(mail, "Error en Procesamiento de Mensajes Portaflow - XCEWSMULTI", 
	        cuerpoMensaje.toString());
	      } else {log.info("No se encontró respuestas No Procesados");}	   
	    } catch (EmailException ee) {
	      log.info("Ocurrio un error en el envio del mail");
	    } catch (Exception e) {
	      e.printStackTrace();
	      log.info("Ocurrio un error tecnico");
	    } 
	  }
  
}
