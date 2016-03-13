package pe.com.nextel.provisioning.model.dao;

import java.io.FileOutputStream;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitario;
import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitarioTest;
import pe.com.nextel.provisioning.model.vo.SolicitudVO;
import pe.com.nextel.provisioning.model.vo.TRKVO;

/**
 * <p>Title: Lista de Contactos</p>
 * <p>Description: Clase encargada del acceso a datos a la tabla TRKDAO.</p>
 * <p>Proyecto    : MonitoreoArchivos</p>
 * <p>Clase       : TRKDAO</p>
 * <p>Fecha       : 04 Diciembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : COM S.A.</p>
 * @author  FRANK PICOY (COMSA)
 * @version 1.0
 */

public class TRKDAO {
  private static final Log log = LogFactory.getLog(TRKDAO.class);    

  /**
   * Constructor de la clase
   */
  public TRKDAO() {
  
  } 
  
  /**
   * Metodo que se encarga de listar los trk con respuesta null
   * @return List<TRKVO>
   * @throws Exception
   */
  public static List<TRKVO> consultarTRKconNULL() /*throws Exception*/ {    
    List<TRKVO> rList = new ArrayList<TRKVO>() ;
    TRKVO bean = null;
    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet resultSet = null;
    StringBuffer sql = new StringBuffer(" SELECT trk, estado, remitente, destinatario,")
    .append(" to_char(fecha_insert,'dd/MM/yyyy HH24:mi:ss'), ")
    .append(" tipo_mensaje,resp_soap, to_char(fecha_soap,'dd/MM/yyyy HH24:mi:ss'), ")
    .append(" UTL_RAW.CAST_TO_VARCHAR2(mensaje) ")
    .append(" FROM TRK ")
    .append(" WHERE fecha_insert BETWEEN sysdate-numtodsinterval(60, 'MINUTE') ")
    .append(" AND sysdate-numtodsinterval(10, 'MINUTE') ")
    .append(" AND (resp_soap!='ack' OR resp_soap is null) ");
    
    log.info(" sql consultarTRKconNULL " + sql.toString());

    try {
      con = JdbcUtilitarioTest.getInstance().getConnection();
      stmt = con.prepareStatement(sql.toString());
      resultSet = stmt.executeQuery();
      log.info("Se ejecuto correctamente el query");
      while (resultSet.next()) {
        bean = new TRKVO();
        bean.setTrk(resultSet.getString(1));
        bean.setEstado(resultSet.getString(2));
        bean.setRemitente(resultSet.getString(3));
        bean.setDestinatario(resultSet.getString(4));
        bean.setFecha_insert(resultSet.getString(5));
        bean.setTipo_mensaje(resultSet.getString(6));
        bean.setResp_soap(resultSet.getString(7));
        bean.setFecha_soap(resultSet.getString(8));
        bean.setMensaje(resultSet.getString(9));
        rList.add(bean) ;
      }
    } catch (Exception e) { 
      log.info("Hubo un error " + e.getMessage());
    }
    finally
    {
      try
      {
        resultSet.close();
        stmt.close();
        con.close();
      }catch(SQLException e)
      {
        e.printStackTrace();
        log.error(e);
      }
    }
      
    return rList ;
  }  


  public static List consultarTRK(SolicitudVO solicitudVO) throws Exception {    
	    List rList = new ArrayList() ;
	    TRKVO bean = null;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet resultSet = null;
	    StringBuffer sql = new StringBuffer(" SELECT TRK, TIPO_MENSAJE, REMITENTE, DESTINATARIO, ")
	    .append(" TO_CHAR(FECHA_INSERT,'dd/mm/yyyy HH24:mi:ss') AS FECHA_INSERT, ")
	    .append(" TO_CHAR(FECHA_SOAP,'dd/mm/yyyy HH24:mi:ss') AS FECHA_SOAP, mensaje, RESP_SOAP")
	    .append(" FROM TRK ")
	    .append(" WHERE FECHA_INSERT BETWEEN ")
	    .append(" to_date('"+solicitudVO.getFechaRegistro()+"','dd/mm/yyyy HH24:mi:ss')-numtodsinterval(1, 'MINUTE') ")
	    .append(" AND to_date('"+solicitudVO.getFechaRegistro()+"','dd/mm/yyyy HH24:mi:ss')-numtodsinterval(0, 'MINUTE') ")
	    .append(" AND TIPO_MENSAJE in('SP','SVP','OCC','SAC','PP','NI') order by trk ");
	    
	    log.info(" sql consultarTRK: " + sql.toString());
	    String pathname= "";
	    FileOutputStream fos = null;
	    String value="";
	    try {
	      con = JdbcUtilitarioTest.getInstance().getConnection();
	      stmt = con.prepareStatement(sql.toString());
	      resultSet = stmt.executeQuery();
	      while (resultSet.next()) {
	        bean = new TRKVO();
	        bean.setTrk(resultSet.getString(1));
	        bean.setTipo_mensaje(resultSet.getString(2));
	        bean.setRemitente(resultSet.getString(3));
	        bean.setDestinatario(resultSet.getString(4));
	        bean.setFecha_insert(resultSet.getString(5));
	        bean.setFecha_soap(resultSet.getString(6));
	        //bean.setMensaje(resultSet.getString(7));        
	        //bean.setMensaje(resultSet.getBlob(7));
	        
	        Blob blob = resultSet.getBlob(7);
	        byte[] bdata = blob.getBytes(1, (int) blob.length());
	        value = new String(bdata);
	        bean.setMensaje(value);
	        
	        /*
	        int offset = -1;
	        int chunkSize = 1024;
	        long blobLength = blob.length();
	        if(chunkSize > blobLength) {
	        chunkSize = (int)blobLength;
	        }
	        char buffer[] = new char[chunkSize];
	        StringBuilder stringBuffer = new StringBuilder();
	        Reader reader = new InputStreamReader(blob.getBinaryStream());

	        while((offset = reader.read(buffer)) != -1) {
	        stringBuffer.append(buffer,0,offset);
	        }
	        value = stringBuffer.toString(); 
	        */
	        
	        log.info("value ==> " + value);
	        
	        try {
	            SAXBuilder builder=new SAXBuilder(false); 
	            //Usa parser Xerces y no queremos que valide el documento
	            Document doc=builder.build(new StringReader(value));
	            //construyo el arbol en memoria desde el fichero
	            // que se lo pasaré por parametro.
	            Element raiz=doc.getRootElement();

	            System.out.println("==>XXX<==NAME:"+raiz.getName()+"==>VALUE:"+raiz.getValue());
	            
	            List equipos=raiz.getChildren("CabeceraMensaje");
	            System.out.println("Formada por:"+equipos.size()+" equipos");
	            
	            Iterator i = equipos.iterator();
	            while (i.hasNext()){
	                Element e= (Element)i.next();
	                System.out.println("==>NAME:"+e.getChildText("IdentificadorMensaje"));
	                System.out.println("==>NAME:"+e.getChildText("Remitente"));
	                System.out.println("==>NAME:"+e.getChildText("IdentificadorProceso"));
	            }
	            
	            
	            List cuerpoMensaje=raiz.getChildren("CuerpoMensaje");
	            System.out.println("Formada por:"+equipos.size()+" CuerpoMensaje");
	            
	            Iterator j = equipos.iterator();
	            while (j.hasNext()){
	                Element e= (Element)j.next();
	                System.out.println("==>NAME:"+e.getChildText("IdentificadorMensaje"));
	                System.out.println("==>NAME:"+e.getChildText("Remitente"));
	                System.out.println("==>NAME:"+e.getChildText("IdentificadorProceso"));
	            }
	         }catch (Exception e){
	            e.printStackTrace();
	         }

	         
	        /*
	        	pathname= "C:/samuel.txt";
                File file = new File(pathname);
                fos = new FileOutputStream(file);                    
                Blob bin = resultSet.getBlob(7);
                InputStream inStream = bin.getBinaryStream();
                int size = (int)bin.length();
                byte[] buffer = new byte[size];
                int length = -1;
                while ((length = inStream.read(buffer)) != -1)
                {
                  fos.write(buffer, 0, length);                
                }  
	        */
	        bean.setResp_soap(resultSet.getString(8));
	        rList.add(bean) ;
	      }
	    } catch (Exception e) { 
	      log.info("Hubo un error " + e.getMessage());
	    }
	    finally
	    {
          try
	      { //fos.close();
	        resultSet.close();
	        stmt.close();
	        con.close();
	      }catch(SQLException e)
	      {
	        e.printStackTrace();
	        log.error(e);
	      }
	    }
	      
	    return rList ;
	  }  
  
  public static List<TRKVO> consultarTRKNoProcesados() /*throws Exception*/ {    
	    List<TRKVO> rList = new ArrayList<TRKVO>() ;
	    TRKVO bean = null;
	    Connection con = null;
	    PreparedStatement stmt = null;
	    ResultSet resultSet = null;
	    StringBuffer sql = new StringBuffer(" select xcewsmulti.trk.trk, xcewsmulti.trk.tipo_mensaje,") 
	    .append(" to_char(xcewsmulti.trk.fecha_insert,'dd/MM/yyyy HH24:mi:ss')fecha, ")
	    .append(" xcewsmulti.trk.remitente, xcewsmulti.trk.destinatario, ")
	    .append(" xcewsmulti.trk.resp_soap ")
	    .append(" from xcewsmulti.trk ")
	    .append(" where xcewsmulti.trk.fecha_insert BETWEEN sysdate-numtodsinterval(60, 'MINUTE') ")
	    .append(" AND sysdate-numtodsinterval(10, 'MINUTE') ")
	    .append(" and not exists ")
	    .append(" (select null from portaflow.cabecera_mensaje where ") 
	    .append(" portaflow.cabecera_mensaje.track_id=xcewsmulti.trk.trk) ")
	    .append(" order by xcewsmulti.trk.trk desc ");
	    
	    log.info(" sql consultarTRKNoProcesados " + sql.toString());

	    try {
	      //con = JdbcUtilitarioTest.getInstance().getConnection();
	    	con = JdbcUtilitario.getInstance().getConnection();
	      stmt = con.prepareStatement(sql.toString());
	      resultSet = stmt.executeQuery();
	      log.info("Se ejecuto correctamente el query No Procesados");
	      while (resultSet.next()) {
	        bean = new TRKVO();
	        bean.setTrk(resultSet.getString(1));
	        bean.setTipo_mensaje(resultSet.getString(2));
	        bean.setFecha_insert(resultSet.getString(3));
	        bean.setRemitente(resultSet.getString(4));
	        bean.setDestinatario(resultSet.getString(5));       
	        bean.setResp_soap(resultSet.getString(6));	        
	        rList.add(bean) ;
	      }
	    } catch (Exception e) { 
	      log.info("Hubo un error " + e.getMessage());
	    }
	    finally
	    {
	      try
	      {
	        resultSet.close();
	        stmt.close();
	        con.close();
	      }catch(SQLException e)
	      {
	        e.printStackTrace();
	        log.error(e);
	      }
	    }
	      
	    return rList ;
	  }  
  
  
}
