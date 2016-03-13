package pe.com.nextel.provisioning.model.vo;

import java.sql.Blob;

/**
 * <p>Title: JobVO</p>
 * <p>Description: Clase VO .</p>
 * <p>Proyecto    : provisioningNextel</p>
 * <p>Clase       : JobDAO</p>
 * <p>Fecha       : 20 Noviembre 2009</p>
 * <p>Copyright   : Copyright (c) 2000-2009</p>
 * <p>Company     : NEXTEL</p>
 * @author  FRANK PICOY ROSAS (COMSA)
 * @version 1.0
 */
public class JobVO {
	private String idjob;
	private String idarchivo;
	private String idcabecera;
	private String fechaejecucion;
	private String mensaje;
	private Blob mensajeBlob;
	
	/**
	 * Constructor JobVO
	 */
	public JobVO() {
		super();
	}

	public String getIdjob() {
    return idjob;
  }

  public void setIdjob(String idjob) {
    this.idjob = idjob;
  }

  public String getIdarchivo() {
		return idarchivo;
	}

	public void setIdarchivo(String idarchivo) {
		this.idarchivo = idarchivo;
	}

	public String getIdcabecera() {
		return idcabecera;
	}

	public void setIdcabecera(String idcabecera) {
		this.idcabecera = idcabecera;
	}

	public String getFechaejecucion() {
		return fechaejecucion;
	}

	public void setFechaejecucion(String fechaejecucion) {
		this.fechaejecucion = fechaejecucion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Blob getMensajeBlob() {
		return mensajeBlob;
	}

	public void setMensajeBlob(Blob mensajeBlob) {
		this.mensajeBlob = mensajeBlob;
	}
}
