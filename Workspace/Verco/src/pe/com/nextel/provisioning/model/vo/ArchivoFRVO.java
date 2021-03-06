package pe.com.nextel.provisioning.model.vo;

import java.sql.Date;

public class ArchivoFRVO {
	
	private CabeceraArchivoVO cabecera;
	private int idarchivo;
	private String numeroSolicitud;
	private String numeroTelefono;
//	private Date fechaEjecucion;
//	private Date fechaComunicacion;
	private String fechaEjecucion;
	private String fechaComunicacion;	
	private String receptor;
	private String cedenteInicial;
	private String motivoRetorno;
	private String tipoTecnologia;
	private String worldNumber;
	private String mensaje;
	private Date fechaRegistro;
	
	private String asignatario;
	private String comando;
	private String numbertype;
	private String hlraddress;
	private String newroute;
	
	/**
	 * Constructor ArchivoFRVO
	 */
	public ArchivoFRVO() {
		super();
	}

	/**
	 * @return the idarchivo
	 */
	public int getIdarchivo() {
		return idarchivo;
	}

	/**
	 * @param idarchivo the idarchivo to set
	 */
	public void setIdarchivo(int idarchivo) {
		this.idarchivo = idarchivo;
	}

	/**
	 * @return the numeroSolicitud
	 */
	public String getNumeroSolicitud() {
		return numeroSolicitud;
	}

	/**
	 * @param numeroSolicitud the numeroSolicitud to set
	 */
	public void setNumeroSolicitud(String numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}

	/**
	 * @return the numeroTelefono
	 */
	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	/**
	 * @param numeroTelefono the numeroTelefono to set
	 */
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}



	/**
	 * @return the receptor
	 */
	public String getReceptor() {
		return receptor;
	}

	/**
	 * @param receptor the receptor to set
	 */
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}

	/**
	 * @return the cedenteInicial
	 */
	public String getCedenteInicial() {
		return cedenteInicial;
	}

	/**
	 * @param cedenteInicial the cedenteInicial to set
	 */
	public void setCedenteInicial(String cedenteInicial) {
		this.cedenteInicial = cedenteInicial;
	}

	/**
	 * @return the motivoRetorno
	 */
	public String getMotivoRetorno() {
		return motivoRetorno;
	}

	/**
	 * @param motivoRetorno the motivoRetorno to set
	 */
	public void setMotivoRetorno(String motivoRetorno) {
		this.motivoRetorno = motivoRetorno;
	}

	/**
	 * @return the tipoTecnologia
	 */
	public String getTipoTecnologia() {
		return tipoTecnologia;
	}

	/**
	 * @param tipoTecnologia the tipoTecnologia to set
	 */
	public void setTipoTecnologia(String tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}

	/**
	 * @return the worldNumber
	 */
	public String getWorldNumber() {
		return worldNumber;
	}

	/**
	 * @param worldNumber the worldNumber to set
	 */
	public void setWorldNumber(String worldNumber) {
		this.worldNumber = worldNumber;
	}

	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the asignatario
	 */
	public String getAsignatario() {
		return asignatario;
	}

	/**
	 * @param asignatario the asignatario to set
	 */
	public void setAsignatario(String asignatario) {
		this.asignatario = asignatario;
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

	/**
	 * @return the numbertype
	 */
	public String getNumbertype() {
		return numbertype;
	}

	/**
	 * @param numbertype the numbertype to set
	 */
	public void setNumbertype(String numbertype) {
		this.numbertype = numbertype;
	}

	/**
	 * @return the hlraddress
	 */
	public String getHlraddress() {
		return hlraddress;
	}

	/**
	 * @param hlraddress the hlraddress to set
	 */
	public void setHlraddress(String hlraddress) {
		this.hlraddress = hlraddress;
	}

	/**
	 * @return the newroute
	 */
	public String getNewroute() {
		return newroute;
	}

	/**
	 * @param newroute the newroute to set
	 */
	public void setNewroute(String newroute) {
		this.newroute = newroute;
	}

	public String getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(String fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public String getFechaComunicacion() {
		return fechaComunicacion;
	}

	public void setFechaComunicacion(String fechaComunicacion) {
		this.fechaComunicacion = fechaComunicacion;
	}	

	public CabeceraArchivoVO getCabecera() {
		return cabecera;
	}

	public void setCabecera(CabeceraArchivoVO cabecera) {
		this.cabecera = cabecera;
	}	
	
}
