package pe.com.nextel.provisioning.model.bo;

import java.io.File;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.PageContext;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.Region;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.model.dao.JobReporteDAO;
import pe.com.nextel.provisioning.model.vo.JobReporteVO; 
import pe.com.nextel.provisioning.model.vo.PreFacturacionVO;
import pe.com.nextel.provisioning.model.vo.ReporteVO;
import pe.com.nextel.provisioning.util.BundleGeneric;
import pe.com.nextel.provisioning.util.Email;
import pe.com.nextel.provisioning.util.ZipCreate;

public class ReporteBO {
private static final Log log = LogFactory.getLog(ReporteBO.class);
private static ReporteBO single = null;
public ReporteBO(){}
	
	public static ReporteBO getInstance(){
		if (single == null)
			single = new ReporteBO();
		return single;
	}
	
	public static List<ReporteVO> sizeColumnlistaExcel(int e){		
		List<ReporteVO> lista = new ArrayList<ReporteVO>();
		if (e==1){
		for(int i=0;i<10;i++){
			ReporteVO  report = new ReporteVO();
			report.setOperador("22");
			report.setEmisionFactura("01/03/2010");
			report.setPortador("22222");
			report.setRetornos("010000");
			report.setObjeciones("010000");								
			lista.add(report);			
		}}
		if (e==2){
			for(int i=0;i<10;i++){
				ReporteVO  report = new ReporteVO();
				report.setOperador("22");
				report.setConceptoFactura("01 - Portado");
				report.setIdProceso("20201002010100001");
				report.setFecha("01/02/2010");
				report.setRangoInicial("999331867");								
				report.setRangoFinal("999331867");
				report.setTotalRango("11");
				report.setMensaje("REC01ABD08");
				lista.add(report);			
			}}
		if (e==3){
			for(int i=0;i<10;i++){
				ReporteVO  report = new ReporteVO();
				report.setNumero("989515379");
				report.setDonante("21");							
				report.setReceptor("22");
				report.setDonante_inicial("21");
				report.setUltimo_proceso("22201002010100001");
				report.setNumero_secuencial("22201002010100001");
				report.setInicio_proceso("01/02/2010 08:08:42");
				report.setInicio_ventana("05/02/2010 00:01:00");
				report.setDuracion_ventana("180");
				report.setEstado("01");
				report.setTimestamp_modificacion("02/02/2010 17:08:35");
				report.setDonante_previo("21");
				report.setTipoPortabilidad("01");
				lista.add(report);			
			}}
		if (e==4){
			for(int i=0;i<10;i++){
				ReporteVO  report = new ReporteVO();
				report.setIdProceso("21201001040100814");
				report.setOperadorReceptor("21 - América Móvil, S.A.C.");
				report.setOperadorCedente("22 - Telefónica Móviles S.A.");
				report.setTipoDocumento("DNI");
				report.setNumeroDocumento("10866971");
				report.setRangoInicial("985257707");
				report.setRangoFinal("985257707");
				report.setTipoPortabilidad("01 - Móvil prepago");
				report.setFechaHoraMensajeSp("04/01/2010 20:15:13");
				lista.add(report);
			}}
		
		
		
		return lista;
	}
	
	public void ejecutarJobReporte() throws Exception {
		  Email email =new Email();
		  String archivoXls=BundleGeneric.getBundle("ruta");
		  String archivoTxt=BundleGeneric.getBundle("rutatxt");
		  String archivoZip=BundleGeneric.getBundle("rutazip");
		  File file=null;
		  String []fileAdjunto=new String[1];
		  fileAdjunto[0]=archivoZip;
		  String contenidoMensaje="";
		  //BUSCAR REGISTROS CON INDICADORPROCESO=0		  
		  List <JobReporteVO>report=JobReporteDAO.getInstance().consultaJobReporte();
		  System.out.println("LA CANTIDAD DE LINEAS ES " + report.size());
		  JobReporteVO reporte=null;
		  if (report.size()>0){
				for (int i=0;i<report.size();i++){
					JobReporteVO valores=report.get(i);
					reporte=new JobReporteVO();
					reporte.setIdjob(valores.getIdjob());
					reporte.setFechaInicio(valores.getFechaInicio());
					reporte.setFechaFin(valores.getFechaFin());
					reporte.setTipoReporte(valores.getTipoReporte());
					reporte.setIdrepor(valores.getIdrepor());
				}				
		  if (reporte.getIdjob()==null){reporte.setIdjob("");}
		  if (reporte.getIdjob().length()>0){
		  log.info("Inicio::-------------ReporteJob--------------"+reporte.getIdrepor());	  
		  // OBTENER LA LISTA DEL PORTAFLOW
		  String updJobReport01=JobReporteDAO.getInstance().ActualizarJobReporte(reporte,"1");
		  List<ReporteVO> listaExcel =JobReporteDAO.getInstance().listaJobReporte(reporte);
		  log.info("Total Registros listaExcel="+listaExcel.size());
		  if (listaExcel.size()>=0){			  
			  if ("1".equals(reporte.getIdrepor())){
				  file=new File(archivoXls);
				  contenidoMensaje="Se envía el "+reporte.getTipoReporte()+" con el siguiente " +
				  				   "rango de fechas :"+reporte.getFechaInicio()+" al "+reporte.getFechaFin();
			  }else if ("2".equals(reporte.getIdrepor()) && (listaExcel.size()>15000)){
				  file=new File(archivoTxt);
				  contenidoMensaje="Se envía el "+reporte.getTipoReporte();
			  }else if ("2".equals(reporte.getIdrepor()) && (listaExcel.size()<15000)){
				  file=new File(archivoXls);
				  contenidoMensaje="Se envía el "+reporte.getTipoReporte();
			  }else if ("3".equals(reporte.getIdrepor())){
				  file=new File(archivoXls);
				  contenidoMensaje="Se envía el Consolidado:"+reporte.getTipoReporte()+" con el siguiente " +
 				   				   "rango de fechas :"+reporte.getFechaInicio()+" al "+reporte.getFechaFin();		  	  
			  }else if ("4".equals(reporte.getIdrepor())){
				  file=new File(archivoXls);
				  contenidoMensaje="Se envía el "+reporte.getTipoReporte()+" con el siguiente " +
   				   "rango de fechas :"+reporte.getFechaInicio()+" al "+reporte.getFechaFin();		  	  
			  }else if ("5".equals(reporte.getIdrepor())){
				  file=new File(archivoXls);
				  contenidoMensaje="Se envía el "+reporte.getTipoReporte()+" con el siguiente " +
   				   "rango de fechas :"+reporte.getFechaInicio()+" al "+reporte.getFechaFin();		  	  
			  }else{
				  file=new File(archivoXls);
			  }		  
		  log.info("updJobReport01="+updJobReport01);
		  if ("OK".equals(updJobReport01)){
	      ExportarArchivoExcel(listaExcel, reporte);		  		  
		  if (file.exists()){
		  ZipCreate.ZipFile(file,archivoZip);
		  if (new File(archivoZip).exists()){	  
		  /*
		  email.sendEmail("hmiramira@comsa.com.pe",//BundleGeneric.getBundle("CORREO_DIRIGIDO_A");
		  "snavarro@comsa.com.pe,wpicoy@comsa.com.pe",			  
		  "Reporte Solicitado - Portabilidad Numerica",
		  contenidoMensaje,
		  fileAdjunto);	*/	
		    
		  
		  email.sendEmail("snavarro@comsa.com.pe","fjulcamanyan@comsa.com.pe,manaya@comsa.com.pe",
		  "Reporte Solicitado - Portabilidad Numerica", contenidoMensaje, fileAdjunto);
      //email.sendEmail("jramirez@comsa.com.pe,fjulcamanyan@comsa.com.pe",
      //"Reporte Solicitado - Portabilidad Numerica", contenidoMensaje, fileAdjunto);
		  String updJobReport02=JobReporteDAO.getInstance().ActualizarJobReporte(reporte,"2");
		  log.info("Se cierra el flujo de creacion y envío:::updJobReport02="+updJobReport02);
		  }else{
		  log.info("ERROR AL CREAR EL ARCHIVO ZIP..."); 
		  String updJobReportError=JobReporteDAO.getInstance().ActualizarJobReporte(reporte,"3");
		  }}else{
		  log.info("ERROR AL CREAR EL ARCHIVO EXCEL..."); 
		  String updJobReportError=JobReporteDAO.getInstance().ActualizarJobReporte(reporte,"3");
		  }
		  }
		  }
		  log.info("Fin::-------------------------------");				
		  } 
		  }
	  }
	
	
	
	public void ejecutarConsulta() throws Exception {
		  Email email =new Email();
		  String archivoXls=BundleGeneric.getBundle("ruta");
		  String archivoTxt=BundleGeneric.getBundle("rutatxt");
		  String archivoZip=BundleGeneric.getBundle("rutazip");
		  File file=null;
		  String []fileAdjunto=new String[1];
		  fileAdjunto[0]=archivoZip;
		  String contenidoMensaje="";
		  //BUSCAR REGISTROS CON INDICADORPROCESO=0		  
		  List <JobReporteVO>report=JobReporteDAO.getInstance().consultaJobReporte();
		  JobReporteVO reporte=null;
		  if (report.size()>0){
				for (int i=0;i<report.size();i++){
					JobReporteVO valores=report.get(i);
					reporte=new JobReporteVO();
					reporte.setIdjob(valores.getIdjob());
					reporte.setFechaInicio(valores.getFechaInicio());
					reporte.setFechaFin(valores.getFechaFin());
					reporte.setTipoReporte(valores.getTipoReporte());
					reporte.setIdrepor(valores.getIdrepor());
				}				
		  if (reporte.getIdjob()==null){reporte.setIdjob("");}
		  if (reporte.getIdjob().length()>0){
		  log.info("Inicio::-------------ReporteJob--------------"+reporte.getIdrepor());	  
		  // OBTENER LA LISTA DEL PORTAFLOW
		  String updJobReport01=JobReporteDAO.getInstance().ActualizarJobReporte(reporte,"1");
		  List<ReporteVO> listaExcel =JobReporteDAO.getInstance().listaJobReporte(reporte);
		  log.info("Total Registros listaExcel="+listaExcel.size());
		  if (listaExcel.size()>=0){			  
			  if ("1".equals(reporte.getIdrepor())){
				  file=new File(archivoXls);
				  contenidoMensaje="Se envía el "+reporte.getTipoReporte()+" con el siguiente " +
				  				   "rango de fechas :"+reporte.getFechaInicio()+" al "+reporte.getFechaFin();
			  }else if ("2".equals(reporte.getIdrepor()) && (listaExcel.size()>15000)){
				  file=new File(archivoTxt);
				  contenidoMensaje="Se envía el "+reporte.getTipoReporte();
			  }else if ("2".equals(reporte.getIdrepor()) && (listaExcel.size()<15000)){
				  file=new File(archivoXls);
				  contenidoMensaje="Se envía el "+reporte.getTipoReporte();
			  }else if ("3".equals(reporte.getIdrepor())){
				  file=new File(archivoXls);
				  contenidoMensaje="Se envía el Consolidado:"+reporte.getTipoReporte()+" con el siguiente " +
				   				   "rango de fechas :"+reporte.getFechaInicio()+" al "+reporte.getFechaFin();		  	  
			  }else{
				  file=new File(archivoXls);
			  }		  
		  log.info("updJobReport01="+updJobReport01);
		  if ("OK".equals(updJobReport01)){
	      ExportarArchivoExcel(listaExcel, reporte);		  		  
		  if (file.exists()){
		  ZipCreate.ZipFile(file,archivoZip);
		  if (new File(archivoZip).exists()){	  
		  /*
		  email.sendEmail("hmiramira@comsa.com.pe",//BundleGeneric.getBundle("CORREO_DIRIGIDO_A");
		  "snavarro@comsa.com.pe,wpicoy@comsa.com.pe",			  
		  "Reporte Solicitado - Portabilidad Numerica",
		  contenidoMensaje,
		  fileAdjunto);	*/	
		    
		  email.sendEmail("centroatencionportabilidad@comsa.com.pe","hmiramira@comsa.com.pe", 
		  "Reporte Solicitado - Portabilidad Numerica", contenidoMensaje, fileAdjunto);
		  String updJobReport02=JobReporteDAO.getInstance().ActualizarJobReporte(reporte,"2");
		  log.info("Se cierra el flujo de creacion y envío:::updJobReport02="+updJobReport02);
		  }else{
		  log.info("ERROR AL CREAR EL ARCHIVO ZIP..."); 
		  String updJobReportError=JobReporteDAO.getInstance().ActualizarJobReporte(reporte,"3");
		  }}else{
		  log.info("ERROR AL CREAR EL ARCHIVO EXCEL..."); 
		  String updJobReportError=JobReporteDAO.getInstance().ActualizarJobReporte(reporte,"3");
		  }
		  }
		  }
		  log.info("Fin::-------------------------------");				
		  } 
		  }
	  }
	
	
	public void ExportarArchivoExcel(List<ReporteVO> listaExcel, JobReporteVO tituloCabecera) throws Exception{
		System.out.println("cabecera tipo_reporte="+tituloCabecera.getIdrepor());
		if ("1".equals(tituloCabecera.getIdrepor())){
			generarArchivoExcelOsiptel(listaExcel,tituloCabecera);
		}if ("2".equals(tituloCabecera.getIdrepor())){
			generarArchivoExcelNumeracionPortada(listaExcel,tituloCabecera);
		}if ("3".equals(tituloCabecera.getIdrepor())){
			ExportarConsolidadoFacturacion(listaExcel,tituloCabecera);
		}if ("4".equals(tituloCabecera.getIdrepor())){
			generarArchivoExcelRechazos(listaExcel,tituloCabecera);
		}if ("5".equals(tituloCabecera.getIdrepor())){
			generarArchivoExcelVabdcp(listaExcel,tituloCabecera);
		}
	}
	
	public void ExportarConsolidadoFacturacion(List<ReporteVO> listaExcel, JobReporteVO tituloCabecera) throws Exception{
		log.info("ExportarConsolidadoFacturacion");
		List<ReporteVO> listaDetalle20=JobReporteDAO.getInstance().consultaDetalleFacturacion(tituloCabecera,"20");
		log.info("listaDetalle20:"+listaDetalle20.size());
		List<ReporteVO> listaDetalle22=JobReporteDAO.getInstance().consultaDetalleFacturacion(tituloCabecera,"22");
		log.info("listaDetalle22:"+listaDetalle22.size());
		List<ReporteVO> listaDetalle21=JobReporteDAO.getInstance().consultaDetalleFacturacion(tituloCabecera,"21");
		log.info("listaDetalle21:"+listaDetalle21.size());
		generarDetalleFacturacionExcel(tituloCabecera,listaExcel,listaDetalle20,listaDetalle21,listaDetalle22);
		log.info("ExportarConsolidadoFacturacion");
	}
	
	public void generarArchivoExcelOsiptel(List<ReporteVO> listaExcel, JobReporteVO tituloCabecera) throws IOException{

		HSSFWorkbook 	libro = new HSSFWorkbook();
		HSSFSheet 		hoja =  libro.createSheet(BundleGeneric.getBundle("hoja1"));	
		HSSFSheet 		hoja2 = libro.createSheet(BundleGeneric.getBundle("hoja2"));
		HSSFSheet 		hoja3 = libro.createSheet(BundleGeneric.getBundle("hoja3"));

		HSSFFont fuenteCabecera = libro.createFont();
		fuenteCabecera.setFontHeightInPoints((short)11);
		fuenteCabecera.setFontName(fuenteCabecera.FONT_ARIAL);
		fuenteCabecera.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		 
		HSSFCellStyle estyloTitulo = libro.createCellStyle();
		estyloTitulo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estyloTitulo.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		estyloTitulo.setFont(fuenteCabecera);	
		
		HSSFCellStyle estyloCabecera = libro.createCellStyle();
		estyloCabecera.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		estyloCabecera.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		estyloCabecera.setFont(fuenteCabecera);	
		
		
		HSSFCell celda;
		HSSFRow fila;
		int i =0;
		
		fila = hoja.createRow(i++);
		String [] cabeceraTitulo =		{BundleGeneric.getBundle("titulo")};
		for(int j=0; j < cabeceraTitulo.length;j++){
			celda = fila.createCell(j);
			celda.setCellStyle(estyloTitulo);
			celda.setCellValue(cabeceraTitulo[j]);	
		}
	 
		String [] cabecerafechaInicio =	{"Fecha Inicio",tituloCabecera.getFechaInicio()};
		fila = hoja.createRow(i++);
		for(int k=0; k < cabecerafechaInicio.length;k++){
			celda = fila.createCell(k);
			celda.setCellValue(cabecerafechaInicio[k]);
		}
			
		String [] cabecerafechaFinal =	{"Fecha Fin",tituloCabecera.getFechaFin()};
		fila = hoja.createRow(i++);	
		for(int m=0; m < cabecerafechaFinal.length;m++){
			celda = fila.createCell(m);
			celda.setCellValue(cabecerafechaFinal[m]);
		}
			
		String [] cabeceraTipoReporte =	{"Tipo Reporte", tituloCabecera.getTipoReporte()};
		fila = hoja.createRow(i++);
		hoja.addMergedRegion(new Region(0,(short)0,0,(short)8));
		for(int n=0; n < cabeceraTipoReporte.length;n++){
			celda = fila.createCell(n);
			celda.setCellValue(cabeceraTipoReporte[n]);
		}
		String [] cabeceraHoja = { "Id Proceso","Operador Receptor", "Operador Cedente", "Tipo documento", "N° documento",
				"Rango Inicial", "Rango Final", "Tipo Portabilidad", "Fecha hora mensaje SP","Ultimo Mensaje","Causa Rechazo"};
		
		
		fila = hoja.createRow(i++);
		for(int o=0; o < cabeceraHoja.length;o++){
		celda = fila.createCell(o);
		celda.setCellStyle(estyloTitulo);
		celda.setCellValue(cabeceraHoja[o]);
		}
			
		for(ReporteVO k :listaExcel ){
		fila = hoja.createRow(i++);
		fila.createCell(0).setCellValue(k.getIdProceso());
		fila.createCell(1).setCellValue(k.getOperadorReceptor());
		fila.createCell(2).setCellValue(k.getOperadorCedente());
		fila.createCell(3).setCellValue(k.getTipoDocumento());
		fila.createCell(4).setCellValue(k.getNumeroDocumento());
		fila.createCell(5).setCellValue(k.getRangoInicial());
		fila.createCell(6).setCellValue(k.getRangoFinal());
		fila.createCell(7).setCellValue(k.getTipoPortabilidad());
		fila.createCell(8).setCellValue(k.getFechaHoraMensajeSp());
		fila.createCell(9).setCellValue(k.getUltimoMensaje());
		fila.createCell(10).setCellValue(k.getCausaRechazo());
		}
		for (int h = 0; h <sizeColumnlistaExcel(4).size(); h++) {		  
			hoja.autoSizeColumn((short) h);				
			}	
		
		String strNombreArchivo =  BundleGeneric.getBundle("ruta");
		File filita = new File(strNombreArchivo);
		FileOutputStream archivoSalida = new FileOutputStream(filita);
		libro.write(archivoSalida);
		archivoSalida.close();

		}
	
	public void generarArchivoExcelNumeracionPortada(List<ReporteVO> listaExcel, JobReporteVO tituloCabecera) throws IOException{		
		if (listaExcel.size()>15000){ 
		try {
			FileWriter fw = new FileWriter(BundleGeneric.getBundle("rutatxt"));
			BufferedWriter bw = new BufferedWriter(fw);
		    PrintWriter salida = new PrintWriter(bw);		    
		    salida.println("						"+tituloCabecera.getTipoReporte().replace("ó","o")+"				");		      
		    salida.println("");	      
		    salida.println("Numero	Donante	Receptor	DonanteInicial	UltimoProceso	NumeroSecuencial	InicioProceso	InicioVentana	DuracionVentana	Estado	Timestamp_Modificacion	DonantePrevio	TipoPortabilidad");
			for(int i=0; i<listaExcel.size(); i++){				  
				ReporteVO k=(ReporteVO)listaExcel.get(i);
				
				k.setNumero(k.getNumero()!=null?k.getNumero():"");
				k.setDonante(k.getDonante()!=null?k.getDonante():"");
				k.setReceptor(k.getReceptor()!=null?k.getReceptor():"");
				k.setDonante_inicial(k.getDonante_inicial()!=null?k.getDonante_inicial():"");
				k.setUltimo_proceso(k.getUltimo_proceso()!=null?k.getUltimo_proceso():"");
				k.setNumero_secuencial(k.getNumero_secuencial()!=null?k.getNumero_secuencial():"");
				k.setInicio_proceso(k.getInicio_proceso()!=null?k.getInicio_proceso():"");
				k.setInicio_ventana(k.getInicio_ventana()!=null?k.getInicio_ventana():"");
				k.setDuracion_ventana(k.getDuracion_ventana()!=null?k.getDuracion_ventana():"");
				k.setEstado(k.getEstado()!=null?k.getEstado():"");
				k.setTimestamp_modificacion(k.getTimestamp_modificacion()!=null?k.getTimestamp_modificacion():"");
				k.setDonante_previo(k.getDonante_previo()!=null? k.getDonante_previo():"");
			    k.setTipoPortabilidad(k.getTipoPortabilidad()!=null?k.getTipoPortabilidad():"");
				
				salida.println(k.getNumero()+"	"+
							   k.getDonante()+"	"+
							   k.getReceptor()+"	"+
							   k.getDonante_inicial()+"	"+
							   k.getUltimo_proceso()+"	"+
							   k.getNumero_secuencial()+"	"+
							   k.getInicio_proceso()+"	"+
							   k.getInicio_ventana()+"	"+
							   k.getDuracion_ventana()+"	"+
							   k.getEstado()+"	"+
							   k.getTimestamp_modificacion()+"	"+
							   k.getDonante_previo()+"	"+
							   k.getTipoPortabilidad());  		 
			  }		
		    salida.close();
		} catch (IOException e) {e.printStackTrace();
		}
		}else{
		
			String [] cabeceraHoja = {"Numero","Donante","Rceptor","DonanteInicial",
					"UltimoProceso","NumeroSecuencial","InicioProceso",
					"InicioVentana","DuracionVentana","Estado",
					"Timestamp_Modificacion","DonantePrevio","TipoPortabilidad"};	      
			HSSFWorkbook 	libro = new HSSFWorkbook();
			HSSFSheet 		hoja =  libro.createSheet(BundleGeneric.getBundle("hoja1"));
		
			HSSFFont fuenteCabecera = libro.createFont();
			fuenteCabecera.setFontHeightInPoints((short)11);
			fuenteCabecera.setFontName(fuenteCabecera.FONT_ARIAL);
			fuenteCabecera.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);			 
			HSSFCellStyle estyloTitulo = libro.createCellStyle();
			estyloTitulo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			estyloTitulo.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
			estyloTitulo.setFont(fuenteCabecera);	
			
			HSSFCellStyle estyloCabecera = libro.createCellStyle();
			estyloCabecera.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			estyloCabecera.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
			estyloCabecera.setFont(fuenteCabecera);
			
			HSSFCell celda;
			HSSFRow fila;
			int i =0;
			
			fila = hoja.createRow(i++);
			String [] cabeceraTitulo =		{tituloCabecera.getTipoReporte()};
			for(int j=0; j < cabeceraTitulo.length;j++){
				celda = fila.createCell(j);
				celda.setCellStyle(estyloTitulo);
				celda.setCellValue(cabeceraTitulo[j]);	
			}		
			fila = hoja.createRow(i++);
			for(int o=0; o < cabeceraHoja.length;o++){
			celda = fila.createCell(o);
			celda.setCellStyle(estyloTitulo);
			celda.setCellValue(cabeceraHoja[o]);
			}
			
			/*long total=listaExcel.size();	
			int restarSobrante=0;
			int[] contab = new int[5];
			int[] valores=new int[5]; 
			restarSobrante=(int)total;
			int valoresResta=restarSobrante;
			int autoincrement=10000;
			System.out.println("tamaño a exportar01="+total);
			System.out.println("tamaño a exportar02="+restarSobrante);
			if (restarSobrante>13000){
				for (int t=0;t<contab.length;t++){			
					if (valoresResta>13000){
						valoresResta=valoresResta-autoincrement;				
						valores[t]=autoincrement;
					}else{
					valores[t]=valoresResta;
					valoresResta=0;
					}
				}	
			}	
			System.out.println("i=="+i);
			*/
			for(ReporteVO k :listaExcel ){
			fila = hoja.createRow(i++);
			fila.createCell(0).setCellValue(k.getNumero());
			fila.createCell(1).setCellValue(k.getDonante());
			fila.createCell(2).setCellValue(k.getReceptor());
			fila.createCell(3).setCellValue(k.getDonante_inicial());
			fila.createCell(4).setCellValue(k.getUltimo_proceso());
			fila.createCell(5).setCellValue(k.getNumero_secuencial());
			fila.createCell(6).setCellValue(k.getInicio_proceso());
			fila.createCell(7).setCellValue(k.getInicio_ventana());
			fila.createCell(8).setCellValue(k.getDuracion_ventana());
			fila.createCell(9).setCellValue(k.getEstado());
			fila.createCell(10).setCellValue(k.getTimestamp_modificacion());
			fila.createCell(11).setCellValue(k.getDonante_previo());
			fila.createCell(12).setCellValue(k.getTipoPortabilidad());	
			}
			
			for (int h = 0; h <sizeColumnlistaExcel(3).size(); h++) {			  
				hoja.autoSizeColumn((short) h);					
				}	
			
			String strNombreArchivo =  BundleGeneric.getBundle("ruta");
			File filita = new File(strNombreArchivo);
			FileOutputStream archivoSalida = new FileOutputStream(filita);
			libro.write(archivoSalida);
			archivoSalida.close();	
		}
	} 
	
	public void generarArchivoExcelRechazos(List<ReporteVO> listaExcel, JobReporteVO tituloCabecera) throws IOException{		

			String [] cabeceraHoja = {"Numero","Receptor","Cedente","NumeroSecuencial",
					"Id_Proceso","Fecha_registro","Causa_Rechazo","Fecha_Eabdcp"};	      
			HSSFWorkbook 	libro = new HSSFWorkbook();
			HSSFSheet 		hoja =  libro.createSheet(BundleGeneric.getBundle("hoja1"));
		
			HSSFFont fuenteCabecera = libro.createFont();
			fuenteCabecera.setFontHeightInPoints((short)11);
			fuenteCabecera.setFontName(fuenteCabecera.FONT_ARIAL);
			fuenteCabecera.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);			 
			HSSFCellStyle estyloTitulo = libro.createCellStyle();
			estyloTitulo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			estyloTitulo.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
			estyloTitulo.setFont(fuenteCabecera);	
			
			HSSFCellStyle estyloCabecera = libro.createCellStyle();
			estyloCabecera.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
			estyloCabecera.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
			estyloCabecera.setFont(fuenteCabecera);
			
			HSSFCell celda;
			HSSFRow fila;
			int i =0;
			
			fila = hoja.createRow(i++);
			String [] cabeceraTitulo =		{tituloCabecera.getTipoReporte()};
			for(int j=0; j < cabeceraTitulo.length;j++){
				celda = fila.createCell(j);
				celda.setCellStyle(estyloTitulo);
				celda.setCellValue(cabeceraTitulo[j]);	
			}		
			fila = hoja.createRow(i++);
			for(int o=0; o < cabeceraHoja.length;o++){
			celda = fila.createCell(o);
			celda.setCellStyle(estyloTitulo);
			celda.setCellValue(cabeceraHoja[o]);
			}
			
			for(ReporteVO k :listaExcel ){
			fila = hoja.createRow(i++);
			fila.createCell(0).setCellValue(k.getNumero());
			fila.createCell(1).setCellValue(k.getReceptor());
			fila.createCell(2).setCellValue(k.getDonante());
			fila.createCell(3).setCellValue(k.getNumero_secuencial());
			fila.createCell(4).setCellValue(k.getUltimo_proceso());
			fila.createCell(5).setCellValue(k.getFecha());
			fila.createCell(6).setCellValue(k.getCausaRechazo());
			fila.createCell(7).setCellValue(k.getFechaEabdcp());
			}
			
			for (int h = 0; h <sizeColumnlistaExcel(3).size(); h++) {			  
				hoja.autoSizeColumn((short) h);					
				}	
			
			String strNombreArchivo =  BundleGeneric.getBundle("ruta");
			File filita = new File(strNombreArchivo);
			FileOutputStream archivoSalida = new FileOutputStream(filita);
			libro.write(archivoSalida);
			archivoSalida.close();	
		
	} 
	  
	
	public void generarArchivoExcelVabdcp(List<ReporteVO> listaExcel, JobReporteVO tituloCabecera) throws IOException{		

		String [] cabeceraHoja = {"Numero","Receptor","Cedente","NumeroSecuencial",
				"Id_Proceso","Fecha_registro","Tipo_Mensaje","Ultimo_Mensaje"};	      
		HSSFWorkbook 	libro = new HSSFWorkbook();
		HSSFSheet 		hoja =  libro.createSheet(BundleGeneric.getBundle("hoja1"));
	
		HSSFFont fuenteCabecera = libro.createFont();
		fuenteCabecera.setFontHeightInPoints((short)11);
		fuenteCabecera.setFontName(fuenteCabecera.FONT_ARIAL);
		fuenteCabecera.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);			 
		HSSFCellStyle estyloTitulo = libro.createCellStyle();
		estyloTitulo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estyloTitulo.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		estyloTitulo.setFont(fuenteCabecera);	
		
		HSSFCellStyle estyloCabecera = libro.createCellStyle();
		estyloCabecera.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		estyloCabecera.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		estyloCabecera.setFont(fuenteCabecera);
		
		HSSFCell celda;
		HSSFRow fila;
		int i =0;
		
		fila = hoja.createRow(i++);
		String [] cabeceraTitulo =		{tituloCabecera.getTipoReporte()};
		for(int j=0; j < cabeceraTitulo.length;j++){
			celda = fila.createCell(j);
			celda.setCellStyle(estyloTitulo);
			celda.setCellValue(cabeceraTitulo[j]);	
		}		
		fila = hoja.createRow(i++);
		for(int o=0; o < cabeceraHoja.length;o++){
		celda = fila.createCell(o);
		celda.setCellStyle(estyloTitulo);
		celda.setCellValue(cabeceraHoja[o]);
		}

		//Número, Receptor,Cedente,Numero_Secuencial,Id_Proceso,Fecha_Registro,Tipo_Mensaje,Ultimo_Mensaje

		for(ReporteVO k :listaExcel ){
		fila = hoja.createRow(i++);
		fila.createCell(0).setCellValue(k.getNumero());
		fila.createCell(1).setCellValue(k.getReceptor());
		fila.createCell(2).setCellValue(k.getDonante());
		fila.createCell(3).setCellValue(k.getNumero_secuencial());
		fila.createCell(4).setCellValue(k.getUltimo_proceso());
		fila.createCell(5).setCellValue(k.getFecha());
		fila.createCell(6).setCellValue(k.getMensaje());
		fila.createCell(7).setCellValue(k.getUltimoMensaje());
		}
		
		for (int h = 0; h <sizeColumnlistaExcel(3).size(); h++) {			  
			hoja.autoSizeColumn((short) h);					
			}	
		
		String strNombreArchivo =  BundleGeneric.getBundle("ruta");
		File filita = new File(strNombreArchivo);
		FileOutputStream archivoSalida = new FileOutputStream(filita);
		libro.write(archivoSalida);
		archivoSalida.close();	
	
} 
  


	public void generarDetalleFacturacionExcel(JobReporteVO tituloCabecera,
											   List<ReporteVO> listaConsolidado,
											   List<ReporteVO> listaDetalle20,
											   List<ReporteVO> listaDetalle21,
											   List<ReporteVO> listaDetalle22
											   ) throws Exception{

		HSSFWorkbook 	libro = new HSSFWorkbook();
		HSSFSheet 		consolidado   =  libro.createSheet("Consolidado");
		HSSFSheet 		hoja   =  libro.createSheet("Nextel");
		HSSFSheet 		hoja02 =  libro.createSheet("Claro");
		HSSFSheet 		hoja03 =  libro.createSheet("Movistar");
		
		String [] cabeceraHoja = { "Operador","Concepto Factura", "IdProceso", "Fecha", "Inicio Rango",
				   				   "Rango Final ", "Total Rango","Mensaje"};
		String [] cabeceraCons = { "Operador","Portados","Rechazos","Retornos","Objeciones","Monto"};
		
		HSSFFont fuenteCabecera = libro.createFont();
		fuenteCabecera.setFontHeightInPoints((short)11);
		fuenteCabecera.setFontName(fuenteCabecera.FONT_ARIAL);
		fuenteCabecera.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle estyloTitulo = libro.createCellStyle();
		estyloTitulo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		estyloTitulo.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		estyloTitulo.setFont(fuenteCabecera);	
		
		HSSFCellStyle estyloCabecera = libro.createCellStyle();
		estyloCabecera.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
		estyloCabecera.setFillForegroundColor(new HSSFColor.WHITE().getIndex());
		estyloCabecera.setFont(fuenteCabecera);		
		
		HSSFCell celdaConsol;
		HSSFRow filaConsol;
		int i =0;
		
		filaConsol= consolidado.createRow(i++);
		String [] cabeceraTituloConsol ={"REPORTE CONSOLIDADO FACTURACION"};
		consolidado.addMergedRegion(new Region(0,(short)0,0,(short)8));
		for(int j=0; j < cabeceraTituloConsol.length;j++){
		celdaConsol = filaConsol.createCell(j);
		celdaConsol.setCellStyle(estyloTitulo);
		celdaConsol.setCellValue(cabeceraTituloConsol[j]);	
		}	
		
		String [] cabecerafechaInicioS =	{"Fecha Inicio",tituloCabecera.getFechaInicio()};
		filaConsol = consolidado.createRow(i++);
		for(int k=0; k < cabecerafechaInicioS.length;k++){
			celdaConsol = filaConsol.createCell(k);
			celdaConsol.setCellValue(cabecerafechaInicioS[k]);
		}
			
		String [] cabecerafechaFinalS =	{"Fecha Fin",tituloCabecera.getFechaFin()};
		filaConsol = consolidado.createRow(i++);	
		for(int m=0; m < cabecerafechaFinalS.length;m++){
			celdaConsol = filaConsol.createCell(m);
			celdaConsol.setCellValue(cabecerafechaFinalS[m]);
		}
		
		filaConsol = consolidado.createRow(i++);
		for(int o=0; o < cabeceraCons.length;o++){
		celdaConsol = filaConsol.createCell(o);
		celdaConsol.setCellStyle(estyloTitulo);
		celdaConsol.setCellValue(cabeceraCons[o]);
		}
		
		for(ReporteVO k :listaConsolidado){
		filaConsol = consolidado.createRow(i++);
		filaConsol.createCell(0).setCellValue(k.getOperador());
		filaConsol.createCell(1).setCellValue(k.getPortador());
		filaConsol.createCell(2).setCellValue(k.getRecAbd());
		filaConsol.createCell(3).setCellValue(k.getRetornos());
		filaConsol.createCell(4).setCellValue(k.getObjeciones());				
		filaConsol.createCell(5).setCellValue(k.getMonto());
		}
		
		for (int o = 0; o <sizeColumnlistaExcel(1).size(); o++) {  
		consolidado.autoSizeColumn((short) o);			
		}
		//----------------------------
		
		/****/
		HSSFCell celda;
		HSSFRow fila;
		i =0;
		fila= hoja.createRow(i++);
		String [] cabeceraTitulo ={"REPORTE DETALLADO DEL OPERADOR :NEXTEL-20"};
		hoja.addMergedRegion(new Region(0,(short)0,0,(short)8));
		for(int j=0; j < cabeceraTitulo.length;j++){
		celda = fila.createCell(j);
		celda.setCellStyle(estyloTitulo);
		celda.setCellValue(cabeceraTitulo[j]);	
		} 
		String [] cabecerafechaInicio =	{"Fecha Inicio",tituloCabecera.getFechaInicio()};
		fila= hoja.createRow(i++);
		for(int k=0; k < cabecerafechaInicio.length;k++){
		celda = fila.createCell(k);
		celda.setCellValue(cabecerafechaInicio[k]);
		}
		String [] cabecerafechaFinal =	{"Fecha Fin",tituloCabecera.getFechaFin()};
		fila = hoja.createRow(i++);	
		for(int m=0; m < cabecerafechaFinal.length;m++){
		celda = fila.createCell(m);
		celda.setCellValue(cabecerafechaFinal[m]);
		}
		
		fila = hoja.createRow(i++);
		for(int o=0; o < cabeceraHoja.length;o++){
		celda = fila.createCell(o);
		celda.setCellStyle(estyloTitulo);
		celda.setCellValue(cabeceraHoja[o]);
		}
		
		for(ReporteVO k :listaDetalle20){
		fila = hoja.createRow(i++);
		fila.createCell(0).setCellValue(k.getOperador());
		fila.createCell(1).setCellValue(k.getConceptoFactura());
		fila.createCell(2).setCellValue(k.getIdProceso());		
		fila.createCell(3).setCellValue(k.getFecha());	
		fila.createCell(4).setCellValue(k.getRangoInicial());
		fila.createCell(5).setCellValue(k.getRangoFinal());
		fila.createCell(6).setCellValue(k.getTotalRango());
		fila.createCell(7).setCellValue(k.getMensaje());
		}	
		for (int o = 0; o <sizeColumnlistaExcel(2).size(); o++) {  
		hoja.autoSizeColumn((short) o);			
		}
		/*****/
		
		HSSFCell celda02;
		HSSFRow fila02;
		i =0;
		fila02= hoja02.createRow(i++);
		String[] cabeceraTitulos ={"REPORTE DETALLADO DEL OPERADOR :CLARO-21"};
		hoja02.addMergedRegion(new Region(0,(short)0,0,(short)8));
		for(int j=0; j < cabeceraTitulos.length;j++){
		celda02 = fila02.createCell(j);
		celda02.setCellStyle(estyloTitulo);
		celda02.setCellValue(cabeceraTitulos[j]);	
		} 
		String [] cabecerafechaInicios =	{"Fecha Inicio",tituloCabecera.getFechaInicio()};
		fila02= hoja02.createRow(i++);
		for(int k=0; k < cabecerafechaInicios.length;k++){
		celda02 = fila02.createCell(k);
		celda02.setCellValue(cabecerafechaInicios[k]);
		}		
		String [] cabecerafechaFinals =	{"Fecha Fin",tituloCabecera.getFechaFin()};
		fila02 = hoja02.createRow(i++);	
		for(int m=0; m < cabecerafechaFinals.length;m++){
		celda02 = fila02.createCell(m);
		celda02.setCellValue(cabecerafechaFinals[m]);
		}
		
		fila02 = hoja02.createRow(i++);
		for(int o=0; o < cabeceraHoja.length;o++){
		celda02 = fila02.createCell(o);
		celda02.setCellStyle(estyloTitulo);
		celda02.setCellValue(cabeceraHoja[o]);
		}
		
		for(ReporteVO k :listaDetalle21){
		fila02 = hoja02.createRow(i++);
		fila02.createCell(0).setCellValue(k.getOperador());
		fila02.createCell(1).setCellValue(k.getConceptoFactura());
		fila02.createCell(2).setCellValue(k.getIdProceso());		
		fila02.createCell(3).setCellValue(k.getFecha());	
		fila02.createCell(4).setCellValue(k.getRangoInicial());
		fila02.createCell(5).setCellValue(k.getRangoFinal());
		fila02.createCell(6).setCellValue(k.getTotalRango());
		fila02.createCell(7).setCellValue(k.getMensaje());	
		}	
		for (int o = 0; o <sizeColumnlistaExcel(2).size(); o++) {  
		hoja02.autoSizeColumn((short) o);			
		}
		/*****/
		
		HSSFCell celda03;
		HSSFRow fila03;
		i =0;
		fila03= hoja03.createRow(i++);
		String[] cabeceraTitul ={"REPORTE DETALLADO DEL OPERADOR :MOVISTAR-22"};
		hoja03.addMergedRegion(new Region(0,(short)0,0,(short)8));
		for(int j=0; j < cabeceraTitul.length;j++){
		celda03 = fila03.createCell(j);
		celda03.setCellStyle(estyloTitulo);
		celda03.setCellValue(cabeceraTitul[j]);	
		} 
		String [] cabecerafechaIni =	{"Fecha Inicio",tituloCabecera.getFechaInicio()};
		fila03= hoja03.createRow(i++);
		for(int k=0; k < cabecerafechaIni.length;k++){
		celda03 = fila03.createCell(k);
		celda03.setCellValue(cabecerafechaIni[k]);
		}		
		String [] cabecerafechaFin =	{"Fecha Fin",tituloCabecera.getFechaFin()};
		fila03 = hoja03.createRow(i++);	
		for(int m=0; m < cabecerafechaFin.length;m++){
		celda03 = fila03.createCell(m);
		celda03.setCellValue(cabecerafechaFin[m]);
		}
		
		fila03 = hoja03.createRow(i++);
		for(int o=0; o < cabeceraHoja.length;o++){
		celda03 = fila03.createCell(o);
		celda03.setCellStyle(estyloTitulo);
		celda03.setCellValue(cabeceraHoja[o]);
		}
		
		for(ReporteVO k :listaDetalle22){
		fila03 = hoja03.createRow(i++);
		fila03.createCell(0).setCellValue(k.getOperador());
		fila03.createCell(1).setCellValue(k.getConceptoFactura());
		fila03.createCell(2).setCellValue(k.getIdProceso());		
		fila03.createCell(3).setCellValue(k.getFecha());	
		fila03.createCell(4).setCellValue(k.getRangoInicial());
		fila03.createCell(5).setCellValue(k.getRangoFinal());
		fila03.createCell(6).setCellValue(k.getTotalRango());
		fila03.createCell(7).setCellValue(k.getMensaje());		
		}	
		for (int o = 0; o <sizeColumnlistaExcel(2).size(); o++) {  
		hoja03.autoSizeColumn((short) o);			
		}
		/*****/		
		String strNombreArchivo =  BundleGeneric.getBundle("ruta");
		File filita = new File(strNombreArchivo);
		FileOutputStream archivoSalida = new FileOutputStream(filita);
		libro.write(archivoSalida);
		archivoSalida.close();		
		}

	/*public static  void main(String [] arg) throws IOException{

		JobReporteVO cab = new JobReporteVO();	
		cab.setFechaInicio("01/01/2010");
		cab.setFechaFin("31/01/2010");
		cab.setTipoReporte("Tipo de Reporte");
		
		if("".equals(verificadorString(listaExcel(),cab)))
		{
			generarArchivoExcel(listaExcel(),cab);
		}else{
		System.out.println(verificadorString(listaExcel(),cab));	
		}
		
		
	}*/
	/*@SuppressWarnings("unchecked")
	public static boolean verificador(List<ReporteVO> listaExcel, JobReporteVO tituloCabecera){
		boolean veri = true;
		if(tituloCabecera.getFechaInicio()==null)
		{
			return false;
		}if(tituloCabecera.getFechaFin()==null)
		{
			return false;
		}if(tituloCabecera.getTipoReporte()==null)
		{
			return false;
		}
		
		List lista = reporteOsiptel();
		for(int i=0; i<lista.size();i++){
			ReporteVO ob = (ReporteVO)lista.get(i);
			if(ob.getIdProceso()==null){
				return false;
			}if(ob.getOperadorReceptor()==null){
				return false;
			}if(ob.getOperadorCedente()==null){
				return false;
			}if(ob.getTipoDocumento()==null){
				return false;
			}if(ob.getNumeroDocumento()==null){
				return false;
			}if(ob.getRangoInicial()==null){
				return false;
			}if(ob.getRangoFinal()==null){
				return false;
			}if(ob.getTipoPortabilidad()==null){
				return false;
			}if(ob.getFechaHoraMensajeSp()==null){
				return false;
			}
			
			
		}
		
		return veri;
	}*/
	
	/*@SuppressWarnings("unchecked")
	public static String verificadorString(List<ReporteVO> listaExcel, JobReporteVO tituloCabecera){
		String veri = "";
		if(tituloCabecera.getFechaInicio()==null)
		{
			veri=BundleGeneric.getBundle("error")+" "+tituloCabecera.getFechaInicio();
			
		}if(tituloCabecera.getFechaFin()==null)
		{
			veri+="\n error el fecha Fin de la cabecera";
		}if(tituloCabecera.getTipoReporte()==null)
		{
			veri+="\n error tipo reporte de la cabecera";
		}
		
		List lista = reporteOsiptel();
		for(int i=0; i<lista.size();i++){
			ReporteVO ob = (ReporteVO)lista.get(i);
			if(ob.getIdProceso()==null){
				veri+="\n error idproceso fila "+(i+1);
			}if(ob.getOperadorReceptor()==null){
				veri+="\n error OperadorReceptor fila "+(i+1);
			}if(ob.getOperadorCedente()==null){
				veri+="\n error OperadorCedente  fila "+(i+1);
			}if(ob.getTipoDocumento()==null){
				veri+="\n error TipoDocumento() fila "+(i+1);
			}if(ob.getNumeroDocumento()==null){
				veri+="\n error NumeroDocumento( fila "+(i+1);
			}if(ob.getRangoInicial()==null){
				veri+="\n error RangoInicial fila "+(i+1);
			}if(ob.getRangoFinal()==null){
				veri+="\n error RangoFinal fila "+(i+1);
			}if(ob.getTipoPortabilidad()==null){
				veri+="\n error TipoPortabilidad fila "+(i+1);
			}if(ob.getFechaHoraMensajeSp()==null){
				veri+="\n error FechaHoraMensajeSpfila "+(i+1);
			}
			
			
		}
		
		return veri;
	}*/
	
}
