package pe.com.nextel.provisioning.model.vo;

import java.util.List;

public class JobReporteVO {
private String idrepor;
private String nombrereporte;
private String tipoReporte;
private List   listaTipoReporte;

private String idjob;
private String idreporte;
private String fechaInicio;
private String fechaFin;
private String indicadorproceso;
private String fecharegistro;
private String ind_fechas;

public String getInd_fechas() {
	return ind_fechas;
}
public void setInd_fechas(String indFechas) {
	ind_fechas = indFechas;
}
public String getIdrepor() {
	return idrepor;
}
public void setIdrepor(String idrepor) {
	this.idrepor = idrepor;
}
public String getNombrereporte() {
	return nombrereporte;
}
public void setNombrereporte(String nombrereporte) {
	this.nombrereporte = nombrereporte;
}
public String getTipoReporte() {
	return tipoReporte;
}
public void setTipoReporte(String tipoReporte) {
	this.tipoReporte = tipoReporte;
}
public List getListaTipoReporte() {
	return listaTipoReporte;
}
public void setListaTipoReporte(List listaTipoReporte) {
	this.listaTipoReporte = listaTipoReporte;
}
public String getIdjob() {
	return idjob;
}
public void setIdjob(String idjob) {
	this.idjob = idjob;
}
public String getIdreporte() {
	return idreporte;
}
public void setIdreporte(String idreporte) {
	this.idreporte = idreporte;
}
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
public String getIndicadorproceso() {
	return indicadorproceso;
}
public void setIndicadorproceso(String indicadorproceso) {
	this.indicadorproceso = indicadorproceso;
}
public String getFecharegistro() {
	return fecharegistro;
}
public void setFecharegistro(String fecharegistro) {
	this.fecharegistro = fecharegistro;
}
public JobReporteVO(){
	super();
}
public JobReporteVO(String idreporte,String nombrereporte){
	this.idreporte=idreporte;
	this.nombrereporte=nombrereporte;
}
}
