package pe.com.nextel.provisioning.model.vo;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author snavarro
 *
 */	
public class CabeceraArchivoVO {
	
	private int idcabecera;
	private String tipoArchivo;
	private String fechaProceso;
	private String  fechaReg;
	private String codigo;
	private String archivo;
	private TipoArchivoVO tipoArchivoBean;
	private int cantidadRegistros;
	private int cantidadAprovisionados;
	private String procesoInicio;
	private EstadoProcesoVO estadoProcesoBean;
	private Date fechaRegistro;
	private String fecha;
	private int flagDelay;
	private int flagEjecucion;
	private String fechaInicioEjecucion;
	@SuppressWarnings("unused")
	private ArrayList<ArchivoFPVO> listaArchivoFPVO;
	@SuppressWarnings("unused")
	private ArrayList<ArchivoFRVO> listaArchivoFRVO;
	private String estado;
	
	/**
	 * Constructor CabeceraArchivoVO
	 */
	
	@SuppressWarnings("unchecked")
	public CabeceraArchivoVO() {
		super();
		tipoArchivoBean = new TipoArchivoVO();
		estadoProcesoBean = new EstadoProcesoVO();	
		listaArchivoFPVO = new ArrayList();
		listaArchivoFPVO = new ArrayList();
	}
	
	
	/**
	 * @return the cantidadAprovsionados
	 */
	public int getCantidadAprovisionados() {
		return cantidadAprovisionados;
	}


	/**
	 * @param cantidadAprovsionados the cantidadAprovsionados to set
	 */
	public void setCantidadAprovsionados(int cantidadAprovisionados) {
		this.cantidadAprovisionados = cantidadAprovisionados;
	}


	/**
	 * @return the idcabecera
	 */
	public int getIdcabecera() {
		return idcabecera;
	}


	/**
	 * @param idcabecera the idcabecera to set
	 */
	public void setIdcabecera(int idcabecera) {
		this.idcabecera = idcabecera;
	}


	/**
	 * @return the tipoArchivo
	 */
	public String getTipoArchivo() {
		return tipoArchivo;
	}


	/**
	 * @param tipoArchivo the tipoArchivo to set
	 */
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}


	
	/**
	 * @return the fechaProceso
	 */
	public String getFechaProceso() {
		return fechaProceso;
	}


	/**
	 * @param fechaProceso the fechaProceso to set
	 */
	public void setFechaProceso(String fechaProceso) {
		this.fechaProceso = fechaProceso;
	}


	/**
	 * @return the archivo
	 */
	public String getArchivo() {
		return archivo;
	}
	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	/**
	 * @return the tipoArchivoBean
	 */
	public TipoArchivoVO getTipoArchivoBean() {
		return tipoArchivoBean;
	}
	/**
	 * @param tipoArchivoBean the tipoArchivoBean to set
	 */
	public void setTipoArchivoBean(TipoArchivoVO tipoArchivoBean) {
		this.tipoArchivoBean = tipoArchivoBean;
	}
	/**
	 * @return the cantidadRegistros
	 */
	public int getCantidadRegistros() {
		return cantidadRegistros;
	}
	/**
	 * @param cantidadRegistros the cantidadRegistros to set
	 */
	public void setCantidadRegistros(int cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}

	/**
	 * @return the procesoInicio
	 */
	public String getProcesoInicio() {
		return procesoInicio;
	}
	/**
	 * @param procesoInicio the procesoInicio to set
	 */
	public void setProcesoInicio(String procesoInicio) {
		this.procesoInicio = procesoInicio;
	}
	/**
	 * @return the estadoProcesoBean
	 */
	public EstadoProcesoVO getEstadoProcesoBean() {
		return estadoProcesoBean;
	}
	/**
	 * @param estadoProcesoBean the estadoProcesoBean to set
	 */
	public void setEstadoProcesoBean(EstadoProcesoVO estadoProcesoBean) {
		this.estadoProcesoBean = estadoProcesoBean;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}	
	
	public ArrayList<ArchivoFPVO> getListaArchivoFPVO() {
		return listaArchivoFPVO;
	}
	public void setListaArchivoFPVO(ArrayList<ArchivoFPVO> listaArchivoFPVO) {
		this.listaArchivoFPVO = listaArchivoFPVO;
	}
	public ArrayList<ArchivoFRVO> getListaArchivoFRVO() {
		return listaArchivoFRVO;
	}
	public void setListaArchivoFRVO(ArrayList<ArchivoFRVO> listaArchivoFRVO) {
		this.listaArchivoFRVO = listaArchivoFRVO;
	}	

	




	/**
	 * @return the fechaReg
	 */
	public String getFechaReg() {
		return fechaReg;
	}


	/**
	 * @param fechaReg the fechaReg to set
	 */
	public void setFechaReg(String fechaReg) {
		this.fechaReg = fechaReg;
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


	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}	
	
	public int getFlagDelay() {
		return flagDelay;
	}


	public void setFlagDelay(int flagDelay) {
		this.flagDelay = flagDelay;
	}


	public int getFlagEjecucion() {
		return flagEjecucion;
	}


	public void setFlagEjecucion(int flagEjecucion) {
		this.flagEjecucion = flagEjecucion;
	}	
	
	public String getFechaInicioEjecucion() {
		return fechaInicioEjecucion;
	}

	public void setFechaInicioEjecucion(String fechaInicioEjecucion) {
		this.fechaInicioEjecucion = fechaInicioEjecucion;
	}


  public String getEstado() {
    return estado;
  }


  public void setEstado(String estado) {
    this.estado = estado;
  }	
	
	
	
}
