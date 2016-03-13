package pe.com.nextel.provisioning.model.vo;

public class EstadisticaVO {
	private String operador;
	private int exito;
	private int sinExito;
	private int pendiente;
	private int totales;

	public EstadisticaVO() {
		super();
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public int getExito() {
		return exito;
	}

	public void setExito(int exito) {
		this.exito = exito;
	}

	public int getSinExito() {
		return sinExito;
	}

	public void setSinExito(int sinExito) {
		this.sinExito = sinExito;
	}

	public int getPendiente() {
		return pendiente;
	}

	public void setPendiente(int pendiente) {
		this.pendiente = pendiente;
	}

	public int getTotales() {
		return totales;
	}

	public void setTotales(int totales) {
		this.totales = totales;
	}
	
	
}
