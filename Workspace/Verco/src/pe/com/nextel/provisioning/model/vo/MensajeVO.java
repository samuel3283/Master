package pe.com.nextel.provisioning.model.vo;

public class MensajeVO {

	protected String comando;	
	protected java.lang.String USERNAME;
  protected java.lang.String PASSWORD;
  protected java.lang.String number;
  protected java.lang.String numberType;
  protected java.lang.String IMSI;
  protected java.lang.String HLRAddress;
  protected java.lang.String newRoute;
  protected java.lang.String operatorCode;
  protected java.lang.String locationCode;
  protected java.lang.String interconnectCode;
  protected java.lang.String CPcode;
  protected java.lang.String CNL;
  protected java.lang.String recipientID;
  protected java.lang.String donorID;
  protected java.lang.String control;
  
  public MensajeVO(){
    this.HLRAddress="";
  }
        	
    
    /**
	 * @return the comando
	 */
	public String getComando() {
		return comando;
	}
	/**
	 * @param comando the comando to set
	 */
	public void setComando(String comando) {
		this.comando = comando;
	}
	public java.lang.String getControl() {
		return control;
	}
	public void setControl(java.lang.String control) {
		this.control = control;
	}
	public java.lang.String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(java.lang.String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public java.lang.String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(java.lang.String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public java.lang.String getNumber() {
		return number;
	}
	public void setNumber(java.lang.String number) {
		this.number = number;
	}
	public java.lang.String getNumberType() {
		return numberType;
	}
	public void setNumberType(java.lang.String numberType) {
		this.numberType = numberType;
	}
	public java.lang.String getIMSI() {
		return IMSI;
	}
	public void setIMSI(java.lang.String iMSI) {
		IMSI = iMSI;
	}
	public java.lang.String getHLRAddress() {
		return HLRAddress;
	}
	public void setHLRAddress(java.lang.String hLRAddress) {
		HLRAddress = hLRAddress;
	}
	public java.lang.String getNewRoute() {
		return newRoute;
	}
	public void setNewRoute(java.lang.String newRoute) {
		this.newRoute = newRoute;
	}
	public java.lang.String getOperatorCode() {
		return operatorCode;
	}
	public void setOperatorCode(java.lang.String operatorCode) {
		this.operatorCode = operatorCode;
	}
	public java.lang.String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(java.lang.String locationCode) {
		this.locationCode = locationCode;
	}
	public java.lang.String getInterconnectCode() {
		return interconnectCode;
	}
	public void setInterconnectCode(java.lang.String interconnectCode) {
		this.interconnectCode = interconnectCode;
	}
	public java.lang.String getCPcode() {
		return CPcode;
	}
	public void setCPcode(java.lang.String cPcode) {
		CPcode = cPcode;
	}
	public java.lang.String getCNL() {
		return CNL;
	}
	public void setCNL(java.lang.String cNL) {
		CNL = cNL;
	}
	public java.lang.String getRecipientID() {
		return recipientID;
	}
	public void setRecipientID(java.lang.String recipientID) {
		this.recipientID = recipientID;
	}
	public java.lang.String getDonorID() {
		return donorID;
	}
	public void setDonorID(java.lang.String donorID) {
		this.donorID = donorID;
	}

	
	
}
