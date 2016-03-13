package pe.com.nextel.provisioning.model.vo;

import java.math.BigDecimal;
import java.util.Date;

public class BandejaSalidaVO {

	public BandejaSalidaVO(){
		super();
	}
	
	private BigDecimal idBandeja;
	private Date fechaSalida;
	private BigDecimal reIntentos; 
	private BigDecimal bLoqueado;
	private String  estadoIntentos ;
	
	public String getEstadoIntentos() {
		return estadoIntentos;
	}
	public void setEstadoIntentos(String estadoIntentos) {
		this.estadoIntentos = estadoIntentos;
	}
	public BigDecimal getIdBandeja() {
		return idBandeja;
	}
	public void setIdBandeja(BigDecimal idBandeja) {
		this.idBandeja = idBandeja;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public BigDecimal getReIntentos() {
		return reIntentos;
	}
	public void setReIntentos(BigDecimal reIntentos) {
		this.reIntentos = reIntentos;
	}
	public BigDecimal getbLoqueado() {
		return bLoqueado;
	}
	public void setbLoqueado(BigDecimal bLoqueado) {
		this.bLoqueado = bLoqueado;
	}
		
}
