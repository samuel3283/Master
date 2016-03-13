package pe.com.nextel.provisioning.model.vo;

public class PortabilidadVO {

	private String codigo ;
	private String descripcion;
	private String tipo_mensaje;
	private String totalMensajes;
	private String totalLineas;
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipo_mensaje() {
		return tipo_mensaje;
	}
	public void setTipo_mensaje(String tipo_mensaje) {
		this.tipo_mensaje = tipo_mensaje;
	}
	
	public String getTotalMensajes() {
		return totalMensajes;
	}
	public void setTotalMensajes(String totalMensajes) {
		this.totalMensajes = totalMensajes;
	}
	public String getTotalLineas() {
		return totalLineas;
	}
	public void setTotalLineas(String totalLineas) {
		this.totalLineas = totalLineas;
	}
}
