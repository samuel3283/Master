package pe.com.nextel.provisioning.model.vo;

public class TRKVO {

	private String trk;
	public String getTrk() {
    return trk;
  }

  public void setTrk(String trk) {
    this.trk = trk;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getRemitente() {
    return remitente;
  }

  public void setRemitente(String remitente) {
    this.remitente = remitente;
  }

  public String getDestinatario() {
    return destinatario;
  }

  public void setDestinatario(String destinatario) {
    this.destinatario = destinatario;
  }

  public String getFecha_insert() {
    return fecha_insert;
  }

  public void setFecha_insert(String fechaInsert) {
    fecha_insert = fechaInsert;
  }

  public String getTipo_mensaje() {
    return tipo_mensaje;
  }

  public void setTipo_mensaje(String tipoMensaje) {
    tipo_mensaje = tipoMensaje;
  }

  public String getResp_soap() {
    return resp_soap;
  }

  public void setResp_soap(String respSoap) {
    resp_soap = respSoap;
  }

  public String getFecha_soap() {
    return fecha_soap;
  }

  public void setFecha_soap(String fechaSoap) {
    fecha_soap = fechaSoap;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }



  private String estado;
	private String remitente;
  private String destinatario;
  private String fecha_insert;
  private String tipo_mensaje;
  private String resp_soap;
  private String fecha_soap;
  private String mensaje;
	
	/**
	 * Constructor TipoArchivoVO
	 */
	public TRKVO() {
		super();
	}

				
}
