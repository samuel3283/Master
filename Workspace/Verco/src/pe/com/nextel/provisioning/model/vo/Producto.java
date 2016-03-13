package pe.com.nextel.provisioning.model.vo;

import java.math.BigDecimal;

public class Producto {

	private Integer codigo;
	private String nombre;
	private String descripcion;
	private String tipoproducto;
	private Integer stock;
	private BigDecimal precio;
	private String ruta;
	
	
	public Producto() {
		
	}


	public String getRuta() {
		return ruta;
	}


	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getTipoproducto() {
		return tipoproducto;
	}


	public void setTipoproducto(String tipoproducto) {
		this.tipoproducto = tipoproducto;
	}


	public Integer getStock() {
		return stock;
	}


	public void setStock(Integer stock) {
		this.stock = stock;
	}


	public BigDecimal getPrecio() {
		return precio;
	}


	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}


}
