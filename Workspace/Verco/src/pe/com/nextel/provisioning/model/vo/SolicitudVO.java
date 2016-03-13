package pe.com.nextel.provisioning.model.vo;

import java.util.ArrayList;
import java.util.List;

public class SolicitudVO {

	private String idInterno;
	private String trackId;
	private String idMensaje;
	private String tipoMensaje;
	private String mensaje;
	private String numero;
	private String operadorOrigen;
	private String operadorDestino;
	private String numeroSecuencial;
	private String identificadorProceso;
	private String fechaRegistro;
	private String detalle;
	private String respuestaSoap;
	private String fechaSoap;
	private List ListadoAlarmas;
	private List listadoMensajes;

	public SolicitudVO() {
		super();
		ListadoAlarmas = new ArrayList();
		listadoMensajes = new ArrayList();
	}
	
	
	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public List getListadoMensajes() {
		return listadoMensajes;
	}

	public void setListadoMensajes(List listadoMensajes) {
		this.listadoMensajes = listadoMensajes;
	}

	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public List getListadoAlarmas() {
		return ListadoAlarmas;
	}


	public void setListadoAlarmas(List listadoAlarmas) {
		ListadoAlarmas = listadoAlarmas;
	}


	public String getIdInterno() {
		return idInterno;
	}

	public void setIdInterno(String idInterno) {
		this.idInterno = idInterno;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}

	public String getTipoMensaje() {
		return tipoMensaje;
	}

	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}

	public String getOperadorOrigen() {
		return operadorOrigen;
	}

	public void setOperadorOrigen(String operadorOrigen) {
		this.operadorOrigen = operadorOrigen;
	}

	public String getOperadorDestino() {
		return operadorDestino;
	}

	public void setOperadorDestino(String operadorDestino) {
		this.operadorDestino = operadorDestino;
	}

	public String getNumeroSecuencial() {
		return numeroSecuencial;
	}

	public void setNumeroSecuencial(String numeroSecuencial) {
		this.numeroSecuencial = numeroSecuencial;
	}

	public String getIdentificadorProceso() {
		return identificadorProceso;
	}

	public void setIdentificadorProceso(String identificadorProceso) {
		this.identificadorProceso = identificadorProceso;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getRespuestaSoap() {
		return respuestaSoap;
	}

	public void setRespuestaSoap(String respuestaSoap) {
		this.respuestaSoap = respuestaSoap;
	}

	public String getFechaSoap() {
		return fechaSoap;
	}

	public void setFechaSoap(String fechaSoap) {
		this.fechaSoap = fechaSoap;
	}
	
	
}
