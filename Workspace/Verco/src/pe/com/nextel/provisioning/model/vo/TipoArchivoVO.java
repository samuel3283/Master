/**
 * 
 */
package pe.com.nextel.provisioning.model.vo;

/**
 * @author snavarro
 *
 */
public class TipoArchivoVO {
	
	private String codigo;
	private String descripcion;
	
	/**
	 * Constructor TipoArchivoVO
	 */
	public TipoArchivoVO() {
		super();
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
		
	
}
