package pe.com.nextel.provisioning.model.vo;

import java.util.List;

public class PreFacturacionVO {
public String mesInicio;
public String anioInicio;
public String mesfin;
public String aniofin;		
public String periodoInicio;
public String periodoFin;

public String operador;
public String portados; 
public String retornos; 
public String objeciones;
public String monto;
public String tot_portados;
public String tot_retornos;
public String tot_objeciones;
public String tot_monto;

private List listaPreFacturacion;
private String fechaInicio;
private String fechaFin;
private String tipoCons;
private String conceptoFactura;
private String idProceso;
private String fecha;
private String inicioRango;
private String finalRango;
private String totalRango;

public PreFacturacionVO(){}
public PreFacturacionVO(String operador,
					    String portados,String retornos,String objeciones,String monto,
					    String tot_portados,String tot_retornos,String tot_objeciones,
					    String tot_monto,String conceptoFactura,String idProceso,String fecha,
					    String inicioRango,String finalRango,String totalRango)
{
	this.operador=operador;
	this.portados=portados;
	this.retornos=retornos;
	this.objeciones=objeciones;
	this.monto=monto;
	this.tot_portados=tot_portados;
	this.tot_retornos=tot_retornos;
	this.tot_objeciones=tot_objeciones;
	this.tot_monto=tot_monto;
	this.conceptoFactura=conceptoFactura;
	this.idProceso=idProceso;
	this.fecha=fecha;
	this.inicioRango=inicioRango;
	this.finalRango=finalRango;
	this.totalRango=totalRango;
}


public String getConceptoFactura() {
	return conceptoFactura;
}
public void setConceptoFactura(String conceptoFactura) {
	this.conceptoFactura = conceptoFactura;
}
public String getIdProceso() {
	return idProceso;
}
public void setIdProceso(String idProceso) {
	this.idProceso = idProceso;
}
public String getFecha() {
	return fecha;
}
public void setFecha(String fecha) {
	this.fecha = fecha;
}
public String getInicioRango() {
	return inicioRango;
}
public void setInicioRango(String inicioRango) {
	this.inicioRango = inicioRango;
}
public String getFinalRango() {
	return finalRango;
}
public void setFinalRango(String finalRango) {
	this.finalRango = finalRango;
}
public String getTotalRango() {
	return totalRango;
}
public void setTotalRango(String totalRango) {
	this.totalRango = totalRango;
}
public String getTipoCons() {
	return tipoCons;
}
public void setTipoCons(String tipoCons) {
	this.tipoCons = tipoCons;
}
public String getMesInicio() {
	return mesInicio;
}
public void setMesInicio(String mesInicio) {
	this.mesInicio = mesInicio;
}
public String getAnioInicio() {
	return anioInicio;
}
public void setAnioInicio(String anioInicio) {
	this.anioInicio = anioInicio;
}
public String getMesfin() {
	return mesfin;
}
public void setMesfin(String mesfin) {
	this.mesfin = mesfin;
}
public String getAniofin() {
	return aniofin;
}
public void setAniofin(String aniofin) {
	this.aniofin = aniofin;
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
public String getTot_portados() {
	return tot_portados;
}
public void setTot_portados(String totPortados) {
	tot_portados = totPortados;
}
public String getTot_retornos() {
	return tot_retornos;
}
public void setTot_retornos(String totRetornos) {
	tot_retornos = totRetornos;
}
public String getTot_objeciones() {
	return tot_objeciones;
}
public void setTot_objeciones(String totObjeciones) {
	tot_objeciones = totObjeciones;
}
public String getTot_monto() {
	return tot_monto;
}
public void setTot_monto(String totMonto) {
	tot_monto = totMonto;
}
public String getPeriodoInicio() {
	return periodoInicio;
}
public void setPeriodoInicio(String periodoInicio) {
	this.periodoInicio = periodoInicio;
}
public String getPeriodoFin() {
	return periodoFin;
}
public void setPeriodoFin(String periodoFin) {
	this.periodoFin = periodoFin;
}
public List getListaPreFacturacion() {
	return listaPreFacturacion;
}
public void setListaPreFacturacion(List listaPreFacturacion) {
	this.listaPreFacturacion = listaPreFacturacion;
}
public String getMonto() {
	return monto;
}
public void setMonto(String monto) {
	this.monto = monto;
}

public String getOperador() {
	return operador;
}
public void setOperador(String operador) {
	this.operador = operador;
}
public String getPortados() {
	return portados;
}
public void setPortados(String portados) {
	this.portados = portados;
}
public String getRetornos() {
	return retornos;
}
public void setRetornos(String retornos) {
	this.retornos = retornos;
}
public String getObjeciones() {
	return objeciones;
}
public void setObjeciones(String objeciones) {
	this.objeciones = objeciones;
}



}
