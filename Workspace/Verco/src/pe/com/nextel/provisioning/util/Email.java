package pe.com.nextel.provisioning.util;

import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
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
 
public Email() throws EmailException{
	try{
	  strHostSmtp = BundleGeneric.getBundle("SERVER_CORREO");//getHost();
	  strMailFrom = BundleGeneric.getBundle("CORREO_FROM");//obtenerFrom();
		
	     log.info("Server SMTP:" + strHostSmtp+"==>Remitente:" + strMailFrom+"<==");
	}catch(Exception e){
		
		throw new EmailException("No se pudo cargar", e);
	}
}



public void sendEmail(String pstrMailTo, String pstrMailCc, String pstrSubject , String pstrBody,String[] pstrFileAdjuntos){
boolean bolHTMLFormat = false;
log.info("Enviando por correo="+pstrMailTo);
MimeMultipart mimemultipart = new MimeMultipart();
Properties properties = new Properties();
properties.put("mail.smtp.host",this.strHostSmtp);
Session sesion = Session.getDefaultInstance(properties,null);
sesion.setDebug(false);

try{
MimeMessage mimemessage = new MimeMessage(sesion);	
mimemessage.setFrom(new InternetAddress(this.strMailFrom));
mimemessage.setRecipients(Message.RecipientType.TO, pstrMailTo);	
mimemessage.setRecipients(Message.RecipientType.CC, pstrMailCc);	
mimemessage.setSubject(pstrSubject);	
mimemessage.setSentDate(new Date());
MimeBodyPart mimebodypart = new MimeBodyPart();
if(bolHTMLFormat){
mimebodypart.setContent(pstrBody, "txt/html");
}else{
mimebodypart.setText(pstrBody);
}
mimemultipart.addBodyPart(mimebodypart);

//OPCION ADJUNTOS ARCHIVOS // MODIFICADO POR HMIRAMIRA 15-02-2010
for (int i=0;i<pstrFileAdjuntos.length;i++){
	  MimeBodyPart adjunto =new MimeBodyPart();
	  adjunto.setDataHandler(new DataHandler(new FileDataSource(pstrFileAdjuntos[i])));
	  adjunto.setFileName(new FileDataSource(pstrFileAdjuntos[i]).getName());    	  
	  mimemultipart.addBodyPart(adjunto);
}
//------------------------

mimemessage.setContent(mimemultipart);
Transport.send(mimemessage);

}catch(Exception e){
log.info("ER-P06-009: sendMail.Exception::" + e.getMessage());	
}
}

public void sendEmail2(String pstrMailTo, String pstrMailCc, String pstrSubject , String pstrBody,String[] pstrFileAdjuntos){
  boolean bolHTMLFormat = false;
  log.info("Enviando por correo="+pstrMailTo);
  MimeMultipart mimemultipart = new MimeMultipart();
  Properties properties = new Properties();
  properties.put("mail.smtp.host",this.strHostSmtp);
  Session sesion = Session.getDefaultInstance(properties,null);
  sesion.setDebug(false);

  try{
  MimeMessage mimemessage = new MimeMessage(sesion);  
  mimemessage.setFrom(new InternetAddress(this.strMailFrom));
  mimemessage.setRecipients(Message.RecipientType.TO, pstrMailTo);  
  mimemessage.setRecipients(Message.RecipientType.CC, pstrMailCc);  
  mimemessage.setSubject(pstrSubject);  
  mimemessage.setSentDate(new Date());
  MimeBodyPart mimebodypart = new MimeBodyPart();
  if(bolHTMLFormat){
  mimebodypart.setContent(pstrBody, "txt/html");
  }else{
  mimebodypart.setText(pstrBody);
  }
  mimemultipart.addBodyPart(mimebodypart);

  //OPCION ADJUNTOS ARCHIVOS // MODIFICADO POR HMIRAMIRA 15-02-2010
  for (int i=0;i<pstrFileAdjuntos.length;i++){
      MimeBodyPart adjunto =new MimeBodyPart();
      adjunto.setDataHandler(new DataHandler(new FileDataSource(pstrFileAdjuntos[i])));
      adjunto.setFileName(new FileDataSource(pstrFileAdjuntos[i]).getName());       
      mimemultipart.addBodyPart(adjunto);
  }
  //------------------------

  mimemessage.setContent(mimemultipart);
  Transport.send(mimemessage);

  }catch(Exception e){
  log.info("ER-P06-009: sendMail.Exception::" + e.getMessage());  
  }
  }


public void sendEmailSinAdjunto(String pstrMailTo, String pstrMailCc, String pstrSubject , String pstrBody){
	boolean bolHTMLFormat = false;
	log.info("Enviando por correo="+pstrMailTo);
	MimeMultipart mimemultipart = new MimeMultipart();
	Properties properties = new Properties();
	properties.put("mail.smtp.host",this.strHostSmtp);
	Session sesion = Session.getDefaultInstance(properties,null);
	sesion.setDebug(false);

	try{
	MimeMessage mimemessage = new MimeMessage(sesion);	
	mimemessage.setFrom(new InternetAddress(this.strMailFrom));
	mimemessage.setRecipients(Message.RecipientType.TO, pstrMailTo);	
	mimemessage.setRecipients(Message.RecipientType.CC, pstrMailCc);	
	mimemessage.setSubject(pstrSubject);	
	mimemessage.setSentDate(new Date());
	MimeBodyPart mimebodypart = new MimeBodyPart();
	if(bolHTMLFormat){
	mimebodypart.setContent(pstrBody, "txt/html");
	}else{
	mimebodypart.setText(pstrBody);
	}
	mimemultipart.addBodyPart(mimebodypart);

	mimemessage.setContent(mimemultipart);
	Transport.send(mimemessage);

	}catch(Exception e){
	log.info("ER-P06-009: sendMail.Exception::" + e.getMessage());	
	}
	}


public static void main(String[] args) {
	
	Email email; //
	String pstrMailTo="snavarro@comsa.com.pe";
	String pstrMailCc="snavarror@indracompany.com";
	String pstrSubject="Re: Incidencia Reportada";
	String pstrBody="... ";
	

	
	try {
		email = new Email();//
		
		email.sendEmail(pstrMailTo, pstrMailCc, pstrSubject, pstrBody,args);//
				
	} catch (Exception e) {
		
	}
	
	
}



}
