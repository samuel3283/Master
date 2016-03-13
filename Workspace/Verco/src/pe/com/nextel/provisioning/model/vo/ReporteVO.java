package pe.com.nextel.provisioning.model.vo;

public class ReporteVO {	
	/**REPORTE ID=1**/
	private String idProceso;
	private String operadorReceptor;
	private String operadorCedente;
	private String tipoDocumento;
	private String numeroDocumento;
	private String rangoInicial;
	private String rangoFinal;
	private String tipoPortabilidad;
	private String fechaHoraMensajeSp;
      private String ultimoMensaje;
	private String causaRechazo;
	/**REPORTE ID=2**/
	private String numero;  
	private String donante;  
	private String receptor;  
	private String donante_inicial;  
	private String ultimo_proceso;  
	private String numero_secuencial;  
	private String inicio_proceso;  
	private String inicio_ventana;  
	private String duracion_ventana;  
	private String estado;  
	private String timestamp_modificacion;  
	private String donante_previo;  
	private String fechaEabdcp;  
	/**REPORTE ID=3**/
	private String operador;
	private String emisionFactura;
	private String portador;
	private String recAbd;
	private String retornos;
	private String objeciones;
	private String conceptoFactura;
	private String fecha;
	private String totalRango;
	private String mensaje;
	private String monto;
	
	public ReporteVO(){
		super();
	}
	
	
	 
	public String getFechaEabdcp() {
		return fechaEabdcp;
	}



	public void setFechaEabdcp(String fechaEabdcp) {
		this.fechaEabdcp = fechaEabdcp;
	}



	public String getMonto() {
		return monto;
	}



	public void setMonto(String monto) {
		this.monto = monto;
	}



	public String getUltimoMensaje() {
		return ultimoMensaje;
	}



	public void setUltimoMensaje(String ultimoMensaje) {
		this.ultimoMensaje = ultimoMensaje;
	}



	public String getCausaRechazo() {
		return causaRechazo;
	}



	public void setCausaRechazo(String causaRechazo) {
		this.causaRechazo = causaRechazo;
	}



	public String getConceptoFactura() {
		return conceptoFactura;
	}



	public void setConceptoFactura(String conceptoFactura) {
		this.conceptoFactura = conceptoFactura;
	}



	public String getFecha() {
		return fecha;
	}



	public void setFecha(String fecha) {
		this.fecha = fecha;
	}



	public String getTotalRango() {
		return totalRango;
	}



	public void setTotalRango(String totalRango) {
		this.totalRango = totalRango;
	}



	public String getMensaje() {
		return mensaje;
	}



	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}



	public String getObjeciones() {
		return objeciones;
	}



	public void setObjeciones(String objeciones) {
		this.objeciones = objeciones;
	}



	public String getOperador() {
		return operador;
	}



	public void setOperador(String operador) {
		this.operador = operador;
	}



	public String getEmisionFactura() {
		return emisionFactura;
	}



	public void setEmisionFactura(String emisionFactura) {
		this.emisionFactura = emisionFactura;
	}



	public String getPortador() {
		return portador;
	}



	public void setPortador(String portador) {
		this.portador = portador;
	}



	public String getRecAbd() {
		return recAbd;
	}



	public void setRecAbd(String recAbd) {
		this.recAbd = recAbd;
	}



	public String getRetornos() {
		return retornos;
	}



	public void setRetornos(String retornos) {
		this.retornos = retornos;
	}



	



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getDonante() {
		return donante;
	}



	public void setDonante(String donante) {
		this.donante = donante;
	}



	public String getReceptor() {
		return receptor;
	}



	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}



	public String getDonante_inicial() {
		return donante_inicial;
	}



	public void setDonante_inicial(String donanteInicial) {
		donante_inicial = donanteInicial;
	}



	public String getUltimo_proceso() {
		return ultimo_proceso;
	}



	public void setUltimo_proceso(String ultimoProceso) {
		ultimo_proceso = ultimoProceso;
	}



	public String getNumero_secuencial() {
		return numero_secuencial;
	}



	public void setNumero_secuencial(String numeroSecuencial) {
		numero_secuencial = numeroSecuencial;
	}



	public String getInicio_proceso() {
		return inicio_proceso;
	}



	public void setInicio_proceso(String inicioProceso) {
		inicio_proceso = inicioProceso;
	}



	public String getInicio_ventana() {
		return inicio_ventana;
	}



	public void setInicio_ventana(String inicioVentana) {
		inicio_ventana = inicioVentana;
	}



	public String getDuracion_ventana() {
		return duracion_ventana;
	}



	public void setDuracion_ventana(String duracionVentana) {
		duracion_ventana = duracionVentana;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public String getTimestamp_modificacion() {
		return timestamp_modificacion;
	}



	public void setTimestamp_modificacion(String timestampModificacion) {
		timestamp_modificacion = timestampModificacion;
	}



	public String getDonante_previo() {
		return donante_previo;
	}



	public void setDonante_previo(String donantePrevio) {
		donante_previo = donantePrevio;
	}



	public String getIdProceso() {
		return idProceso;
	}
	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}
	public String getOperadorReceptor() {
		return operadorReceptor;
	}
	public void setOperadorReceptor(String operadorReceptor) {
		this.operadorReceptor = operadorReceptor;
	}
	public String getOperadorCedente() {
		return operadorCedente;
	}
	public void setOperadorCedente(String operadorCedente) {
		this.operadorCedente = operadorCedente;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getRangoInicial() {
		return rangoInicial;
	}
	public void setRangoInicial(String rangoInicial) {
		this.rangoInicial = rangoInicial;
	}
	public String getRangoFinal() {
		return rangoFinal;
	}
	public void setRangoFinal(String rangoFinal) {
		this.rangoFinal = rangoFinal;
	}
	public String getTipoPortabilidad() {
		return tipoPortabilidad;
	}
	public void setTipoPortabilidad(String tipoPortabilidad) {
		this.tipoPortabilidad = tipoPortabilidad;
	}
	public String getFechaHoraMensajeSp() {
		return fechaHoraMensajeSp;
	}
	public void setFechaHoraMensajeSp(String fechaHoraMensajeSp) {
		this.fechaHoraMensajeSp = fechaHoraMensajeSp;
	}

	
	
	
	
}
