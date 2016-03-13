package pe.com.nextel.provisioning.model.bo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.Region;

import pe.com.nextel.provisioning.model.vo.PreFacturacionVO;
import pe.com.nextel.provisioning.util.BundleGeneric;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class PreFacturacionBO {
	private static final Log log = LogFactory.getLog(PreFacturacionBO.class);
	private static PreFacturacionBO single = null;
	public PreFacturacionBO(){}
		
		public static PreFacturacionBO getInstance(){
			if (single == null)
				single = new PreFacturacionBO();
			return single;
		}
	 public static List<PreFacturacionVO> sizeColumnlistaExcel(){		
			List<PreFacturacionVO> lista = new ArrayList<PreFacturacionVO>();		
			for(int i=0;i<10;i++){
				PreFacturacionVO  report = new PreFacturacionVO();
				report.setOperador("22");
				report.setConceptoFactura("01");
				report.setIdProceso("22201001040100006");
				report.setFecha("05/01/2010");
				report.setInicioRango("993531489");
				report.setFinalRango("993531489");
				report.setTotalRango("1");
				lista.add(report);			
			}		
			return lista;
		}
		
	 public static List<PreFacturacionVO> sizeColumnlistaExcelConsol(){		
			List<PreFacturacionVO> lista = new ArrayList<PreFacturacionVO>();		
			for(int i=0;i<10;i++){
				PreFacturacionVO  report = new PreFacturacionVO();
				report.setOperador("22");
				report.setPortados("010000");
				report.setRetornos("010000");
				report.setObjeciones("010000");
				report.setMonto("993531489");				
				lista.add(report);			
			}		
			return lista;
		}
	public void generarArchivoExcel(PreFacturacionVO tituloCabecera,
									List<PreFacturacionVO> listaConsolidado,
									List<PreFacturacionVO> listaDetalle20,
									List<PreFacturacionVO> listaDetalle21,
									List<PreFacturacionVO> listaDetalle22
									) throws Exception{
		
		HSSFWorkbook 	libro = new HSSFWorkbook();
		HSSFSheet 		consolidado   =  libro.createSheet("Consolidado");
		HSSFSheet 		hoja   =  libro.createSheet("Nextel");
		HSSFSheet 		hoja02 =  libro.createSheet("Claro");
		HSSFSheet 		hoja03 =  libro.createSheet("Movistar");
		
		String [] cabeceraHoja = { "Operador","Concepto Factura", "IdProceso", "Fecha", "Inicio Rango",
								   "Rango Final ", "Total Rango"};
		String [] cabeceraCons = { "Operador","Portados","Retornos","Objeciones","Monto $"};
		
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
		String [] cabeceraTituloConsol ={"REPORTE CONSOLIDADO"};
		consolidado.addMergedRegion(new Region(0,(short)0,0,(short)8));
		for(int j=0; j < cabeceraTituloConsol.length;j++){
			celdaConsol = filaConsol.createCell(j);
			celdaConsol.setCellStyle(estyloTitulo);
			celdaConsol.setCellValue(cabeceraTituloConsol[j]);	
		} 
		String [] cabecerafechaInicioConsol =	{"Fecha Inicio",tituloCabecera.getFechaInicio()};
		filaConsol= consolidado.createRow(i++);
		for(int k=0; k < cabecerafechaInicioConsol.length;k++){
			celdaConsol = filaConsol.createCell(k);
			celdaConsol.setCellValue(cabecerafechaInicioConsol[k]);
		}
		String [] cabecerafechaFinalConsol =	{"Fecha Fin",tituloCabecera.getFechaFin()};
		filaConsol = consolidado.createRow(i++);	
		for(int m=0; m < cabecerafechaFinalConsol.length;m++){
			celdaConsol = filaConsol.createCell(m);
			celdaConsol.setCellValue(cabecerafechaFinalConsol[m]);
		}
		
		filaConsol = consolidado.createRow(i++);
		for(int o=0; o < cabeceraCons.length;o++){
		celdaConsol = filaConsol.createCell(o);
		celdaConsol.setCellStyle(estyloTitulo);
		celdaConsol.setCellValue(cabeceraCons[o]);
		}
		
		for(PreFacturacionVO k :listaConsolidado){
			filaConsol = consolidado.createRow(i++);
			filaConsol.createCell(0).setCellValue(k.getOperador());
			filaConsol.createCell(1).setCellValue(k.getPortados());
			filaConsol.createCell(2).setCellValue(k.getRetornos());
			filaConsol.createCell(3).setCellValue(k.getObjeciones());
			filaConsol.createCell(4).setCellValue(k.getMonto());				
			}
		
			for (int o = 0; o <sizeColumnlistaExcelConsol().size(); o++) {  
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
		
		for(PreFacturacionVO k :listaDetalle20){
		fila = hoja.createRow(i++);
		fila.createCell(0).setCellValue(k.getOperador());
		fila.createCell(1).setCellValue(k.getConceptoFactura());
		fila.createCell(2).setCellValue(k.getIdProceso());		
		fila.createCell(3).setCellValue(k.getFecha());	
		fila.createCell(4).setCellValue(k.getInicioRango());
		fila.createCell(5).setCellValue(k.getFinalRango());
		fila.createCell(6).setCellValue(k.getTotalRango());
		}	
		for (int o = 0; o <sizeColumnlistaExcel().size(); o++) {  
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
		
		for(PreFacturacionVO k :listaDetalle21){
		fila02 = hoja02.createRow(i++);
		fila02.createCell(0).setCellValue(k.getOperador());
		fila02.createCell(1).setCellValue(k.getConceptoFactura());
		fila02.createCell(2).setCellValue(k.getIdProceso());
		fila02.createCell(3).setCellValue(k.getFecha());
		fila02.createCell(4).setCellValue(k.getInicioRango());
		fila02.createCell(5).setCellValue(k.getFinalRango());
		fila02.createCell(6).setCellValue(k.getTotalRango());	
		}	
		for (int o = 0; o <sizeColumnlistaExcel().size(); o++) {  
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
		
		for(PreFacturacionVO k :listaDetalle22){
		fila03 = hoja03.createRow(i++);
		fila03.createCell(0).setCellValue(k.getOperador());
		fila03.createCell(1).setCellValue(k.getConceptoFactura());
		fila03.createCell(2).setCellValue(k.getIdProceso());
		fila03.createCell(3).setCellValue(k.getFecha());
		fila03.createCell(4).setCellValue(k.getInicioRango());
		fila03.createCell(5).setCellValue(k.getFinalRango());
		fila03.createCell(6).setCellValue(k.getTotalRango());	
		}	
		for (int o = 0; o <sizeColumnlistaExcel().size(); o++) {  
			hoja03.autoSizeColumn((short) o);			
		}
		/*****/
		if(!(new File(BundleGeneric.getBundle("rutaSave")).exists()))
		{new File(BundleGeneric.getBundle("rutaSave")).mkdir();}
		String strNombreArchivo =  BundleGeneric.getBundle("rutaSave")+"/ReporteDetalle.xls";
		File filita = new File(strNombreArchivo);
		FileOutputStream archivoSalida = new FileOutputStream(filita);
		libro.write(archivoSalida);
		archivoSalida.close();
		}
}
