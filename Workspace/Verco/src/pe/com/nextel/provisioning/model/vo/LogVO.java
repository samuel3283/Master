package pe.com.nextel.provisioning.model.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class LogVO implements Serializable {

	private BigInteger idLog;
	private String descripcion;
	private String idError;
	private Date fechaRegistro;
	
	public LogVO(){}
	
	public BigInteger getIdLog() {
		return idLog;
	}
	public void setIdLog(BigInteger idLog) {
		this.idLog = idLog;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getIdError() {
		return idError;
	}
	public void setIdError(String idError) {
		this.idError = idError;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
}
