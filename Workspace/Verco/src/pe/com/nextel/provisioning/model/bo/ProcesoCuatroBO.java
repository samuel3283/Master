package pe.com.nextel.provisioning.model.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.DAOException;
import pe.com.nextel.provisioning.framework.exception.FileUtilException;
import pe.com.nextel.provisioning.framework.util.files.FileUtil;
import pe.com.nextel.provisioning.framework.util.portabilidad.ReglasPortabilidad;
import pe.com.nextel.provisioning.framework.util.transaction.TransactionContext;
import pe.com.nextel.provisioning.model.dao.ArchivoFPDAO;
import pe.com.nextel.provisioning.model.dao.ArchivoFRDAO;
import pe.com.nextel.provisioning.model.dao.CabeceraArchivoDAO;
import pe.com.nextel.provisioning.model.vo.ArchivoFPVO;
import pe.com.nextel.provisioning.model.vo.ArchivoFRVO;
import pe.com.nextel.provisioning.model.vo.CabeceraArchivoVO;
import pe.com.nextel.provisioning.model.vo.EstadoProcesoVO;
import pe.com.nextel.provisioning.model.vo.MensajeVO;
import pe.com.nextel.provisioning.view.ValueConstants;


public class ProcesoCuatroBO {
	 private static final Log log = LogFactory.getLog(ProcesoCuatroBO.class);    
	 private static ProcesoCuatroBO single = null;
	 
	 public ProcesoCuatroBO() {
	 }
	    
	 public static ProcesoCuatroBO getInstance() {
	    if (single == null)
	        single = new ProcesoCuatroBO();
	    return single;
	 }	

	 
	 public String ejecutar(String fechaProceso, Map mapParametros) {
		 log.info("==> INICIO PROCESO 04 "+fechaProceso);
		 
		 String resultado = "";
		 resultado = ejecutar(ValueConstants.ARCHIVO_FP, fechaProceso, mapParametros);
		 log.info("==> RESULTADO FPWS "+resultado);
		 
		 resultado = ejecutar(ValueConstants.ARCHIVO_FR, fechaProceso, mapParametros);		 
		 log.info("==> RESULTADO FRWS "+resultado);
		 
		 log.info("==> FIN PROCESO 04 "+fechaProceso);
		 
		 return resultado;
	 }
	 
	 
	 /**Metodo CambiarFormatoWs: Cambiar Formato a Sentencias Web Service
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return		: ActionForward
	  * @date		: 29/11/2009
	  * @time		: 06:33:19 PM
	  * @author		: S.
	  * @descripcion: Crea Archivos FPWS y FRWS 
	 */		
	 public String ejecutar(String tipo, String fechaProceso, Map mapParametros) {
		 	
		    //Map mapParametros = new HashMap();    
		   	String resultado = ValueConstants.PROCESO_SATISFACTORIO;
		   	List<CabeceraArchivoVO> listCabeceraArchivo = new ArrayList<CabeceraArchivoVO>();
		   	CabeceraArchivoVO cabeceraArchivoBean = null;
		   	String nombre_archivo ="";
			String valor ="";
		   	try 
		   	{
		   		log.info("ProcesoCuatroBO ejecutar ::: ==> TipoArch :::"+tipo+" ==> fechaProceso :"+fechaProceso);
	   			
			   	listCabeceraArchivo = ObtenerCabeceraArchivo(tipo,fechaProceso);

		   		for(int i=0;i<listCabeceraArchivo.size();i++)
		   		{	
		   			
		   			cabeceraArchivoBean = new CabeceraArchivoVO();
		   			cabeceraArchivoBean = listCabeceraArchivo.get(i);

		   			
		   			if(ValueConstants.ARCHIVO_FP.equals(cabeceraArchivoBean.getTipoArchivo())) {
					nombre_archivo=ValueConstants.DESTINO_ARCHIVO_FP+cabeceraArchivoBean.getFechaProceso();
		   			} else {
		   			nombre_archivo=ValueConstants.DESTINO_ARCHIVO_FR+cabeceraArchivoBean.getFechaProceso();
			   		}
		   			log.info("ProcesoCuatroBO ejecutar ::: ==> IdCabecera :::"+cabeceraArchivoBean.getIdcabecera()+" ::: ==> TipoArch :::"+cabeceraArchivoBean.getTipoArchivo()+" ==> Archivo :"+nombre_archivo);
		   			 
		   			
		   			if(ValueConstants.ARCHIVO_FP.equals(cabeceraArchivoBean.getTipoArchivo()) && FileUtil.existsFile(String.valueOf(mapParametros.get(ValueConstants.CODIGO_RUTA_ORIGEN_PROCESO_CUATRO))+ nombre_archivo)) {
				   		
		   				if(resultado.equals(ValueConstants.PROCESO_SATISFACTORIO)) {
			   				/*Metodo que obtiene las columnas necesarias para crear los archivos FPWS*/
		   					resultado = ProcesoCuatroBO.getInstance().AsignarMensajeFpWs(cabeceraArchivoBean, mapParametros);
		   					log.info("AsignarMensajeFpWs :::"+resultado);
		   						
				 			if(resultado.equals(ValueConstants.PROCESO_SATISFACTORIO)) {
						   		/*Metodo que genera el formato y copia los archivos FPWS en servidor destino*/
								resultado = ProcesoCuatroBO.getInstance().obtenerSentenciaWsFp(cabeceraArchivoBean, mapParametros);
				 				log.info("obtenerSentenciaWsFp :::"+resultado);
				 					
								if(resultado.equals(ValueConstants.PROCESO_SATISFACTORIO)) {
									
									//Actualizamos Estado Proceso Cabecera
									cabeceraArchivoBean.setEstado(ValueConstants.CODIGO_PROCESO4);
									resultado = CabeceraArchivoDAO.actualizar(cabeceraArchivoBean);
									log.info(" actuaizar estado :::"+resultado);
									//Activar Flag Delay
									activarFlag(cabeceraArchivoBean,ValueConstants.ESTADO_ACTIVO,ValueConstants.VALOR_UNO);							
								}				 			
				 				
				 			}
		   				
		   				}			 			
			 			
		   			} else if(ValueConstants.ARCHIVO_FR.equals(cabeceraArchivoBean.getTipoArchivo()) && FileUtil.existsFile(String.valueOf(mapParametros.get(ValueConstants.CODIGO_RUTA_ORIGEN_PROCESO_CUATRO))+ nombre_archivo)) {
			 			
		   				if(resultado.equals(ValueConstants.PROCESO_SATISFACTORIO)) {
					   		/*Metodo que genera el formato y copia los archivos FRWS en servidor destino*/
		   					
		   					resultado = ProcesoCuatroBO.getInstance().AsignarMensajeFrWs(cabeceraArchivoBean, mapParametros);	 		   		
		   					log.info("AsignarMensajeFrWs :::"+resultado);
		   					
		   					if(resultado.equals(ValueConstants.PROCESO_SATISFACTORIO)) {
		   						/*Metodo que genera el formato y copia los archivos FRWS en servidor destino*/
		   						resultado = ProcesoCuatroBO.getInstance().obtenerSentenciaWsFr(cabeceraArchivoBean, mapParametros);
		   						log.info("obtenerSentenciaWsFr :::"+resultado);

				   				if(resultado.equals(ValueConstants.PROCESO_SATISFACTORIO)) {
									
				   					//Actualizamos Estado Proceso Cabecera
									cabeceraArchivoBean.setEstado(ValueConstants.CODIGO_PROCESO4);
									resultado = CabeceraArchivoDAO.actualizar(cabeceraArchivoBean);
									log.info(" actuaizar estado :::"+resultado);		
									//Activar Flag Delay
									activarFlag(cabeceraArchivoBean,ValueConstants.ESTADO_ACTIVO,ValueConstants.VALOR_UNO);
								}	
		   						
		   					}	
			 			
		   				}		

		   				
		   			}		   			
		   					
		   		}
		   		
		   	}catch(Exception e)
	        {	resultado = ValueConstants.ERROR_CAUSAS_TECNICAS;
	            log.error(e.getMessage());
	        }
		   	
		 return resultado;
	 }
	 
	 
	 
	 public void activarFlag(CabeceraArchivoVO cabeceraArchivoVO, String estado, String opcion) {       
		 	
		 	try {
		 	CabeceraArchivoDAO.activarFlag(cabeceraArchivoVO, estado, opcion);
		 	} 
		 	catch(Exception e) {
		 		log.info(e.getMessage());
		 	}

	 }
	 	 
	 
	 public void asignarProcesoCambio(String tipoArchivo, String fechaProceso, String opcion) {
		 
		 	try {
			 	CabeceraArchivoDAO.asignarProcesoCambio(tipoArchivo, fechaProceso, opcion);
			 	} 
			 	catch(Exception e) {
			 		
			 		log.info(e.getMessage());
			 	}
	 }
		 
	 
	 public List<CabeceraArchivoVO> ObtenerCabeceraArchivo(String tipo, String fechaProceso) {       

		 	CabeceraArchivoVO cabeceraArchivoVO = null;
				
		 	List<CabeceraArchivoVO> lista = new ArrayList();   
            try
            {
                 List<DynaBean> listaArchivo = CabeceraArchivoDAO.ObtenerCabeceraArchivo(tipo, fechaProceso);
                 if(log.isInfoEnabled())log.info(" ObtenerCabeceraArchivo ::: size ::: "+(listaArchivo == null?null:listaArchivo.size()));
                 Iterator it = listaArchivo.iterator();
                 for(;it.hasNext();)
                 {
                	 	 cabeceraArchivoVO = new CabeceraArchivoVO();
                         DynaBean dyna = (DynaBean)it.next();
                         
                         cabeceraArchivoVO.setIdcabecera(Integer.valueOf(String.valueOf(dyna.get("IDCABECERA"))));
                         cabeceraArchivoVO.setCantidadRegistros(Integer.valueOf(String.valueOf(dyna.get("CANTIDADREGISTROS"))));
                                                  
                         cabeceraArchivoVO.setFechaReg(String.valueOf(dyna.get("FECHAREGISTRO")));
                         cabeceraArchivoVO.setFechaProceso(String.valueOf(dyna.get("FECHAPROCESO")));
                         cabeceraArchivoVO.setTipoArchivo(String.valueOf(dyna.get("TIPOARCHIVO")));
                         cabeceraArchivoVO.setArchivo(String.valueOf(dyna.get("ARCHIVO")));
                         cabeceraArchivoVO.setProcesoInicio(String.valueOf(dyna.get("PROCESOINICIO")));
                         cabeceraArchivoVO.setEstadoProcesoBean(new EstadoProcesoVO());
                         cabeceraArchivoVO.getEstadoProcesoBean().setCodigo(String.valueOf(dyna.get("ESTADO")));
                         
                         lista.add(cabeceraArchivoVO);
                 }
                 if(log.isInfoEnabled())log.info(" ObtenerCabeceraArchivo ::: size ::: ");
                 
             }
             catch(DAOException de)
             {
                   log.info(de.getMessage());
             }
             catch(Exception e)
             {
                   log.info(e.getMessage());
             }
             return lista;   
    }	 	

	 
	 
	 
	 public String AsignarMensajeFpWs(CabeceraArchivoVO cabeceraArchivoVO, Map map) throws DAOException {       
	 	
		 	String resultado = ValueConstants.PROCESO_SATISFACTORIO;
		 	MensajeVO vo = null;
		 	ArchivoFPVO archivoFPVO = null;
		 	List<MensajeVO> lista=new ArrayList();   
		 	TransactionContext tx = null;
		 	
            try
            {
                 List<DynaBean> listaArchivo = ArchivoFPDAO.ObtenerArchivosFp(cabeceraArchivoVO);
                 
                 if(log.isInfoEnabled())log.info(" AsignarMensajeFpWs ::: size ::: "+(listaArchivo == null?null:listaArchivo.size()));

                 Iterator it = listaArchivo.iterator();
                 tx = new TransactionContext(); 
                 for(;it.hasNext();)
                 {		 
                	 	 vo = new MensajeVO();
                	 	 archivoFPVO = new ArchivoFPVO();
                	 	 DynaBean dyna = (DynaBean)it.next();
                	 	 archivoFPVO.setIdarchivo(Integer.parseInt(String.valueOf(dyna.get("IDARCHIVO"))));
                         archivoFPVO.setAsignatario(String.valueOf(dyna.get("ASIGNATARIO")));
                	 	 archivoFPVO.setReceptor(String.valueOf(dyna.get("RECEPTOR")));
                	 	 archivoFPVO.setWorldNumber(String.valueOf(dyna.get("WORLDNUMBER")));
                	 	 archivoFPVO.setCedente(String.valueOf(dyna.get("CEDENTE")));

                	 	 log.info("REGLAS PORTA FP ==>  ASIG ::"+archivoFPVO.getAsignatario()+" ==> REC ::"+archivoFPVO.getReceptor()+" ==> WNUM ::"+archivoFPVO.getWorldNumber());
                	 	 /*Obtiene Parametros NumberType, HlrAddress, newRoute*/
                	 	 vo = ReglasPortabilidad.getInstance().asignarDatosProcesoPortabilidad(archivoFPVO, map);
                	 	 /*Actualiza columnas obtenidas a cada registro*/
                	 	 ArchivoFPDAO.updateArchivoFpWs(archivoFPVO, vo, tx);
                         tx.commit();
                         lista.add(vo);
                 }
             }
            catch(Exception e){
           	 e.printStackTrace();
           	 log.error(e.getMessage());
           	 try {
           		 tx.rollback();   
           		 resultado = ValueConstants.ERROR_CAUSAS_TECNICAS;             
           	 }
           	 catch(Exception ignore) {
           		 ignore.printStackTrace();
           	 }
           	 finally{
           		 log.error("001"+e.getMessage());
           	 }
            }
            finally{
           	 try{
           		 tx.close();
           		 tx=null;
           	 }catch(Exception ignore){
           		 ignore.printStackTrace();
           	 }
          	}
            
            return resultado;   
    }	 	
	 
	 
	 public String AsignarMensajeFrWs(CabeceraArchivoVO cabeceraArchivoVO, Map map)  
	 {       
	 	
		 	String resultado = ValueConstants.PROCESO_SATISFACTORIO;
		 	MensajeVO vo = null;
		 	ArchivoFRVO archivoFRVO = null;
		 	List<MensajeVO> lista=new ArrayList();   
		 	TransactionContext tx = null;
		 	
            try
            {
                 List<DynaBean> listaArchivo = ArchivoFRDAO.ObtenerArchivosFr(cabeceraArchivoVO);
                 
                 if(log.isInfoEnabled())log.info(" AsignarMensajeFrWs ::: size ::: "+(listaArchivo == null?null:listaArchivo.size()));

                 Iterator it = listaArchivo.iterator();
                 tx = new TransactionContext();
                 for(;it.hasNext();)
                 {
                	 	 vo = new MensajeVO();
                	 	 archivoFRVO = new ArchivoFRVO();
                	 	 DynaBean dyna = (DynaBean)it.next();
                	 	 archivoFRVO.setIdarchivo(Integer.parseInt(String.valueOf(dyna.get("IDARCHIVO"))));
                	 	 archivoFRVO.setAsignatario(String.valueOf(dyna.get("ASIGNATARIO")));
                	 	 archivoFRVO.setReceptor(String.valueOf(dyna.get("RECEPTOR")));
                	 	 archivoFRVO.setCedenteInicial(String.valueOf(dyna.get("CEDENTEINICIAL")));
                	 	 archivoFRVO.setMotivoRetorno(String.valueOf(dyna.get("MOTIVORETORNO")));
                	 	 archivoFRVO.setWorldNumber(String.valueOf(dyna.get("WORLDNUMBER")));
                         
                	 	  
                	 	 log.info("REGLAS PORTA FR ==>  ASIG ::"+archivoFRVO.getAsignatario()+" ==> REC ::"+archivoFRVO.getReceptor()+" ==> CED INI "+archivoFRVO.getCedenteInicial()+" ==> MTO RET ::"+archivoFRVO.getMotivoRetorno()+" ==> WNUM ::"+archivoFRVO.getWorldNumber());
                	 	
                	 	  
                	 	 /*Obtiene Parametros NumberType, HlrAddress, newRoute*/
                	 	 vo = ReglasPortabilidad.getInstance().asignarDatosProcesoRetorno(archivoFRVO, map);
                	 	 /*Actualiza columnas obtenidas a cada registro*/
                	 	 ArchivoFRDAO.updateArchivoFrWs(archivoFRVO, vo,tx);
                		 tx.commit();
                         lista.add(vo);
                 }
             }
             catch(Exception e){
            	 e.printStackTrace();
            	 log.error(e.getMessage());
            	 try {
            		 tx.rollback();   
            	 }
            	 catch(Exception ignore) {
            		 ignore.printStackTrace();
            	 }
            	 finally{
            		 log.error("001"+e.getMessage());
            	 }
             }
             finally{
            	 try{
            		 tx.close();
            		 tx=null;
            	 }catch(Exception ignore){
            		 ignore.printStackTrace();
            	 }
           	}
             
            return resultado;   
    }	 	
	 

	 
	 public List<MensajeVO> ObtenerMensajeFpWs(CabeceraArchivoVO cabeceraArchivoVO, Map map) throws DAOException 
	 {       
		 	log.info(" => GestionProcesoBO ObtenerMensajeFpWs ::: ID CABECERA: "+cabeceraArchivoVO.getIdcabecera());
		 	
		 	MensajeVO vo = null;
		 	List<MensajeVO> lista=new ArrayList();   
            try
            {
                 List<DynaBean> listaArchivo = ArchivoFPDAO.ObtenerArchivosFp(cabeceraArchivoVO);
                 if(log.isInfoEnabled())log.info("Call GestionProcesoBO.ObtenerMensajeFpWs ::: size ::: "+(listaArchivo == null?null:listaArchivo.size()));
                 Iterator it = listaArchivo.iterator();
                 for(;it.hasNext();)
                 {
                	 	 vo = new MensajeVO();
                         DynaBean dyna = (DynaBean)it.next();

                         vo.setComando(String.valueOf(dyna.get("COMANDO")));
                         vo.setNumber(String.valueOf(dyna.get("WORLDNUMBER")));
                         vo.setNumberType(String.valueOf(dyna.get("NUMBERTYPE")));
                         vo.setHLRAddress(String.valueOf(dyna.get("HLRADDRESS")));
                         vo.setNewRoute(String.valueOf(dyna.get("NEWROUTE")));                        
                         vo.setComando(vo.getComando().trim());
                         vo.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
                         vo.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));
                         
                         lista.add(vo);
                 }
                 if(log.isInfoEnabled())log.info("Call GestionProcesoBO.ObtenerMensajeFpWs ::: size ::: ");
                 
             }
             catch(Exception e)
             {
                   e.printStackTrace();
                   throw new DAOException(e.getMessage(),e);
             }
             return lista;   
    }	 	

	  
	 public List<MensajeVO> ObtenerMensajeFrWs(CabeceraArchivoVO cabeceraArchivoVO, Map map) throws DAOException 
	 {       
		 	if (log.isInfoEnabled())  {
		 		log.info(" => GestionProcesoBO ObtenerMensajeFrWs ::: ID CABECERA: "+cabeceraArchivoVO.getIdcabecera());
		 	}
		 	MensajeVO vo = null;
		 	List<MensajeVO> lista=new ArrayList();   
            try
            {
                 List<DynaBean> listaArchivo = ArchivoFRDAO.ObtenerArchivosFr(cabeceraArchivoVO);
                 if(log.isInfoEnabled())log.info("Call GestionProcesoBO.ObtenerMensajeFrWs ::: size ::: "+(listaArchivo == null?null:listaArchivo.size()));
                 Iterator it = listaArchivo.iterator();
                 for(;it.hasNext();)
                 {
                	 	 vo = new MensajeVO();
                         DynaBean dyna = (DynaBean)it.next();

                         vo.setComando(String.valueOf(dyna.get("COMANDO")));
                         vo.setNumber(String.valueOf(dyna.get("WORLDNUMBER")));
                         vo.setNumberType(String.valueOf(dyna.get("NUMBERTYPE")));
                         vo.setHLRAddress(String.valueOf(dyna.get("HLRADDRESS")));
                         vo.setNewRoute(String.valueOf(dyna.get("NEWROUTE")));                        
                         vo.setComando(vo.getComando().trim());
                         vo.setUSERNAME(String.valueOf(map.get(ValueConstants.CODIGO_USUARIO_WS)));
                         vo.setPASSWORD(String.valueOf(map.get(ValueConstants.CODIGO_PASSWORD_WS)));
                         
                         lista.add(vo);
                 }
                 
             }
             catch(Exception e)
             {
            	 log.info(e.getMessage());
             }
             return lista;   
    }	 	

	
	public String obtenerSentenciaWsFp(CabeceraArchivoVO cabeceraArchivoVO, Map map) {

		 MensajeVO vo = null;
		 String resultado = ValueConstants.PROCESO_SATISFACTORIO;
		 boolean retorno = false;
         int i=0;
		 String cadena="";
		 List<MensajeVO> lista = new ArrayList();
		 
		 try {
		  lista = ObtenerMensajeFpWs(cabeceraArchivoVO, map);
		 }
		 catch(DAOException e)
		 { log.error(e.getMessage()); 
		   resultado = ValueConstants.ERROR_CAUSAS_TECNICAS;
		 }
		
		 String[] listaRegistros = new String[lista.size()]; 
			   	
			 for(int j=0;j<lista.size();j++)
			 {
				 vo = (MensajeVO)lista.get(j);
				 
				 cadena = vo.getComando().trim()+
	    	 	 ";"+ValueConstants.LABEL_WS_USERNAME+"="+vo.getUSERNAME()+
	    	 	 ";"+ValueConstants.LABEL_WS_PASSWORD+"=********"+
	    	 	 ";"+ValueConstants.LABEL_WS_NUMBER+"="+vo.getNumber();
	    	 	 
				 //";"+ValueConstants.LABEL_WS_PASSWORD+"="+vo.getPASSWORD()+
	    	 	 if("ADD_USR".equals(vo.getComando().trim())) {
	     	 		 
	    	 		 if(vo.getHLRAddress()!=null && !"".equals(vo.getHLRAddress()) && !"null".equals(vo.getHLRAddress()))	
	    	 			cadena+=";"+ValueConstants.LABEL_WS_HLRADDRESS+"="+vo.getHLRAddress();
	    	 		 if(vo.getNumberType()!=null && !"".equals(vo.getNumberType()) && !"null".equals(vo.getNumberType()))	
	     	 			cadena+=";"+ValueConstants.LABEL_WS_NUMBERTYPE+"="+vo.getNumberType().trim();
	    	 		 if(vo.getNewRoute()!=null && !"".equals(vo.getNewRoute()) && !"null".equals(vo.getNewRoute()))	
	     	 			cadena+=";"+ValueConstants.LABEL_WS_NEWROUTE+"="+vo.getNewRoute();
	    	 		 
	    	 	 } 

	    	 	 listaRegistros[i]=cadena;
	    	 	 i++;
			 }	 
			 
			 String nombre_archivo=ValueConstants.ARCHIVO_FPWS+cabeceraArchivoVO.getFechaProceso();
			 if(log.isInfoEnabled())log.info(" RUTA ==> obtenerSentenciaWsFp ::: PATH="+String.valueOf(map.get(ValueConstants.CODIGO_RUTA_DESTINO_PROCESO_CUATRO))+"  ---- NAME ="+nombre_archivo);
	         
			 try {
			 if(FileUtil.existsFile(String.valueOf(map.get(ValueConstants.CODIGO_RUTA_DESTINO_PROCESO_CUATRO))+ nombre_archivo))	 
				FileUtil.deleteFile(String.valueOf(map.get(ValueConstants.CODIGO_RUTA_DESTINO_PROCESO_CUATRO))+ nombre_archivo);
						 
			 retorno = FileUtil.makeFile(listaRegistros, String.valueOf(map.get(ValueConstants.CODIGO_RUTA_DESTINO_PROCESO_CUATRO)), nombre_archivo, true, true);
			 } 
			 catch(FileUtilException e)
			 { log.error(e.getMessage()); 
			   resultado = ValueConstants.ERROR_ARCHIVO_CREACION_INCORRECTA;
			 }
			 
			 
			 return resultado;		   
	}
		 
	
	public String obtenerSentenciaWsFr(CabeceraArchivoVO cabeceraArchivoVO, Map map) {

			 MensajeVO vo = null;
			 String resultado = ValueConstants.PROCESO_SATISFACTORIO;
			 boolean retorno = false;
	         int i=0;
			 String cadena="";
			 List<MensajeVO> lista = new ArrayList();
			 
			 try {
			 lista = ObtenerMensajeFrWs(cabeceraArchivoVO, map);
			 } catch(DAOException de)
			 {
				 log.info(de.getMessage());
				 resultado=ValueConstants.ERROR_CAUSAS_TECNICAS;
			 }
			 String[] listaRegistros = new String[lista.size()]; 
			   	
			 if(log.isInfoEnabled())log.info("Call GestionProcesoBO.obtenerSentenciaWsFr : "+lista.size());

			 for(int j=0;j<lista.size();j++)
			 {
				 vo = (MensajeVO)lista.get(j);
				 
				 cadena = vo.getComando().trim()+
	    	 	 ";"+ValueConstants.LABEL_WS_USERNAME+"="+vo.getUSERNAME()+
	    	 	 ";"+ValueConstants.LABEL_WS_PASSWORD+"=********"+
	    	 	 ";"+ValueConstants.LABEL_WS_NUMBER+"="+vo.getNumber();
	    	 	 // ";"+ValueConstants.LABEL_WS_PASSWORD+"="+vo.getPASSWORD()+
	    	 	 if("ADD_USR".equals(vo.getComando().trim())) {
	     	 		 
	    	 		 if(log.isInfoEnabled())log.info("Call GestionProcesoBO.obtenerSentenciaWsFr ---00011-- "+vo.getComando());
	    	 		 
	    	 		 if(vo.getHLRAddress()!=null && !"".equals(vo.getHLRAddress()) && !"null".equals(vo.getHLRAddress()))	
		    	 		cadena+=";"+ValueConstants.LABEL_WS_HLRADDRESS+"="+vo.getHLRAddress();
		    	 	 if(vo.getNumberType()!=null && !"".equals(vo.getNumberType()) && !"null".equals(vo.getNumberType()))	
		     	 		cadena+=";"+ValueConstants.LABEL_WS_NUMBERTYPE+"="+vo.getNumberType().trim();
		    	 	 if(vo.getNewRoute()!=null && !"".equals(vo.getNewRoute()) && !"null".equals(vo.getNewRoute()))	
		     	 		cadena+=";"+ValueConstants.LABEL_WS_NEWROUTE+"="+vo.getNewRoute();
	    	 		 
	    	 	 } 
	    	 	 
	    	 	 listaRegistros[i]=cadena;
	    	 	 i++;
			 }	 
			 
			 
			 String nombre_archivo=ValueConstants.ARCHIVO_FRWS+cabeceraArchivoVO.getFechaProceso();
			 if(log.isInfoEnabled())log.info("Call GestionProcesoBO.obtenerSentenciaWsFr ::: PATH="+String.valueOf(map.get(ValueConstants.CODIGO_RUTA_DESTINO_PROCESO_CUATRO))+"  ---- NAME ="+nombre_archivo);

			 try {
				 
			 if(FileUtil.existsFile(String.valueOf(map.get(ValueConstants.CODIGO_RUTA_DESTINO_PROCESO_CUATRO))+ nombre_archivo ))	 
				FileUtil.deleteFile(String.valueOf(map.get(ValueConstants.CODIGO_RUTA_DESTINO_PROCESO_CUATRO))+ nombre_archivo);
				 
			 retorno = FileUtil.makeFile(listaRegistros, String.valueOf(map.get(ValueConstants.CODIGO_RUTA_DESTINO_PROCESO_CUATRO)), nombre_archivo, true, true);
			 } 
			 catch(FileUtilException e)
			 { 
				log.error(e.getMessage()); 
			 	resultado = ValueConstants.ERROR_ARCHIVO_CREACION_INCORRECTA;
			 }
			 
			 return resultado;		   
	}
	
	
}
