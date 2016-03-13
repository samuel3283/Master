package pe.com.nextel.provisioning.model.vo;

import java.sql.Date;

public class UsuarioVO {
	
	private Integer codUsuario;
	private String usuario;
	private String password;
	private String nombre;
	private Date fecha_registro;
	private Integer usuario_registro;
	private String estado;
	
	public UsuarioVO() {
		super();
	}	

	
	
		
	
	
	public Date getFecha_registro() {
		return fecha_registro;
	}






	public void setFecha_registro(Date fechaRegistro) {
		fecha_registro = fechaRegistro;
	}






	public Integer getCodUsuario() {
		return codUsuario;
	}



	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}





	public Integer getUsuario_registro() {
		return usuario_registro;
	}



	public void setUsuario_registro(Integer usuarioRegistro) {
		usuario_registro = usuarioRegistro;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
}
