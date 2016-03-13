package pe.com.nextel.provisioning.framework.util.portabilidad;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.model.vo.ArchivoFPVO;
import pe.com.nextel.provisioning.model.vo.ArchivoFRVO;
import pe.com.nextel.provisioning.model.vo.MensajeVO;
import pe.com.nextel.provisioning.model.vo.MensajeWSVO;
import pe.com.nextel.provisioning.view.ValueConstants;

/**
 * <p>Title: Reglas de Portabilidad</p>
 * <p>Description: Clase encargada del flujo que define las reglas de portabilidad.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : ReglasPortabilidad</p>
 * <p>Fecha       : 24 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  FRANK PICOY (COMSA)
 * @version 1.0
 */

public class ReglasPortabilidad {
  private static final Log log = LogFactory.getLog(ReglasPortabilidad.class);    
  private static ReglasPortabilidad single = null;

  public static final String CODIGO_RECEPTOR_NEXTEL_3G="20";
  public static final String CODIGO_RECEPTOR_NEXTEL_IDEN="27";
  public static final String CODIGO_PROCESO_PORTABILIDAD="01";
  public static final String CODIGO_PROCESO_RETORNO_POR_FRAUDE="01";
  public static final String CODIGO_PROCESO_RETORNO_POR_EXPCONTRATO="02";
  public static final String METODO_WS_ADD="ADD_USR";
  public static final String METODO_WS_REMOVE="RMV_USR";
  public static final String METODO_WS_ERROR="ERR_USR";

  public static final String NUMBER_TYPE_UNO="1";
  public static final String NUMBER_TYPE_DOS="2";
  public static final String NUMBER_TYPE_TRES="3";
  public static final String HRLADDRESS_IDEN="5118100003";
  public static final String HRLADDRESS_3G="511981500006";

  public MensajeWSVO mensajeWS=null;

  /**
   * Constructor de la clase
   */
  public ReglasPortabilidad(){
    mensajeWS= new MensajeWSVO();	
  }

  public static ReglasPortabilidad getInstance() {
    if (single == null)
      single = new ReglasPortabilidad();
    return single;
  }

  /**
   * Metodo que setea los datos del bean mensajeWS de acuerdo al flujo definido por el Proceso
   * de Portabilidad 
   * @param String asignatario
   * @param String receptor
   * @param String number
   * @param String username
   * @param String password
   */
  public MensajeVO asignarDatosProcesoPortabilidad (ArchivoFPVO archivoFPVO, Map map) {

    MensajeVO mensajeVO = new MensajeVO();
    
    if ((archivoFPVO.getCedente().equals(CODIGO_RECEPTOR_NEXTEL_3G) && 
      archivoFPVO.getReceptor().equals(CODIGO_RECEPTOR_NEXTEL_IDEN)) || 
      (archivoFPVO.getCedente().equals(CODIGO_RECEPTOR_NEXTEL_IDEN) && 
      archivoFPVO.getReceptor().equals(CODIGO_RECEPTOR_NEXTEL_3G))) {
      mensajeVO.setComando(METODO_WS_ERROR);
    } else {

      if (archivoFPVO.getAsignatario().equals(archivoFPVO.getReceptor())){
        mensajeVO.setComando(METODO_WS_REMOVE);
        mensajeVO.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
        mensajeVO.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));
        mensajeVO.setNumber(archivoFPVO.getWorldNumber());
  
  
      } else {
        if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(archivoFPVO.getReceptor()) || CODIGO_RECEPTOR_NEXTEL_3G.equals(archivoFPVO.getReceptor())) {
          mensajeVO.setComando(METODO_WS_ADD);
          mensajeVO.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
          mensajeVO.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));
          mensajeVO.setNumber(archivoFPVO.getWorldNumber());
  
          if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(archivoFPVO.getReceptor())){
            mensajeVO.setHLRAddress(HRLADDRESS_IDEN);
          }
          if (CODIGO_RECEPTOR_NEXTEL_3G.equals(archivoFPVO.getReceptor())){
            mensajeVO.setHLRAddress(HRLADDRESS_3G);
          }
          mensajeVO.setNumberType(NUMBER_TYPE_DOS);
        } else {
          if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(archivoFPVO.getAsignatario()) || CODIGO_RECEPTOR_NEXTEL_3G.equals(archivoFPVO.getAsignatario())) {
            mensajeVO.setComando(METODO_WS_ADD);
            mensajeVO.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
            mensajeVO.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));
  
            mensajeVO.setNumber(archivoFPVO.getWorldNumber());
            mensajeVO.setNumberType(NUMBER_TYPE_UNO);
          }
          if (!CODIGO_RECEPTOR_NEXTEL_IDEN.equals(archivoFPVO.getAsignatario()) && !CODIGO_RECEPTOR_NEXTEL_3G.equals(archivoFPVO.getAsignatario())) {
            mensajeVO.setComando(METODO_WS_ADD);
            mensajeVO.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
            mensajeVO.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));
  
            mensajeVO.setNumber(archivoFPVO.getWorldNumber());
            mensajeVO.setNumberType(NUMBER_TYPE_TRES);
          }
        }
        mensajeVO.setNewRoute(archivoFPVO.getReceptor());
      }
    }
    return mensajeVO;
  }



  /**
   * Metodo que setea los datos del bean mensajeWS de acuerdo al flujo definido por el Proceso
   * de Retorno 
   * @param String asignatario
   * @param String cedente inicial
   * @param String tipoRetorno
   * @param String number
   * @param String username
   * @param String password
   */
  public MensajeVO asignarDatosProcesoRetorno (ArchivoFRVO archivoFRVO, Map map) {

    MensajeVO mensajeVO = new MensajeVO();

    if (archivoFRVO.getMotivoRetorno().equals(CODIGO_PROCESO_RETORNO_POR_EXPCONTRATO)){
      mensajeVO.setComando(METODO_WS_REMOVE);
      mensajeVO.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
      mensajeVO.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));
      mensajeVO.setNumber(archivoFRVO.getWorldNumber());
    } else {
      if (archivoFRVO.getAsignatario().equals(archivoFRVO.getCedenteInicial())) {
        mensajeVO.setComando(METODO_WS_REMOVE);
        mensajeVO.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
        mensajeVO.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));
        mensajeVO.setNumber(archivoFRVO.getWorldNumber());
      } else {
        if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(archivoFRVO.getCedenteInicial()) || CODIGO_RECEPTOR_NEXTEL_3G.equals(archivoFRVO.getCedenteInicial())) {
          mensajeVO.setComando(METODO_WS_ADD);
          mensajeVO.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
          mensajeVO.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));
          mensajeVO.setNumber(archivoFRVO.getWorldNumber());
          if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(archivoFRVO.getCedenteInicial())){
            mensajeVO.setHLRAddress(HRLADDRESS_IDEN);
          }
          if (CODIGO_RECEPTOR_NEXTEL_3G.equals(archivoFRVO.getCedenteInicial())){
            mensajeVO.setHLRAddress(HRLADDRESS_3G);
          }
          mensajeVO.setNumberType(NUMBER_TYPE_DOS);
        } else {
          if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(archivoFRVO.getAsignatario()) || CODIGO_RECEPTOR_NEXTEL_3G.equals(archivoFRVO.getAsignatario())) {
            mensajeVO.setComando(METODO_WS_ADD);
            mensajeVO.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
            mensajeVO.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));

            mensajeVO.setNumber(archivoFRVO.getWorldNumber());
            mensajeVO.setNumberType(NUMBER_TYPE_UNO);
          }
          if (!CODIGO_RECEPTOR_NEXTEL_IDEN.equals(archivoFRVO.getAsignatario()) || !CODIGO_RECEPTOR_NEXTEL_3G.equals(archivoFRVO.getAsignatario())) {
            mensajeVO.setComando(METODO_WS_ADD);
            mensajeVO.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
            mensajeVO.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));

            mensajeVO.setNumber(archivoFRVO.getWorldNumber());
            mensajeVO.setNumberType(NUMBER_TYPE_TRES);
          }	
        }
        mensajeVO.setNewRoute(archivoFRVO.getReceptor());
      }
    }
    return mensajeVO;
  }




  /**
   * Metodo que setea los datos del bean mensajeWS de acuerdo al flujo definido por el Proceso
   * de Portabilidad 
   * @param String asignatario
   * @param String receptor
   * @param String number
   * @param String username
   * @param String password
   */
  public void asignarDatosProcesoPortabilidad (String asignatario, String receptor, String number, String username, String password) {
    if (asignatario.equals(receptor)){
      mensajeWS.setMetodoWS(METODO_WS_REMOVE);
      mensajeWS.setUsername(username);
      mensajeWS.setPassword(password);
      mensajeWS.setNumber(number);
    } else {
      if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(receptor) || CODIGO_RECEPTOR_NEXTEL_3G.equals(receptor)) {
        mensajeWS.setMetodoWS(METODO_WS_ADD);
        mensajeWS.setUsername(username);
        mensajeWS.setPassword(password);
        mensajeWS.setNumber(number);
        if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(receptor)){
          mensajeWS.setHrlAddress("5118100003");
        }
        if (CODIGO_RECEPTOR_NEXTEL_3G.equals(receptor)){
          mensajeWS.setHrlAddress("511981500006");
        }
        mensajeWS.setNumberType("2");
      } else {
        if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(asignatario) || CODIGO_RECEPTOR_NEXTEL_3G.equals(asignatario)) {
          mensajeWS.setMetodoWS(METODO_WS_ADD);
          mensajeWS.setUsername(username);
          mensajeWS.setPassword(password);
          mensajeWS.setNumber(number);
          mensajeWS.setNumberType("1");
        }
        if (!CODIGO_RECEPTOR_NEXTEL_IDEN.equals(asignatario) || !CODIGO_RECEPTOR_NEXTEL_3G.equals(asignatario)) {
          mensajeWS.setMetodoWS(METODO_WS_ADD);
          mensajeWS.setUsername(username);
          mensajeWS.setPassword(password);
          mensajeWS.setNumber(number);
          mensajeWS.setNumberType("3");
        }
      }
    }
  }

  /**
   * Metodo que setea los datos del bean mensajeWS de acuerdo al flujo definido por el Proceso
   * de Retorno 
   * @param String asignatario
   * @param String previo
   * @param String tipoRetorno
   * @param String number
   * @param String username
   * @param String password
   */
  public void asignarDatosProcesoRetorno (String asignatario, String previo, String tipoRetorno, String number, String username, String password) {

    if (tipoRetorno.equals(CODIGO_PROCESO_RETORNO_POR_EXPCONTRATO)){
      mensajeWS.setMetodoWS(METODO_WS_REMOVE);
      mensajeWS.setUsername(username);
      mensajeWS.setPassword(password);
      mensajeWS.setNumber(number);
    } else {
      if (asignatario.equals(previo)) {
        mensajeWS.setMetodoWS(METODO_WS_REMOVE);
        mensajeWS.setUsername(username);
        mensajeWS.setPassword(password);
        mensajeWS.setNumber(number);
      } else {
        if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(previo) || CODIGO_RECEPTOR_NEXTEL_3G.equals(previo)) {
          mensajeWS.setMetodoWS(METODO_WS_ADD);
          mensajeWS.setUsername(username);
          mensajeWS.setPassword(password);
          mensajeWS.setNumber(number);
          if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(previo)){
            mensajeWS.setHrlAddress("5118100003");
          }
          if (CODIGO_RECEPTOR_NEXTEL_3G.equals(previo)){
            mensajeWS.setHrlAddress("511981500006");
          }
          mensajeWS.setNumberType("2");
        } else {
          if (CODIGO_RECEPTOR_NEXTEL_IDEN.equals(asignatario) || CODIGO_RECEPTOR_NEXTEL_3G.equals(asignatario)) {
            mensajeWS.setMetodoWS(METODO_WS_ADD);
            mensajeWS.setUsername(username);
            mensajeWS.setPassword(password);
            mensajeWS.setNumber(number);
            mensajeWS.setNumberType("1");
          }
          if (!CODIGO_RECEPTOR_NEXTEL_IDEN.equals(asignatario) || !CODIGO_RECEPTOR_NEXTEL_3G.equals(asignatario)) {
            mensajeWS.setMetodoWS(METODO_WS_ADD);
            mensajeWS.setUsername(username);
            mensajeWS.setPassword(password);
            mensajeWS.setNumber(number);
            mensajeWS.setNumberType("3");
          }	
        }
      }
    }
  }
}
