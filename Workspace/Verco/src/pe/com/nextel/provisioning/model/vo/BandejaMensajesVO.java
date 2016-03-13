package pe.com.nextel.provisioning.model.vo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

public class BandejaMensajesVO {
	
	public BandejaMensajesVO(){
		super();
    }
	
	private String estadoSoap;
	private Date fechaRegistro;
	private Date fechaSalida;
	private String fRegistro;
	private String mensajeJob;
	
	public String getfRegistro() {
		return fRegistro;
	}
	public void setfRegistro(String fRegistro) {
		this.fRegistro = fRegistro;
	}
	public String getfSoap() {
		return fSoap;
	}
	public void setfSoap(String fSoap) {
		this.fSoap = fSoap;
	}

	private Blob Mensaje;
	private Date FechaSoap;
	private String fSoap;
	private String fechaInicio;
	private String fechaFin;
		
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	private BigInteger codResSoap;
	private String desResSoap;
	private BigDecimal idJob ;
	private BigDecimal idBandeja;
	private MensajeVO mensajeVO;
	private List mensajeList;
	private String idCabecera;
	private String idArchivo;
	
	public List getMensajeList() {
		return mensajeList;
	}
	public void setMensajeList(List mensajeList) {
		this.mensajeList = mensajeList;
	}
	public MensajeVO getMensajeVO() {
		return mensajeVO;
	}
	public void setMensajeVO(MensajeVO mensajeVO) {
		this.mensajeVO = mensajeVO;
	}
	public String getEstadoSoap() {
		return estadoSoap;
	}
	public void setEstadoSoap(String estadoSoap) {
		this.estadoSoap = estadoSoap;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public Blob getMensaje() {
		return Mensaje;
	}
	public void setMensaje(Blob mensaje) {
		Mensaje = mensaje;
	}
	public Date getFechaSoap() {
		return FechaSoap;
	}
	public void setFechaSoap(Date fechaSoap) {
		FechaSoap = fechaSoap;
	}
	public BigInteger getCodResSoap() {
		return codResSoap;
	}
	public void setCodResSoap(BigInteger codResSoap) {
		this.codResSoap = codResSoap;
	}
	public String getDesResSoap() {
		return desResSoap;
	}
	public void setDesResSoap(String desResSoap) {
		this.desResSoap = desResSoap;
	}
	public BigDecimal getIdJob() {
		return idJob;
	}
	public void setIdJob(BigDecimal idJob) {
		this.idJob = idJob;
	}
	public BigDecimal getIdBandeja() {
		return idBandeja;
	}
	public void setIdBandeja(BigDecimal idBandeja) {
		this.idBandeja = idBandeja;
	}
  public String getMensajeJob() {
    return mensajeJob;
  }
  public void setMensajeJob(String mensajeJob) {
    this.mensajeJob = mensajeJob;
  }
  public String getIdCabecera() {
    return idCabecera;
  }
  public void setIdCabecera(String idCabecera) {
    this.idCabecera = idCabecera;
  }
  public String getIdArchivo() {
    return idArchivo;
  }
  public void setIdArchivo(String idArchivo) {
    this.idArchivo = idArchivo;
  }
		
	
}
