package pe.com.nextel.provisioning.framework.util.jmail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.EmailException;
import pe.com.nextel.provisioning.util.BundleGeneric;

public class Email {
  String strHostSmtp="";
  String strMailFrom="";
  private static final Log log = LogFactory.getLog(Email.class);
  public Email() throws EmailException {
    try {
      strHostSmtp = BundleGeneric.getBundle("SERVER_CORREO");//getHost();
      strMailFrom = BundleGeneric.getBundle("CORREO_FROM");//obtenerFrom();
      log.info("Server SMTP:" + strHostSmtp+"==>Remitente:" + strMailFrom+"<==");
    }
    catch(Exception e) {
    //logger.error(e.getMessage(), e);
    throw new EmailException("No se pudo cargar par\341metros inciales de Envio de emails", e);
    }
  }
  
  public void sendMail(String pstrMailTo, String pstrSubject , String pstrBody) {
    boolean bolHTMLFormat = false;
    MimeMultipart multipart = new MimeMultipart(); 
    Properties pro = new Properties(); 
    pro.put("mail.smtp.host", this.strHostSmtp); //
    Session ses = Session.getDefaultInstance(pro, null); 
    ses.setDebug(false); 
    try { 
      MimeMessage mme = new MimeMessage(ses); 
      mme.setFrom(new InternetAddress(this.strMailFrom));
      mme.setRecipients(Message.RecipientType.TO, pstrMailTo); 
      //mme.setRecipients(Message.RecipientType.CC, null); 
      //mme.setRecipients(Message.RecipientType.BCC, null); 
      mme.setSubject(pstrSubject); 
      mme.setSentDate(new Date()); 
      MimeBodyPart mbp = new MimeBodyPart(); 
      if (bolHTMLFormat){ 
        mbp.setContent(pstrBody, "text/html"); 
      } else{ 
        mbp.setText(pstrBody); 
      } 
      multipart.addBodyPart(mbp); 
      mme.setContent(multipart); 
      Transport.send(mme); 
    } catch (Exception e){ 
    	log.info("ER-P06-009: sendMail.Exception:: " + e.getMessage());           
    } 
  }
}
