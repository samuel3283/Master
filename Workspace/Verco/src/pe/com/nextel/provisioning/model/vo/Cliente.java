package pe.com.nextel.provisioning.model.vo;

public class Cliente {

	private Integer codigo;
	private String codcliente;
	private String nombre;
	private String apellidos;
	private String direccion;
	private String email;
	private String telefono;
	private String tipodocumento;
	private String dni;
	private String fechanacimiento;
	
	public Cliente() {
		
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodoc(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public String getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(String fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public String getCodcliente() {
		return codcliente;
	}


	public void setCodcliente(String codcliente) {
		this.codcliente = codcliente;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidos() {
		return apellidos;
	}


	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}
	
	

}
