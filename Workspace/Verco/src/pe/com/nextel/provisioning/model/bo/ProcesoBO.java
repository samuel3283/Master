package pe.com.nextel.provisioning.model.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import pe.com.nextel.provisioning.framework.conexion.ConexionAPI;
import pe.com.nextel.provisioning.framework.conexion.JdbcUtilitarioPortanode;
import pe.com.nextel.provisioning.framework.exception.FileUtilException;
import pe.com.nextel.provisioning.framework.exception.NetException;
import pe.com.nextel.provisioning.framework.util.jmail.Email;
import pe.com.nextel.provisioning.framework.util.net.SFTP;
import pe.com.nextel.provisioning.framework.util.zip.UtilZip;
import pe.com.nextel.provisioning.model.dao.ProcesoDAO;
import pe.com.nextel.provisioning.model.vo.ArchivoFPVO;
import pe.com.nextel.provisioning.model.vo.ArchivoFRVO;
import pe.com.nextel.provisioning.model.vo.ArchivoPortadoVO;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.model.vo.TipoArchivoVO;
import pe.com.nextel.provisioning.view.ValueConstants;

public class ProcesoBO {

	
	 private static final Log log = LogFactory.getLog(ProcesoBO.class);    
	 private static ProcesoBO single = null;
	 
	 public ProcesoBO() {
	 }
	    
	 public static ProcesoBO getInstance() {
	    if (single == null)
	        single = new ProcesoBO();
	    return single;
	 }	
	 

	 public static String leeNP_auxiliar() {

			int cantidadRegistros = 0;
			int lineasRegistrosArchivo = 0;		
			String nombreArchivo = "export_carga.txt";
			String directorio = "C:/";
			String resultado = ValueConstants.VALOR_OK;
			String archivo="export_carga.txt";
			/*archivo="export_tmp.txt";
			directorio="C:/";*/
			String fecha="26/01/2011";
			//directorio="C:/";
			String ruta = directorio + archivo;
			Connection con = null;
			ArchivoPortadoVO archivoPortadoVO = null;
			try {
				File fi = new File(ruta);
				FileInputStream fis = new FileInputStream(fi);
				byte[] zipped = new byte[(int) fi.length()];
				fis.read(zipped);
				archivo = new String(zipped);
				
				// Obtener datos de archivo
				String[] arrayArchivo = archivo.split("\n");
				log.info("LeerArchivo ==> Cantidad de registros " + arrayArchivo.length);

				if (arrayArchivo.length>0){
					
					con = ConexionAPI.getInstance().getConnection();
		        	
									
					for (int i = 0; i < arrayArchivo.length; i++) {
					//for (int i = 0; i < 10; i++) {
						if((arrayArchivo[i].trim()!="" || arrayArchivo[i].trim()!=null) && arrayArchivo[i].length()>20) {
							
							archivoPortadoVO = new ArchivoPortadoVO();
							
							archivoPortadoVO.setIdentificadorProceso(arrayArchivo[i].substring(17,34));
							archivoPortadoVO.setNumero(arrayArchivo[i].substring(0,9));
							archivoPortadoVO.setInicioVentana(arrayArchivo[i].substring(68,76));
							
							log.info("==> Registro "+i+"==>IDPROCESO:"+ arrayArchivo[i].substring(17,34)+"==>NUM:"+arrayArchivo[i].substring(0,9)+"==>FEC:"+arrayArchivo[i].substring(68,76));
								
							resultado = ProcesoDAO.insertaArchivoNP_aux(con, archivoPortadoVO, fecha);
							
						}		      
						             
					}			
	          } 
				
				log.info("Proceso ==> STATUS ::: "+resultado);

			} catch (Exception e) {
				resultado = ValueConstants.ERROR_CAUSAS_TECNICAS+" "+e.getMessage()+"-Nombre de Archivo:"+nombreArchivo;;
				log.error(e);
			}
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }				    

			return resultado;
			
			
	 }
	 
	 
	 public static String leeNP() {

			int cantidadRegistros = 0;
			int lineasRegistrosArchivo = 0;		
			String nombreArchivo = "NumeracionesPortadas_20110401";
			String directorio = "C:/";
			String resultado = ValueConstants.VALOR_OK;
			String archivo="NumeracionesPortadas_20110401";
			/*archivo="export_tmp.txt";
			directorio="C:/";*/
			String fecha="01/04/2011";
			//directorio="C:/";
			String ruta = directorio + archivo;
			Connection con = null;
			ArchivoPortadoVO archivoPortadoVO = null;
			try {
				File fi = new File(ruta);
				FileInputStream fis = new FileInputStream(fi);
				byte[] zipped = new byte[(int) fi.length()];
				fis.read(zipped);
				archivo = new String(zipped);
				
				// Obtener datos de archivo
				String[] arrayArchivo = archivo.split("\n");
				log.info("LeerArchivo ==> Cantidad de registros " + arrayArchivo.length);

				if (arrayArchivo.length>0){
					
					con = ConexionAPI.getInstance().getConnection();
		        	
					ProcesoDAO.limpiarTabla(con);
					
					for (int i = 0; i < arrayArchivo.length; i++) {
					
						if((arrayArchivo[i].trim()!="" || arrayArchivo[i].trim()!=null) && arrayArchivo[i].length()>20) {
							
							archivoPortadoVO = new ArchivoPortadoVO();
							
							archivoPortadoVO.setIdentificadorProceso(arrayArchivo[i].substring(0,17));
							archivoPortadoVO.setNumero(arrayArchivo[i].substring(20,29));
							archivoPortadoVO.setInicioVentana(arrayArchivo[i].substring(29,37));
							
							//log.info("==> Registro "+i+" ==>"+ arrayArchivo[i]);
							log.info("==> Registro "+i+"==>IDPROCESO:"+ arrayArchivo[i].substring(0,17)+"==>NUM:"+arrayArchivo[i].substring(20,29)+"==>FEC:"+arrayArchivo[i].substring(29,37));
								
							resultado = ProcesoDAO.insertaArchivoNP(con, archivoPortadoVO, fecha);
							
						}		      
						             
					}			
	          } 
				
				log.info("Proceso ==> STATUS ::: "+resultado);

			} catch (Exception e) {
				resultado = ValueConstants.ERROR_CAUSAS_TECNICAS+" "+e.getMessage()+"-Nombre de Archivo:"+nombreArchivo;;
				log.error(e);
			}
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }				    

			return resultado;
			
			
	 }
	 
	 public static String leeSP() {

			int cantidadRegistros = 0;
			int lineasRegistrosArchivo = 0;		
			String nombreArchivo = "SolicitudesProgramadas_20110324";
			String directorio = "C:/";
			String resultado = ValueConstants.VALOR_OK;
			String archivo="SolicitudesProgramadas_20110324";
			/*archivo="export_tmp.txt";
			directorio="C:/";*/
			String fecha="24/03/2011";
			//directorio="C:/";
			String ruta = directorio + archivo;
			Connection con = null;
			ArchivoPortadoVO archivoPortadoVO = null;
			try {
				File fi = new File(ruta);
				FileInputStream fis = new FileInputStream(fi);
				byte[] zipped = new byte[(int) fi.length()];
				fis.read(zipped);
				archivo = new String(zipped);
				
				// Obtener datos de archivo
				String[] arrayArchivo = archivo.split("\n");
				log.info("LeerArchivo ==> Cantidad de registros " + arrayArchivo.length);

				if (arrayArchivo.length>0){
					
					con = ConexionAPI.getInstance().getConnection();
		        	
					ProcesoDAO.limpiarTabla(con);
					
					for (int i = 0; i < arrayArchivo.length; i++) {
					
						if((arrayArchivo[i].trim()!="" || arrayArchivo[i].trim()!=null) && arrayArchivo[i].length()>20) {
							
							archivoPortadoVO = new ArchivoPortadoVO();
							
							archivoPortadoVO.setIdentificadorProceso(arrayArchivo[i].substring(0,17));
							archivoPortadoVO.setNumero(arrayArchivo[i].substring(20,29));
							archivoPortadoVO.setInicioVentana(arrayArchivo[i].substring(29,37));
							
							//log.info("==> Registro "+i+" ==>"+ arrayArchivo[i]);
							log.info("==> Registro "+i+"==>IDPROCESO:"+ arrayArchivo[i].substring(0,17)+"==>NUM:"+arrayArchivo[i].substring(20,29)+"==>FEC:"+arrayArchivo[i].substring(29,37));
								
							resultado = ProcesoDAO.insertaArchivoSP(con, archivoPortadoVO, fecha);
							
						}		      
						             
					}			
	          } 
				
				log.info("Proceso ==> STATUS ::: "+resultado);

			} catch (Exception e) {
				resultado = ValueConstants.ERROR_CAUSAS_TECNICAS+" "+e.getMessage()+"-Nombre de Archivo:"+nombreArchivo;;
				log.error(e);
			}
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }				    

			return resultado;
			
			
	 }
	
	 public static String leeSR() {

			int cantidadRegistros = 0;
			int lineasRegistrosArchivo = 0;		
			String nombreArchivo = "RetornosProgramados_20110324";
			String directorio = "C:/";
			String resultado = ValueConstants.VALOR_OK;
			String archivo="RetornosProgramados_20110324";
			/*archivo="export_tmp.txt";
			directorio="C:/";*/
			String fecha="24/03/2011";
			//directorio="C:/";
			String ruta = directorio + archivo;
			Connection con = null;
			ArchivoPortadoVO archivoPortadoVO = null;
			try {
				File fi = new File(ruta);
				FileInputStream fis = new FileInputStream(fi);
				byte[] zipped = new byte[(int) fi.length()];
				fis.read(zipped);
				archivo = new String(zipped);
				
				// Obtener datos de archivo
				String[] arrayArchivo = archivo.split("\n");
				log.info("LeerArchivo ==> Cantidad de registros " + arrayArchivo.length);

				if (arrayArchivo.length>0){
					
					con = ConexionAPI.getInstance().getConnection();
		        	
					ProcesoDAO.limpiarTabla(con);
					
					for (int i = 0; i < arrayArchivo.length; i++) {
					
						if((arrayArchivo[i].trim()!="" || arrayArchivo[i].trim()!=null) && arrayArchivo[i].length()>20) {
							
							archivoPortadoVO = new ArchivoPortadoVO();
							
							//archivoPortadoVO.setIdentificadorProceso(arrayArchivo[i].substring(0,17));
							archivoPortadoVO.setIdentificadorProceso("");
							archivoPortadoVO.setNumero(arrayArchivo[i].substring(3,12));
							archivoPortadoVO.setInicioVentana(arrayArchivo[i].substring(12,20));
							
							//log.info("==> Registro "+i+" ==>"+ arrayArchivo[i]);
							log.info("==> Registro "+i+"==>IDPROCESO:"+ arrayArchivo[i].substring(0,17)+"==>NUM:"+arrayArchivo[i].substring(3,12)+"==>FEC:"+arrayArchivo[i].substring(12,20));
								
							resultado = ProcesoDAO.insertaArchivoSR(con, archivoPortadoVO, fecha);
							
						}		      
						             
					}			
	          } 
				
				log.info("Proceso ==> STATUS ::: "+resultado);

			} catch (Exception e) {
				resultado = ValueConstants.ERROR_CAUSAS_TECNICAS+" "+e.getMessage()+"-Nombre de Archivo:"+nombreArchivo;;
				log.error(e);
			}
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }				    

			return resultado;
			
			
	 }
	
	 
	 public static void main(String[] args) {
	   leeNP();
		// leeSP();
	   // leeSR();
		 //cargaNPPortanode();
		 //leeNP_auxiliar();
	 }
	 
	 
	 public static void cargaNPPortanode() {
		 
	    Connection con = null;
	    Connection conexcionlocal = null;
	    String fecha="26/01/2011";
	    try {
			 conexcionlocal = ConexionAPI.getInstance().getConnection();
			 con = JdbcUtilitarioPortanode.getInstance().getConnection();
			 ProcesoDAO.numeracionPortadaPortanode(con,conexcionlocal,fecha);  
		} catch (Exception e) {
			e.printStackTrace();
		}
        finally
        {
            try
            {
            	conexcionlocal.close();
            	con.close();
            }catch(SQLException e)
            {
                e.printStackTrace();
                log.error(e);
            }
        }	
		 
		 
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 /*
	 public static void main(String[] args) {

		//iniciarCargaNP();
		iniciarCargaHistoricoNP();
	 }
	 */
		 /*
	 	 procesoNumeracionPortada();
	 	 String resultado = ValueConstants.VALOR_OK;
		 try {
			resultado=obtenerArchivo();
			if(resultado=="OK")
			resultado=leerArchivo();
			//resultado=cargarArchivo();
		} catch (Exception e) {
			resultado="ERROR";
			log.info(e.getMessage());
		}
		log.info("Resultado Proceso:"+resultado);
	 }		*/
	 
	 public static void iniciarProceso() {
		 log.info("Iniciando Proceso...");
		 try {
		 iniciarCargaHistoricoNP();
		 iniciarCargaNP();
		 } catch (Exception e) {
		 log.info("Error Proceso: "+e.getMessage());
			}
		 log.info("Finalizando Proceso...");
	 }
	 
	 ////////////////////////HIS NUM PORTADA ///////////////////////
	 public static void iniciarCargaHistoricoNP() {
		 log.info("Iniciando Proceso CargaHistoricoNP...");
		 String resultado = ValueConstants.VALOR_OK;
		 try {
			resultado=obtenerArchivoHis();
			
			if(resultado=="OK")
			resultado=leerArchivoHis();
			//resultado=cargarArchivo();
		} catch (Exception e) {
			resultado="ERROR";
			log.info(e.getMessage());
		}
		log.info("Resultado Proceso CargaHistoricoNP:"+resultado);
	 }	
	 
	 
	 public static String obtenerArchivoHis() throws NetException {
		 	
			String resultado = ValueConstants.VALOR_OK;
			String origenArchivo = "export_his.txt";
			String destinoArchivo = "export_his.txt";
			String ftpHost = "10.82.1.18";
			String ftpUser = "oratest";
			String ftpPwd = "comsat3st";
			String ftpDirectory = "/home/oratest/";
			String sourcePath = "/app/database/directorio/";
			String extension=".gz";
			//sourcePath="C:/";
		    boolean rpta = false;
	        File file = new File(sourcePath+destinoArchivo);
	        if(file.exists())
	        file.delete();        
	        
			SFTP sftp=null;
			int puerto=22;
			log.info("[validarAccesoFtp PortaNode] ==> Host::"+ftpHost+"==> User::"+ftpUser+"==> Pass::"+ftpPwd+"==>REMOTE PATH::"+ftpDirectory+"-");
			log.info("[validarAccesoFtp PortaNode] ==> archivo::"+origenArchivo+"==> path::"+sourcePath);
			
			try {
			sftp = new SFTP();
			sftp.connect(ftpUser, ftpPwd, ftpHost, ftpDirectory, puerto);
			sftp.load(origenArchivo,sourcePath+destinoArchivo);
			sftp.disconnect();
			} catch (IOException i) {
				sftp.disconnect();
				resultado = ValueConstants.ERROR_ARCHIVOS_NOEXISTE_FTP+"-"+i.getMessage();
				log.info("ARCHIVO NO SE ENCUENTRA: "+i.getMessage());
			} catch (NetException n) {
	  		    sftp.disconnect();
	  		    if(n.getMessage().equals("Auth fail"))
	            resultado = ValueConstants.ERROR_ACCESO_FTP+" Usuario y/o Password Incorrecto: "+n.getMessage();
	            else
	            resultado = ValueConstants.ERROR_ACCESO_FTP+" HOST: "+ftpHost+" "+n.getMessage();
				log.info(":ERROR CONEXION FTP::"+n.getMessage());
			} catch (Exception e) {
			  sftp.disconnect();
			  resultado = ValueConstants.ERROR_CAUSAS_TECNICAS+"\n"+e.getMessage();
			  log.info(e.getMessage());
			}

	        return resultado;
			
	 		}
	 
	 
	 public static String leerArchivoHis() throws Exception {

			int cantidadRegistros = 0;
			int lineasRegistrosArchivo = 0;		
			String nombreArchivo = "";
			String directorio = "/app/database/directorio/";
			String resultado = ValueConstants.VALOR_OK;
			String archivo="export_his.txt";
			//directorio="C:/";
			String ruta = directorio + archivo;
			Connection con = null;
			ArchivoPortadoVO archivoPortadoVO = null;
			
			try {
				File fi = new File(ruta);
				FileInputStream fis = new FileInputStream(fi);
				byte[] zipped = new byte[(int) fi.length()];
				fis.read(zipped);
				archivo = new String(zipped);				
				// Obtener datos de archivo
				String[] arrayArchivo = archivo.split("\n");
				log.info("LeerArchivo ==> Cantidad de registros " + arrayArchivo.length);

				if (arrayArchivo.length>0){
					
					con = ConexionAPI.getInstance().getConnection();
		        	
					ProcesoDAO.limpiarTablaHis(con);
					
					for (int i = 0; i < arrayArchivo.length; i++) {
					
						if(arrayArchivo[i].trim()!="" || arrayArchivo[i].trim()!=null) {
							
							archivoPortadoVO = new ArchivoPortadoVO();
							
							archivoPortadoVO.setNumero(arrayArchivo[i].substring(0,9));
							archivoPortadoVO.setDonante(arrayArchivo[i].substring(10,12));
							archivoPortadoVO.setReceptor(arrayArchivo[i].substring(12,14));
							archivoPortadoVO.setDonanteInicial(arrayArchivo[i].substring(14,16));
							archivoPortadoVO.setIdentificadorProceso(arrayArchivo[i].substring(17,34));
							archivoPortadoVO.setNumeroSecuencial(arrayArchivo[i].substring(35,52));
							archivoPortadoVO.setInicioProceso(arrayArchivo[i].substring(53,67));
							archivoPortadoVO.setInicioVentana(arrayArchivo[i].substring(68,82));
							archivoPortadoVO.setDuracionVentana(arrayArchivo[i].substring(83,86));
													
							archivoPortadoVO.setEstado(arrayArchivo[i].substring(87,89));
							archivoPortadoVO.setFechaModificacion(arrayArchivo[i].substring(90,104));
							archivoPortadoVO.setDonantePrevio(arrayArchivo[i].substring(105,107));
							archivoPortadoVO.setTipoPortabilidad(arrayArchivo[i].substring(108,110));
							archivoPortadoVO.setFechaRegistro(arrayArchivo[i].substring(111,125));
							archivoPortadoVO.setTipoModificacion(arrayArchivo[i].substring(126,127));
							log.info("==> Registro "+i+" ==>"+ arrayArchivo[i]);

							/*
							log.info(" ==> Cabecera :::  Celular:"+arrayArchivo[i].substring(0,9)+
		 					"Donante:"+arrayArchivo[i].substring(10,12)+
		 					"Recptor:"+arrayArchivo[i].substring(12,14)+
		 					"Don ini:"+arrayArchivo[i].substring(14,16)+
		 					"id proc:"+arrayArchivo[i].substring(17,34)+
		 					"nro sec:"+arrayArchivo[i].substring(35,52)+
		 					"ini pro:"+arrayArchivo[i].substring(53,67)+
		 					"ini ven:"+arrayArchivo[i].substring(68,82)+
		 					"dur ven:"+arrayArchivo[i].substring(83,86)+
		 					"estado :"+arrayArchivo[i].substring(87,89)+
		 					"fec mod:"+arrayArchivo[i].substring(90,104)+
		 					"don pre:"+arrayArchivo[i].substring(105,107)+
		 					"tip por:"+arrayArchivo[i].substring(108,110));	*/				
								
							resultado = ProcesoDAO.insertaArchivoHis(con, archivoPortadoVO);
							
						}		      
						             
					}			
	          } else {

	          }
						

				log.info("ProcesoUnoBO ==> STATUS ::: "+resultado);

			} catch (Exception e) {
				resultado = ValueConstants.ERROR_CAUSAS_TECNICAS+" "+e.getMessage()+"-Nombre de Archivo:"+nombreArchivo;;
				log.error(e);
			}
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }				    

			return resultado;
		}
		
		
	 
	 
	 
	 //////////////////////////NUM PORTADA /////////////////////////
	 public static void iniciarCargaNP() {
		 log.info("Iniciando Proceso CargaNP Daemon...");
		 String resultado = ValueConstants.VALOR_OK;
		 try {
			resultado=obtenerArchivo();
			
			if(resultado=="OK")
			resultado=leerArchivo();
			//resultado=cargarArchivo();
		} catch (Exception e) {
			resultado="ERROR";
			log.info(e.getMessage());
		}
		log.info("Resultado Proceso CargaNP: "+resultado);
	 }	
	 
	 
	 
	 
	 public static String obtenerArchivo() throws NetException {
		 	
			String resultado = ValueConstants.VALOR_OK;
			String origenArchivo = "export.txt";
			String destinoArchivo = "export.txt";
			String ftpHost = "10.82.1.18";
			String ftpUser = "oratest";
			String ftpPwd = "comsat3st";
			String ftpDirectory = "/home/oratest/";
			String sourcePath = "/app/database/directorio/";
			String extension=".gz";
			
			/*origenArchivo="export_tmp.txt";
			destinoArchivo="export_tmp.txt";
			sourcePath="C:/";*/
			//sourcePath="C:/";
			
		    boolean rpta = false;
	        File file = new File(sourcePath+destinoArchivo);
	        if(file.exists())
	        file.delete();        
	        
			SFTP sftp=null;
			int puerto=22;
			log.info("[validarAccesoFtp PortaNode] ==> Host::"+ftpHost+"==> User::"+ftpUser+"==> Pass::"+ftpPwd+"==>REMOTE PATH::"+ftpDirectory+"-");
			log.info("[validarAccesoFtp PortaNode] ==> archivo::"+origenArchivo+"==> path::"+sourcePath);
			
			try {
			sftp = new SFTP();
			sftp.connect(ftpUser, ftpPwd, ftpHost, ftpDirectory, puerto);
			sftp.load(origenArchivo,sourcePath+destinoArchivo);
			sftp.disconnect();
			} catch (IOException i) {
				sftp.disconnect();
				resultado = ValueConstants.ERROR_ARCHIVOS_NOEXISTE_FTP+"-"+i.getMessage();
				log.info("ARCHIVO NO SE ENCUENTRA: "+i.getMessage());
			} catch (NetException n) {
	  		    sftp.disconnect();
	  		    if(n.getMessage().equals("Auth fail"))
	            resultado = ValueConstants.ERROR_ACCESO_FTP+" Usuario y/o Password Incorrecto: "+n.getMessage();
	            else
	            resultado = ValueConstants.ERROR_ACCESO_FTP+" HOST: "+ftpHost+" "+n.getMessage();
				log.info(":ERROR CONEXION FTP::"+n.getMessage());
			} catch (Exception e) {
			  sftp.disconnect();
			  resultado = ValueConstants.ERROR_CAUSAS_TECNICAS+"\n"+e.getMessage();
			  log.info(e.getMessage());
			}

	        return resultado;
			
	 		}
	 
	 
	 public static String cargarArchivo() throws Exception {
		 
		 String resultado="OK";
		 resultado=ProcesoDAO.cargarArchivo();
		 
		 return resultado;
	 }
	 
	 
	 
			
	 public static String leerArchivo() throws Exception {

			int cantidadRegistros = 0;
			int lineasRegistrosArchivo = 0;		
			String nombreArchivo = "";
			String directorio = "/app/database/directorio/";
			String resultado = ValueConstants.VALOR_OK;
			String archivo="export.txt";
			/*archivo="export_tmp.txt";
			directorio="C:/";*/
			
			//directorio="C:/";
			String ruta = directorio + archivo;
			Connection con = null;
			ArchivoPortadoVO archivoPortadoVO = null;
			
			try {
				File fi = new File(ruta);

				FileInputStream fis = new FileInputStream(fi);
				byte[] zipped = new byte[(int) fi.length()];
				fis.read(zipped);
				archivo = new String(zipped);
				
				// Obtener datos de archivo
				String[] arrayArchivo = archivo.split("\n");
				log.info("LeerArchivo ==> Cantidad de registros " + arrayArchivo.length);

				if (arrayArchivo.length>0){
					
					con = ConexionAPI.getInstance().getConnection();
		        	
					ProcesoDAO.limpiarTabla(con);
					
					for (int i = 0; i < arrayArchivo.length; i++) {
					
						if(arrayArchivo[i].trim()!="" || arrayArchivo[i].trim()!=null) {
							
							archivoPortadoVO = new ArchivoPortadoVO();
							
							archivoPortadoVO.setNumero(arrayArchivo[i].substring(0,9));
							archivoPortadoVO.setDonante(arrayArchivo[i].substring(10,12));
							archivoPortadoVO.setReceptor(arrayArchivo[i].substring(12,14));
							archivoPortadoVO.setDonanteInicial(arrayArchivo[i].substring(14,16));
							archivoPortadoVO.setIdentificadorProceso(arrayArchivo[i].substring(17,34));
							archivoPortadoVO.setNumeroSecuencial(arrayArchivo[i].substring(35,52));
							archivoPortadoVO.setInicioProceso(arrayArchivo[i].substring(53,67));
							archivoPortadoVO.setInicioVentana(arrayArchivo[i].substring(68,82));
							archivoPortadoVO.setDuracionVentana(arrayArchivo[i].substring(83,86));
													
							archivoPortadoVO.setEstado(arrayArchivo[i].substring(87,89));
							archivoPortadoVO.setFechaModificacion(arrayArchivo[i].substring(90,104));
							archivoPortadoVO.setDonantePrevio(arrayArchivo[i].substring(105,107));
							archivoPortadoVO.setTipoPortabilidad(arrayArchivo[i].substring(108,110));
							
							log.info("==> Registro "+i+" ==>"+ arrayArchivo[i]);

							/*
							log.info(" ==> Cabecera :::  Celular:"+arrayArchivo[i].substring(0,9)+
		 					"Donante:"+arrayArchivo[i].substring(10,12)+
		 					"Recptor:"+arrayArchivo[i].substring(12,14)+
		 					"Don ini:"+arrayArchivo[i].substring(14,16)+
		 					"id proc:"+arrayArchivo[i].substring(17,34)+
		 					"nro sec:"+arrayArchivo[i].substring(35,52)+
		 					"ini pro:"+arrayArchivo[i].substring(53,67)+
		 					"ini ven:"+arrayArchivo[i].substring(68,82)+
		 					"dur ven:"+arrayArchivo[i].substring(83,86)+
		 					"estado :"+arrayArchivo[i].substring(87,89)+
		 					"fec mod:"+arrayArchivo[i].substring(90,104)+
		 					"don pre:"+arrayArchivo[i].substring(105,107)+
		 					"tip por:"+arrayArchivo[i].substring(108,110));	*/				
								
							resultado = ProcesoDAO.insertaArchivo(con, archivoPortadoVO);
							
						}		      
						             
					}			
	          } else {

	          }
						

				log.info("ProcesoUnoBO ==> STATUS ::: "+resultado);

			} catch (Exception e) {
				resultado = ValueConstants.ERROR_CAUSAS_TECNICAS+" "+e.getMessage()+"-Nombre de Archivo:"+nombreArchivo;;
				log.error(e);
			}
	         finally
	         {
	             try
	             {
	                 con.close();
	             }catch(SQLException e)
	             {
	                 e.printStackTrace();
	                 log.error(e);
	             }
	         }				    

			return resultado;
		}
		
		
	 
	 public static void procesoNumeracionPortada() {
		 log.info("Iniciando procesoNumeracionPortada...");
		 String resultado = ValueConstants.VALOR_OK;
		 
		 Date date = new Date();
		 Format formatter = new SimpleDateFormat("yyyyMMdd");
		 String fechaEjecucion = formatter.format(date);
		 //fechaEjecucion="20100315";
		 //Email mail = null;
		 try {
			//mail = new Email(); 
			 
			resultado = ProcesoDAO.maestraNumeracionPortada(fechaEjecucion);
			log.error("(1)procesoNumeracionPortada ==> resultado:"+resultado);            
			if(resultado=="OK") {
			resultado = ProcesoDAO.validaNumeracionPortada();
			log.error("procesoNumeracionPortada ==> resultado:"+resultado);            
			}
			log.error("(2)procesoNumeracionPortada ==> resultado:"+resultado);            
			
			if(resultado!="OK") {
			//ContactoBO.getInstance().notificarContactos(mail, "ERROR PROCESO VALIDACION NUMERACION PORTADA", resultado);
			}
			
		 } catch (Exception e) {
			resultado=resultado+"  ERROR "+e.getMessage();
			//ContactoBO.getInstance().notificarContactos(mail, "ERROR PROCESO VALIDACION NUMERACION PORTADA", resultado);

			log.info(e.getMessage());
		}
		log.info("Resultado procesoNumeracionPortada:::"+resultado);
	 }	


}