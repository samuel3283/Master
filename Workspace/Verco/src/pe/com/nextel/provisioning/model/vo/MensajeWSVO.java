package pe.com.nextel.provisioning.model.vo;

/**
 * <p>Title: Mensaje </p>
 * <p>Description: Clase encargada de contener todos los datos necesarios para consumir el webservice
 * del SmartKey.</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : MensajeWS</p>
 * <p>Fecha       : 24 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  FRANK PICOY (COMSA)
 * @version 1.0
 */

public class MensajeWSVO {
  private String metodoWS="";
  private String numberType="";
  private String username="";
  private String password="";
  private String number="";
  private String hrlAddress="";
	
  public String getMetodoWS() {
	return metodoWS;
  }
	
  public void setMetodoWS(String metodoWS) {
	this.metodoWS = metodoWS;
  }
	
  public String getNumberType() {
	return numberType;
  }
	
  public void setNumberType(String numberType) {
	this.numberType = numberType;
  }
	
  public String getUsername() {
	return username;
  }
	
  public void setUsername(String username) {
	this.username = username;
  }
	
  public String getPassword() {
	return password;
  }
	
  public void setPassword(String password) {
	this.password = password;
  }
	
  public String getNumber() {
	return number;
  }
	
  public void setNumber(String number) {
	this.number = number;
  }
	
  public String getHrlAddress() {
	return hrlAddress;
  }
	
  public void setHrlAddress(String hrlAddress) {
	this.hrlAddress = hrlAddress;
  }
}
