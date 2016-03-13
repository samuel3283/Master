package pe.com.nextel.provisioning.model.vo;

public class AlarmaVO {

	private String processinstance;
	private String fechaExpiracion;
	private String alarma;
	private String descripcionAlarma;
	
	public AlarmaVO() {
		super();
	}
	public String getProcessinstance() {
		return processinstance;
	}
	public void setProcessinstance(String processinstance) {
		this.processinstance = processinstance;
	}
	public String getFechaExpiracion() {
		return fechaExpiracion;
	}
	public void setFechaExpiracion(String fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
	public String getAlarma() {
		return alarma;
	}
	public void setAlarma(String alarma) {
		this.alarma = alarma;
	}
	public String getDescripcionAlarma() {
		return descripcionAlarma;
	}
	public void setDescripcionAlarma(String descripcionAlarma) {
		this.descripcionAlarma = descripcionAlarma;
	}

	
}
